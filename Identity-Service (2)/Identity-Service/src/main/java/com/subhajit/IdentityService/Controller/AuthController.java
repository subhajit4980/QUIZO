package com.subhajit.IdentityService.Controller;

import com.subhajit.IdentityService.Dto.AuthResponse;
import com.subhajit.IdentityService.Dto.SignUpRequest;
import com.subhajit.IdentityService.Model.User;
import com.subhajit.IdentityService.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    public ResponseEntity<AuthResponse> signUpUser(@RequestBody SignUpRequest user) {
        AuthResponse response = authService.signUpUser(user);
        return ResponseEntity.ok(response);
    }

}
