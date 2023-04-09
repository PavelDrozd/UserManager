package com.neaktor.usermanager.web.rest;

import com.neaktor.usermanager.data.dto.ImageDto;
import com.neaktor.usermanager.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageRestController {

    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        ImageDto image = imageService.get(id);
        return buildResponseImage(image);
    }

    private ResponseEntity<?> buildResponseImage(ImageDto image) {
        return ResponseEntity.status(HttpStatus.OK)
                .header("fileName", image.getFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}
