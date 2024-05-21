package com.subhajit.IdentityService.Controller;

import com.subhajit.IdentityService.Dto.AuthResponse;
import com.subhajit.IdentityService.Dto.SignInRequest;
import com.subhajit.IdentityService.Dto.SignUpRequest;
import com.subhajit.IdentityService.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> signUpUser(@RequestBody SignUpRequest request) {
        AuthResponse response = authService.signUpUser(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequest request){
        return authService.authenticate(request);
    }

}
