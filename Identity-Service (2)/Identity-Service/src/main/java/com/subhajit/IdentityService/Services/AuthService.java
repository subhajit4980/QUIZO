package com.subhajit.IdentityService.Services;

import com.subhajit.IdentityService.Dto.AuthResponse;
import com.subhajit.IdentityService.Dto.SignUpRequest;
import com.subhajit.IdentityService.Exception.UserException;
import com.subhajit.IdentityService.Model.User;
import com.subhajit.IdentityService.Repositories.UserRepository;
import com.subhajit.IdentityService.Utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    public AuthResponse signUpUser(SignUpRequest request) {
        if(!request.getEmail().contains("@gmail.com")) throw new UserException("Email is not valid","EMAIL_NOT_VALID", HttpStatus.BAD_REQUEST);
        if(repository.existsByEmail(request.getEmail().toUpperCase(Locale.ROOT))) throw new UserException("User already registered","USER_EXIST",HttpStatus.BAD_REQUEST);
        var charlist= Common.validatePassword(request.getPassword());
        if(request.getPassword().length()<8) throw new UserException("Password  length must be greater than 8 characters","PASSWORD_LENGTH_NOT_VALID",HttpStatus.BAD_REQUEST);
        if(!charlist.isEmpty()) throw new UserException("Password is invalid. Missing character types:" + charlist.toString(),"INVALID_PASSWORD",HttpStatus.BAD_REQUEST);
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail().toUpperCase(Locale.ROOT))
                .password(request.getPassword())
                .creationDate(new Date(System.currentTimeMillis()))
                .verified(false)
                .role(request.getRole())
                .build();

        var savedUser = repository.save(user);
        return AuthResponse.builder()
                .user(savedUser)
                .build();
    }
}
