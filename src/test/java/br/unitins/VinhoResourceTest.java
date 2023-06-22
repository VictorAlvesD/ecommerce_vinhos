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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class VinhoResourceTest {
    @Inject
    VinhoService vinhoService;
    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new AuthUsuarioDTO("duda", "123");

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
    public void testUpdate() {
        VinhoDTO vinho = new VinhoDTO("Valtier Reserva", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1);

        Long id = vinhoService.insert(vinho).id();

        VinhoDTO vinho2 = new VinhoDTO("Valtier", 50, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(vinho2)
                .when().put("/vinhos/" + id)
                .then()
                .statusCode(204);

        VinhoResponseDTO vinhoResponse = vinhoService.findById(id);
        assertThat(vinhoResponse.nome(), is("Valtier"));
        assertThat(vinhoResponse.estoque(), is(50));
    }

    @Test
    public void testDelete() {
        VinhoDTO vinho = new VinhoDTO("Valtier Reserva", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1);
        Long id = vinhoService.insert(vinho).id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().delete("/vinhos/" + id)
                .then()
                .statusCode(204);

        VinhoResponseDTO vinhoResponse = null;
        try {
            vinhoResponse = vinhoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(vinhoResponse);
        }
    }
}
