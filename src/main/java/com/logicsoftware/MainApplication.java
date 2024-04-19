package com.logicsoftware;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name="Autenticação", description="Autenticação de Usuários."),
                @Tag(name="Usuário", description="Métodos de controle de Usuário."),
                @Tag(name="Perfil", description="Métodos de controle de Perfil."),
                @Tag(name="Endereço", description="Métodos de controle de Endereço.")
        },
        info = @Info(
                title="Template API",
                version = "1.0",
                description = "API de integração com o Template.",
                contact = @Contact(
                        name = "Logic Software",
                        url = "https://www.logicsoftware.com.br",
                        email = "logicsoftware86@gmail.com")),
        security = @SecurityRequirement(name = "jwt")
)
public class MainApplication extends Application {
}
