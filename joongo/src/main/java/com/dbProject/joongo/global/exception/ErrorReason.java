package com.dbProject.joongo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorReason {
    private final String message;
    private final String code;
    private final boolean isSuccess;
}
