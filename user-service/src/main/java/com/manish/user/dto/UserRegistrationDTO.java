package com.manish.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @Size(min = 2, max = 30)
    private String username;
    @Size(min = 2, max = 20)
    private String firstname;
    @Size(min = 2, max = 20)
    private String lastname;
    @Email
    private String email;
    @Size(min = 8, max = 50)
    private String password;
    @Size(min = 2, max = 20)
    private String roles;
}
