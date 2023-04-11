package com.neaktor.usermanager.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private List<ImageDto> images = new ArrayList<>();

    private Status status;

    public enum Status {
        ONLINE, OFFLINE
    }

    public void addImageDtoToUserDto(ImageDto image) {
        image.setUser(this);
        images.add(image);
    }
}
