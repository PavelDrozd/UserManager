package com.neaktor.usermanager.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neaktor.usermanager.data.dto.ImageDto;
import com.neaktor.usermanager.data.dto.UserDto;
import com.neaktor.usermanager.service.UserService;
import com.neaktor.usermanager.shared.exception.controller.ControllerInvalidVariableException;
import com.neaktor.usermanager.shared.exception.controller.ControllerValidationException;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestParam("user") String userJson, @RequestParam("uri") String uri) {
        ImageDto image = getImageDtoFromURI(uri);
        UserDto user = processUserJson(userJson);
        user.addImageDtoToUserDto(image);
        UserDto createdUser = userService.create(user);
        return buildResponseUser(createdUser);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestParam("user") String userJson,
                                            @RequestParam("file1") @Nullable MultipartFile file1,
                                            @RequestParam("file2") @Nullable MultipartFile file2,
                                            @RequestParam("file3") @Nullable MultipartFile file3) {
        UserDto user = processUserJson(userJson);
        UserDto createdUser = userService.create(user, processFiles(file1, file2, file3));
        return buildResponseUser(createdUser);
    }

    private ImageDto getImageDtoFromURI(String uri) {
        try {
            URL imageUrl = new URL(uri.replace("\"", ""));
            URLConnection connection = imageUrl.openConnection();
            String contentType = connection.getContentType();
            byte[] bytesFromDecode = getBytesFromDecode(imageUrl, contentType);
            BufferedImage newBi = toBufferedImage(bytesFromDecode);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(newBi, contentType.replace("image/", ""), out);
            return procesImageDto(contentType, out);
        } catch (IOException e) {
            throw new ControllerInvalidVariableException(e);
        }

    }

    private byte[] getBytesFromDecode(URL imageUrl, String contentType) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageUrl);
        byte[] bytes = toByteArray(bufferedImage, contentType.replace("image/", ""));
        String bytesBase64 = Base64.encodeBase64String(bytes);
        return Base64.decodeBase64(bytesBase64);
    }

    private ImageDto procesImageDto(String contentType, ByteArrayOutputStream out) {
        ImageDto image = new ImageDto();
        image.setContentType(contentType);
        String fileName = RandomString.make() + new Date().getTime() + "." + contentType.replace("image/", "");
        image.setFileName(fileName);
        image.setName(fileName);
        image.setBytes(out.toByteArray());
        image.setSize((long) out.size());
        return image;
    }

    private BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream is = new ByteArrayInputStream(bytes);
        return ImageIO.read(is);
    }

    private byte[] toByteArray(BufferedImage bi, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        return baos.toByteArray();
    }

    private UserDto processUserJson(String userJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto user;
        try {
            user = objectMapper.readValue(userJson, UserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private List<MultipartFile> processFiles(MultipartFile... multipartFiles) {
        List<MultipartFile> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile != null) {
                files.add(multipartFile);
            }
        }
        return files;
    }

    private void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ControllerValidationException(errors);
        }
    }

    private ResponseEntity<UserDto> buildResponseUser(UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).location(getLocation(user)).body(user);
    }

    private URI getLocation(UserDto user) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{id}").buildAndExpand(user.getId()).toUri();

    }

}
