package com.subhajit.IdentityService.Model;

import com.subhajit.IdentityService.Model.Enum.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class User {
    @Id
    private String userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    private Date creationDate;

    @NotBlank
    private Boolean verified;

    @Enumerated(EnumType.STRING)
    private Role role;
}
