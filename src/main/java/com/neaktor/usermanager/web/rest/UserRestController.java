package com.neaktor.usermanager.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neaktor.usermanager.data.dto.UserDto;
import com.neaktor.usermanager.service.UserService;
import com.neaktor.usermanager.shared.exception.controller.ControllerValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
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

    @PostMapping("/register")
    public ResponseEntity<UserDto> create(@RequestParam("user") String userJson,
                                          @RequestParam("file1") MultipartFile file1,
                                          @RequestParam("file2") MultipartFile file2,
                                          @RequestParam("file3") MultipartFile file3) {
        List<MultipartFile> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto user;
        user = processUserJson(userJson, objectMapper);
        UserDto createdUser = userService.create(user, files);
        return buildResponseUser(createdUser);
    }

    private UserDto processUserJson(String userJson, ObjectMapper objectMapper) {
        UserDto user;
        try {
            user = objectMapper.readValue(userJson, UserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ControllerValidationException(errors);
        }
    }

    private ResponseEntity<UserDto> buildResponseUser(UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(user))
                .body(user);
    }

    private URI getLocation(UserDto user) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

    }

}
