package com.neaktor.usermanager.service.impl;

import com.neaktor.usermanager.data.dto.UserDto;
import com.neaktor.usermanager.service.UserService;
import com.neaktor.usermanager.shared.exception.service.ServiceNotFoundException;
import com.neaktor.usermanager.shared.exception.service.ServiceValidationException;
import com.neaktor.usermanager.shared.util.mapper.EntityDtoMapper;
import com.neaktor.usermanager.store.entity.User;
import com.neaktor.usermanager.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EntityDtoMapper mapper;

    @Override
    public UserDto create(UserDto userDto) {
        checkUserNull(userDto);
        User user = userRepository.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::mapToUserDto);
    }

    @Override
    public UserDto get(Long id) {
        checkIdNull(id);
        return userRepository.findById(id)
                .map(mapper::mapToUserDto)
                .orElseThrow(() -> new ServiceNotFoundException("User with id: " + id + " doesn't exist"));
    }

    @Override
    public UserDto update(UserDto userDto) {
        checkUserNull(userDto);
        User user = userRepository.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Override
    public void delete(Long id) {
        checkIdNull(id);
        userRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException("User with id: " + id + " doesn't exist"));
        userRepository.deleteById(id);
    }

    private void checkUserNull(UserDto userDto) {
        if (userDto == null) {
            throw new ServiceValidationException("User is null");
        }
    }

    private void checkIdNull(Long id) {
        if (id == null) {
            throw new ServiceValidationException("ID is null");
        }
    }
}
