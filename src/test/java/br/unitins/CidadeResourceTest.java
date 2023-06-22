package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.CidadeDTO;
import br.unitins.dto.CidadeResponseDTO;
import br.unitins.service.CidadeService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class CidadeResourceTest {
    @Inject
    CidadeService cidadeService;
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
                .when().get("/cidades")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertcidadeTest() {
        CidadeDTO cidade = new CidadeDTO("Palmas", (long) 2);

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(cidade)
                .when().post("/cidades")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma cidade no banco de dados
        CidadeDTO cidade = new CidadeDTO(
                "Palmas",
                (long) 1);
        Long id = cidadeService.insert(cidade).id();

        // Criando outro cidade para atuailzacao
        CidadeDTO cidadeUpdate = new CidadeDTO(
                "Goiatins",
                (long) 2);

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(cidadeUpdate)
                .when().put("/cidades/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        CidadeResponseDTO cidadeResponse = cidadeService.findById(id);
        assertThat(cidadeResponse.nome(), is("Goiatins"));
    }

    @Test
    public void testDelete() {
        // Adicionando um cidade no banco de dados
        CidadeDTO cidade = new CidadeDTO(
                "Paraiso",
                (long) 1);
        Long id = cidadeService.insert(cidade).id();

        given()
        .header("Authorization", "Bearer " + token)
                .when().delete("/cidades/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        CidadeResponseDTO cidadeResponse = null;
        try {
            cidadeResponse = cidadeService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(cidadeResponse);
        }
    }
}
