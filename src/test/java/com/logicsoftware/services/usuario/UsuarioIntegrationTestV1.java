package com.logicsoftware.services.usuario;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.logicsoftware.dtos.usuario.UsuarioDTO;
import com.logicsoftware.utils.request.PageResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseSensitiveTableNames = true)
@QuarkusTest
public class UsuarioIntegrationTestV1 {

    @Test
    @DataSet("users.yml")
    public void userListEndpoint() {
        given()
            .when()
            .contentType(ContentType.JSON)
            .queryParam("page", "1")
            .queryParam("size", "10")
            .post("/usuario/listar")
            .then()
            .statusCode(200)
            .extract()
            .body().as(PageResponse.class)
            .equals(
                PageResponse.<UsuarioDTO>builder()
                    .totalElements(0L)
                    .pageSize(10)
                    .totalPages(1)
                    .build()
            );
    }
}
