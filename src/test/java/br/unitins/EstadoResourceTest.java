package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.EstadoDTO;
import br.unitins.dto.EstadoResponseDTO;
import br.unitins.service.EstadoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class EstadoResourceTest {
    @Inject
    EstadoService estadoService;

    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new AuthUsuarioDTO("duda", "123");

         Response response = (Response) given()
                .contentType("application/json")
                .body(auth)
                .when().post("/auth")
                .then()
                .statusCode(200)
                .extract().response();
        
        token = response.header("Authorization");
    }

    @Test
    public void testGetAll() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/estados")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertEstadoTest() {
        EstadoDTO estado = new EstadoDTO("Tocantins", "TO");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(estado)
                .when().post("/estados")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma Estado no banco de dados
        EstadoDTO estado = new EstadoDTO(
                "Tocantins",
                "TO");
        Long id = estadoService.insert(estado).id();

        // Criando outro Estado para atuailzacao
        EstadoDTO estadoUpdate = new EstadoDTO(
                "Tocantiins",
                "TO");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(estadoUpdate)
                .when().put("/estados/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        EstadoResponseDTO EstadoResponse = estadoService.findById(id);
        assertThat(EstadoResponse.nome(), is("Tocantiins"));
        assertThat(EstadoResponse.sigla(), is("TO"));
    }

    @Test
    public void testDelete() {
        // Adicionando um Estado no banco de dados
        EstadoDTO estado = new EstadoDTO(
                "Lucas",
                "TO");
        Long id = estadoService.insert(estado).id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().delete("/estados/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        EstadoResponseDTO estadoResponse = null;
        try {
            estadoResponse = estadoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(estadoResponse);
        }
    }

}
