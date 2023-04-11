package com.neaktor.usermanager.service.impl;

import com.neaktor.usermanager.data.dto.UserDto;
import com.neaktor.usermanager.service.UserService;
import com.neaktor.usermanager.shared.exception.service.ServiceNotFoundException;
import com.neaktor.usermanager.shared.exception.service.ServiceValidationException;
import com.neaktor.usermanager.shared.util.mapper.EntityDtoMapper;
import com.neaktor.usermanager.store.entity.User;
import com.neaktor.usermanager.store.repository.ImageRepository;
import com.neaktor.usermanager.store.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private static UserService userService;

    private static final Long INVALID_USER_ID = Long.MIN_VALUE;
    private static final Long INVALID_USER_MAX_ID = Long.MAX_VALUE;
    private static final Long VALID_USER_ID = 1L;
    private static final String INVALID_USER_USERNAME = "n";
    private static final String VALID_USER_USERNAME = "test";
    private static final String INVALID_USER_EMAIL = "email";
    private static final String VALID_USER_EMAIL = "test@gmail.com";

    @BeforeAll
    static void beforeAll() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        ImageRepository imageRepository = Mockito.mock(ImageRepository.class);

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setEmail("test@gmail.com");

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(user));

        EntityDtoMapper mapper = new EntityDtoMapper(new ModelMapper());
        userService = new UserServiceImpl(userRepository, imageRepository, mapper);
    }

    @Test
    void createUserNullInExceptionOut() {
        assertThrows(ServiceValidationException.class, () -> userService.create(null));
    }

    @Test
    void getUserByID() {
        UserDto actual = userService.get(VALID_USER_ID);
        assertEquals(VALID_USER_ID, actual.getId());
    }

    @Test
    void getUserWithInvalidID() {
        assertThrows(ServiceValidationException.class, () -> userService.get(INVALID_USER_ID));
    }

    @Test
    void updateUserWithInvalidID() {
        UserDto user = new UserDto();
        user.setId(INVALID_USER_ID);
        assertThrows(ServiceValidationException.class, () -> userService.update(user));
    }

    @Test
    void updateUserWithInvalidEmail() {
        UserDto user = new UserDto();
        user.setUsername(VALID_USER_USERNAME);
        user.setEmail(INVALID_USER_EMAIL);
        assertThrows(ServiceValidationException.class, () -> userService.update(user));
    }

}