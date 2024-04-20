package com.logicsoftware.utils.auth;

import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

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
    @Schema(name = "grant_type", example = "password")
    private GrantType grantType;

    @FormParam("refresh_token")
    @Schema(name = "refresh_token")
    private String refreshToken;
}
