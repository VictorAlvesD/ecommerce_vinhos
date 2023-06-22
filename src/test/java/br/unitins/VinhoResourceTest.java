package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.VinhoDTO;
import br.unitins.dto.VinhoResponseDTO;
import br.unitins.service.VinhoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class VinhoResourceTest {
    @Inject
    VinhoService vinhoService;
    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new AuthUsuarioDTO("teste", "12345");

        Response response = (Response) given()
                .contentType("application/json")
                .body(auth)
                .when().post("/auth")
                .then().statusCode(200)
                .extract()
                .response();

        token = response.header("Authorization");
    }

    @Test
    public void getAllTest() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/vinhos")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertVinhoTest() {
        VinhoDTO vinho = new VinhoDTO("Valtier Reserva", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(vinho)
                .when().post("/vinhos")
                .then()
                .statusCode(201);
    }

}
