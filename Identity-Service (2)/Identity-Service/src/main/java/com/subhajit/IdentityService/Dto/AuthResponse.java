package com.subhajit.IdentityService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.subhajit.IdentityService.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("User_Details")
    private User user;
}
