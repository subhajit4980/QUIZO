package com.subhajit.IdentityService.Dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignInRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}