package com.example.user.model;

import lombok.Builder;
import lombok.Data;

/**
 * Model Object to encapsulate error details.
 */
@Data
@Builder
public class Error {
    private String code;
    private String msg;
}
