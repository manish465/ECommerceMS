package com.manish.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @Size(min = 2, max = 30)
    @NotBlank
    private String username;
    @Size(min = 8, max = 50)
    @NotBlank
    private String password;
}
