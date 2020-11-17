package com.example.user.model;

import lombok.Builder;
import lombok.Data;

/**
 * Model Object to capture Address details.
 */
@Data
@Builder
public class Address {
    private String street;
    private String city;
    private String state;
    private String postcode;
}
