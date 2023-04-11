package com.neaktor.usermanager.service.impl;

import com.neaktor.usermanager.data.dto.UserDto;
import com.neaktor.usermanager.service.UserService;
import com.neaktor.usermanager.shared.exception.service.ServiceNotFoundException;
import com.neaktor.usermanager.shared.exception.service.ServiceValidationException;
import com.neaktor.usermanager.shared.util.mapper.EntityDtoMapper;
import com.neaktor.usermanager.store.entity.Image;
import com.neaktor.usermanager.store.entity.User;
import com.neaktor.usermanager.store.repository.ImageRepository;
import com.neaktor.usermanager.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private final EntityDtoMapper mapper;

    @Override
    public UserDto create(UserDto userDto) {
        checkUserNull(userDto);
        checkEmail(userDto.getEmail());
        userDto.setStatus(UserDto.Status.OFFLINE);
        User user = userRepository.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Override
    public UserDto create(UserDto userDto, List<MultipartFile> files) {
        checkUserNull(userDto);
        checkEmail(userDto.getEmail());
        userDto.setStatus(UserDto.Status.OFFLINE);
        User user = userRepository.save(mapper.mapToUser(userDto));
        processImage(files, user);
        return mapper.mapToUserDto(user);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::mapToUserDto);
    }

    @Override
    public UserDto get(Long id) {
        checkId(id);
        return userRepository.findById(id)
                .map(mapper::mapToUserDto)
                .orElseThrow(() -> new ServiceNotFoundException("User with id: " + id + " doesn't exist"));
    }

    @Override
    public UserDto update(UserDto userDto) {
        checkUserNull(userDto);
        checkId(userDto.getId());
        checkEmail(userDto.getEmail());
        User user = userRepository.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Override
    public void delete(Long id) {
        checkId(id);
        userRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException("User with id: " + id + " doesn't exist"));
        userRepository.deleteById(id);
    }

    private void checkUserNull(UserDto userDto) {
        if (userDto == null) {
            throw new ServiceValidationException("User is null");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new ServiceValidationException("ID is null");
        }
        if (id < 0L) {
            throw new ServiceValidationException("ID less than 0");
        }
    }

    private void checkEmail(String email) {
        if (!email.contains("@")){
            throw new ServiceValidationException("Invalid email");
        }
    }

    private void processImage(List<MultipartFile> files, User user) {
        for (MultipartFile file : files) {
            if (file.getSize() != 0) {
                Image image = toImageEntity(file);
                image.setUser(user);
                imageRepository.save(image);
                user.addImageToUser(image);
            }
        }
    }

    private Image toImageEntity(MultipartFile file) {
        Image image = new Image();
        image.setName(file.getName());
        image.setFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        processBytes(file, image);
        return image;
    }

    private void processBytes(MultipartFile file, Image image) {
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new ServiceValidationException(e);
        }
    }
}
