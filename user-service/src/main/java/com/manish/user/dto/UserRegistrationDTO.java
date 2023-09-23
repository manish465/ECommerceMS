package com.manish.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @Size(min = 2, max = 30)
    @NotBlank
    private String username;
    @Size(min = 2, max = 20)
    @NotBlank
    private String firstname;
    @Size(min = 2, max = 20)
    @NotBlank
    private String lastname;
    @Email
    @NotBlank
    private String email;
    @Size(min = 8, max = 50)
    @NotBlank
    private String password;
    @Size(min = 2, max = 20)
    @NotBlank
    private String roles;
}
