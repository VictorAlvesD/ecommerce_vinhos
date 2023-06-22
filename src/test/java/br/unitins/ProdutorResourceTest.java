package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.ProdutorDTO;
import br.unitins.dto.ProdutorResponseDTO;
import br.unitins.service.ProdutorService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class ProdutorResourceTest {
    @Inject
    ProdutorService produtorService;
    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new AuthUsuarioDTO("dudadelorusso@gmail.com", "123");

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
                .when().get("/produtores")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertProdutorTest() {
        ProdutorDTO produtor = new ProdutorDTO("Lagoas de Minas", "Brazil");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(produtor)
                .when().post("/produtores")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma produtor no banco de dados
        ProdutorDTO produtor = new ProdutorDTO(
                "Dias",
                "EUA");
        Long id = produtorService.insert(produtor).id();

        // Criando outro produtor para atuailzacao
        ProdutorDTO produtorUpdate = new ProdutorDTO(
                "Day",
                "Inglaterra");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(produtorUpdate)
                .when().put("/produtores/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        ProdutorResponseDTO produtorResponse = produtorService.findById(id);
        assertThat(produtorResponse.nome(), is("Day"));
        assertThat(produtorResponse.pais(), is("Inglaterra"));
    }

    @Test
    public void testDelete() {
        // Adicionando um produtor no banco de dados
        ProdutorDTO produtor = new ProdutorDTO(
                "Silva e Familia",
                "Paraguai");
        Long id = produtorService.insert(produtor).id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().delete("/produtores/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        ProdutorResponseDTO produtorResponse = null;
        try {
            produtorResponse = produtorService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(produtorResponse);
        }
    }

}