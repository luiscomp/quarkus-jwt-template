package com.logicsoftware.utils.auth;

import javax.ws.rs.FormParam;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @FormParam("username")
    @Schema(example = "luizeduardo354@gmail.com")
    private String username;
    
    @FormParam("password")
    @Schema(example = "123456")
    private String password;

    @FormParam("grant_type")
    @Schema(example = "password")
    private GrantType grantType;

    @FormParam("refresh_token")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
