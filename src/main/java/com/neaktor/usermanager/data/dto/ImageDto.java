package com.neaktor.usermanager.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Long id;

    private String name;

    private String fileName;

    private Long size;

    private String contentType;

    private boolean isPreviewImage;

    private byte[] bytes;

    @ToString.Exclude
    @JsonIgnore
    private UserDto user;

}
