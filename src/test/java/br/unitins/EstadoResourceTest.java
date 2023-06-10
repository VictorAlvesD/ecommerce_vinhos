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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class EstadoResourceTest {
    @Inject
    EstadoService estadoService;
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
                "Tocantins",
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
        assertThat(EstadoResponse.nome(), is("Tocantins"));
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
        EstadoResponseDTO EstadoResponse = null;
        try {
            EstadoResponse = estadoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(EstadoResponse);
        }
    }

    @Test
    public void testFindById() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/estados/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/estados/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/estados/search/João")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    // Teste de método GET que espera um resultado específico:
    @Test
    public void testFindById2() {
        EstadoResponseDTO expectedResponse = new EstadoResponseDTO((long) 1, "Tocantins", "TO");
        EstadoResponseDTO actualResponse = given()
        .header("Authorization", "Bearer " + token)
                .when().get("/estados/1")
                .then()
                .statusCode(200)
                .extract().as(EstadoResponseDTO.class);
        assertEquals(expectedResponse, actualResponse);
    }

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        EstadoDTO dto = new EstadoDTO("", "Brasil"); // dto sem nome
        given()
        .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/estados")
                .then()
                .statusCode(404);
    }

    // Teste de método PUT com validação de existência de Estado:
    @Test
    public void testUpdateIdInexistente() {
        EstadoDTO dto = new EstadoDTO("Família Silva", "Italia");
        given()
        .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/estados/22") // id inválido
                .then()
                .statusCode(404);
    }

    // Teste de método DELETE com validação de existência de Estado:
    @Test
    public void testDeleteIdInexistente() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().delete("/estados/999") // id inválido
                .then()
                .statusCode(404);
    }
}
