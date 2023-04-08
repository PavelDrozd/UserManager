package com.neaktor.usermanager.web.errorhandler;

import com.neaktor.usermanager.shared.exception.ApplicationException;
import com.neaktor.usermanager.shared.exception.controller.ControllerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice("com.neaktor.usermanager.web.view")
public class ErrorHandler {

    private static final String ATTR_ERROR = "error";

    private static final String ERROR_PAGE = "error";
    private static final String ATTR_MESSAGE = "message";
    private static final String SERVER_ERROR = "Server error";
    private static final String CLIENT_ERROR = "Client error";
    private static final String NOT_FOUND = "Not found";
    private static final String SERVER_ERROR_DEFAULT_MESSAGE = "Something went wrong...";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error(ControllerNotFoundException e, Model model){
        model.addAttribute(ATTR_ERROR, NOT_FOUND);
        model.addAttribute(ATTR_MESSAGE, e.getMessage());
        return ERROR_PAGE;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error(ApplicationException e, Model model) {
        model.addAttribute(ATTR_ERROR, CLIENT_ERROR);
        model.addAttribute(ATTR_MESSAGE, e.getMessage());
        return ERROR_PAGE;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Exception e, Model model) {
        model.addAttribute(ATTR_ERROR, SERVER_ERROR);
        model.addAttribute(ATTR_MESSAGE, SERVER_ERROR_DEFAULT_MESSAGE);
        return ERROR_PAGE;
    }


}
