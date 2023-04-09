package com.neaktor.usermanager.service;

import com.neaktor.usermanager.data.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends AbstractService<UserDto, Long> {

    UserDto create(UserDto userDto, List<MultipartFile> files);
}
