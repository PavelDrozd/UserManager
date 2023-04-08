package com.neaktor.usermanager.data.dto.error;

import java.util.List;
import java.util.Map;

public class ValidationResultDto extends ErrorDto {

    private static final String DEFAULT_ERROR = "Validation error";
    private static final String DEFAULT_ERROR_MESSAGE = "Sent data violates validation constraints";

    private Map<String, List<String>> messages;

    public ValidationResultDto() {
        super(DEFAULT_ERROR, DEFAULT_ERROR_MESSAGE);
    }

    public ValidationResultDto(Map<String, List<String>> messages) {
        this();
        this.messages = messages;
    }
}
