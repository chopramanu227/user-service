package com.example.user.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Model Object to capture User details.
 */
@Data
@Builder
public class User {
    @NotBlank
    private String title;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String gender;
    private Address address;
}
