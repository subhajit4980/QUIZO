package com.subhajit.IdentityService.Services;

import com.subhajit.IdentityService.Dto.AuthResponse;
import com.subhajit.IdentityService.Dto.SignInRequest;
import com.subhajit.IdentityService.Dto.SignUpRequest;
import com.subhajit.IdentityService.Exception.UserException;
import com.subhajit.IdentityService.Model.User;
import com.subhajit.IdentityService.Repositories.UserRepository;
import com.subhajit.IdentityService.Utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public AuthResponse signUpUser(SignUpRequest request) {
        if(!request.getEmail().contains("@gmail.com")) throw new UserException( HttpStatus.BAD_REQUEST,"Email is not valid");
        if(repository.existsByEmail(request.getEmail().toUpperCase(Locale.ROOT))) throw new UserException(HttpStatus.CONFLICT,"User already registered");
        var charlist= Common.validatePassword(request.getPassword());
        if(request.getPassword().length()<8) throw new UserException(HttpStatus.BAD_REQUEST,"Password  length must be greater than 8 characters");
        if(!charlist.isEmpty()) throw new UserException(HttpStatus.BAD_REQUEST,"Password is invalid. Missing character types:" + charlist.toString());
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail().toUpperCase(Locale.ROOT))
                .password(encoder.encode(request.getPassword()))
                .creationDate(new Date(System.currentTimeMillis()))
                .verified(false)
                .role(request.getRole())
                .build();

        var savedUser = repository.save(user);
        String jwtToken = jwtUtils.generateToken(new CustomUserDetails(user));

        return AuthResponse.builder()
                .user(savedUser)
                .accessToken(jwtToken)
                .build();
    }
    @Transactional(readOnly = true)
    public ResponseEntity<?> authenticate(SignInRequest request) {
        if(!request.getEmail().contains("@gmail.com")) throw new UserException(HttpStatus.BAD_REQUEST,"Email is not valid");
        Authentication authentication=authentication(request.getEmail().toUpperCase(Locale.ROOT), request.getPassword());
        if(authentication.isAuthenticated()){
            var user = repository.findByEmail(request.getEmail().toUpperCase(Locale.ROOT))
                    .orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST,"Email is not registered"));
            var jwtToken = jwtUtils.generateToken(new CustomUserDetails(user));
            AuthResponse response=AuthResponse.builder()
                    .accessToken(jwtToken)
                    .user(user)
                    .build();
            return  ResponseEntity.ok(response);
        }else
            throw new UserException(HttpStatus.BAD_REQUEST,"Wrong Credentials Provided");
    }

    public Authentication authentication(String email, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(email.toUpperCase(Locale.ROOT), password);
        try {
            Authentication authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UserException(HttpStatus.BAD_REQUEST,"Wrong Credentials Provided");
        }
    }
    public boolean isExpired(String token)
    {
        return jwtUtils.isTokenExpired(token);
    }
}
