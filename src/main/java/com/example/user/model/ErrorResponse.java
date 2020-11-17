package com.example.user.model;

import lombok.Builder;
import lombok.Data;

/**
 * Model Object for the Error response returned by API.
 */
@Data
@Builder
public class ErrorResponse {
    private Error error;
}
