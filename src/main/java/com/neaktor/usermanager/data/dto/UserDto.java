package com.neaktor.usermanager.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private ImageDto image;

    private Status status;

    public enum Status {
        ONLINE, OFFLINE
    }

}
