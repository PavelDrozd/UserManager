package com.neaktor.usermanager.web.view;

import com.neaktor.usermanager.data.dto.UserDto;
import com.neaktor.usermanager.service.UserService;
import com.neaktor.usermanager.shared.exception.controller.ControllerInvalidVariableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserViewController {

    private static final String ATTR_USER = "user";
    private static final String ATTR_IMAGES = "images";
    private static final String ATTR_STATUS = "status";
    private static final String ATTR_MESSAGE = "message";

    private static final String MESSAGE_SUCCESS = "Success";

    private static final String INDEX_PAGE = "index";
    private static final String USER_PAGE = "user";
    private static final String USER_REGISTER_PAGE = "register";
    private static final String USER_CREATE_PAGE = "create";

    private final UserService userService;

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        UserDto user = userService.get(id);
        model.addAttribute(ATTR_USER, user);
        model.addAttribute(ATTR_IMAGES, user.getImages());
        return USER_PAGE;
    }

    @GetMapping("/create")
    public String create() {
        return USER_CREATE_PAGE;
    }

    @GetMapping("/register")
    public String register() {
        return USER_REGISTER_PAGE;
    }

    @GetMapping("/edit/{id}/{status}")
    public String changeStatus(@PathVariable Long id, @PathVariable String status, Model model) {
        UserDto existUser = userService.get(id);
        UserDto.Status oldStatus = existUser.getStatus();
        UserDto.Status newStatus = validateStatus(status);
        existUser.setStatus(newStatus);
        userService.update(existUser);
        model.addAttribute(ATTR_STATUS, MESSAGE_SUCCESS);
        model.addAttribute(ATTR_MESSAGE, "Successfully changed the status from " + oldStatus + " to "
                + newStatus + " for user with id:" + id);
        return INDEX_PAGE;
    }

    private UserDto.Status validateStatus(String status) {
        try {
            return UserDto.Status.valueOf(status.toUpperCase());
        } catch (RuntimeException e) {
            throw new ControllerInvalidVariableException("Invalid status name");
        }
    }
}
