package com.neaktor.usermanager.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
