package com.example.garage.UserEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Mobile number is mandatory")
    private String mobileNumber;

    @NotBlank(message = "Email address is mandatory")
    @Email(message = "Email should be valid")
    private String emailAddress;

    @NotBlank(message = "Password is mandatory")
    private String password;
}