package com.neaktor.usermanager.shared.exception.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
public class ControllerValidationException extends ControllerException {

    @Getter
    private final Errors errors;

}
