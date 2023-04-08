package com.neaktor.usermanager.web.rest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public UserDto get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> create(@RequestBody UserDto user, HttpSession session, Errors errors) {
        checkErrors(errors);
        UserDto created = userService.create(user);
        session.setAttribute("user", created);
        return buildResponseUser(created);
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
