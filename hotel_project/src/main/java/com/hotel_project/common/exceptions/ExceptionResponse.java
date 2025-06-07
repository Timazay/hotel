package com.hotel_project.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private String message;
    private Map<String, String> metadata;
}
