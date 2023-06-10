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

    
    @Test
    public void testFindById() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/vinhos/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/vinhos/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/vinhos/search/João")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        VinhoDTO dto = new VinhoDTO("", 15, (double) 50, "13%", "Produzido a partir das uvas Tempranillo Bobal.",
                "Tempranillo Bobal", 1, 1); // dto sem nome
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/vinhos")
                .then()
                .statusCode(404);
    }

    // Teste de método PUT com validação de existência de vinho:
    @Test
    public void testUpdateIdInexistente() {
        VinhoDTO dto = new VinhoDTO("Valtier Reserva Utiel-Requena DOP", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1);
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/vinhos/12") // id inválido
                .then()
                .statusCode(404);
    }

    // Teste de método DELETE com validação de existência de vinho:
    @Test
    public void testDeleteIdInexistente() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().delete("/vinhos/115") // id inválido
                .then()
                .statusCode(404);
    }
}
