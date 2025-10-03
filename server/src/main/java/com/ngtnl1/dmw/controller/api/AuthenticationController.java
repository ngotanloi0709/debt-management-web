package com.ngtnl1.dmw.controller.api;

import com.ngtnl1.dmw.dto.authentication.login.LoginRequestDTO;
import com.ngtnl1.dmw.dto.authentication.login.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> postLogin(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(new LoginResponseDTO(request.toString()));
    }
}
