package com.ngtnl1.dmw.dto.authentication.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Email is empty")
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    private String email;

    @NotBlank(message = "Password is empty")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;
}
