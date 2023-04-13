package com.logicsoftware.utils.auth;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Dto que representa a resposta de autenticação.")
public class AuthResponse {
    String access_token;
    String refresh_token;
    Long expires_in;
    Long refresh_expires_in;
    String token_type;
}
