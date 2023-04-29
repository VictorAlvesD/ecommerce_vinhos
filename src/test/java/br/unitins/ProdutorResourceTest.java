package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import br.unitins.dto.ProdutorDTO;
import br.unitins.dto.ProdutorResponseDTO;
import br.unitins.service.ProdutorService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class ProdutorResourceTest {
    @Inject
    ProdutorService produtorService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/produtores")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertProdutorTest() {
        ProdutorDTO produtor = new ProdutorDTO("Lagoas de Minas", "Brazil");

        given()
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

    @Test
    public void testFindById() {
        given()
                .when().get("/produtores/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .when().get("/produtores/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .when().get("/produtores/search/João")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    // Teste de método GET que espera um resultado específico:
    @Test
    public void testFindById2() {
        ProdutorResponseDTO expectedResponse = new ProdutorResponseDTO( (long) 1,"Marqués del Atrio", "Espanha");
        ProdutorResponseDTO actualResponse = given()
                .when().get("/produtores/1")
                .then()
                .statusCode(200)
                .extract().as(ProdutorResponseDTO.class);
        assertEquals(expectedResponse, actualResponse);
    }

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        ProdutorDTO dto = new ProdutorDTO("","Brasil"); // dto sem nome
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/produtores")
                .then()
                .statusCode(404); 
    }

    // Teste de método PUT com validação de existência de produtor:
    @Test
    public void testUpdateIdInexistente() {
        ProdutorDTO dto = new ProdutorDTO("Família Silva", "Italia");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/produtores/22") // id inválido
                .then()
                .statusCode(404); 
    }

    // Teste de método DELETE com validação de existência de produtor:
    @Test
    public void testDeleteIdInexistente() {
        given()
                .when().delete("/produtores/999") // id inválido
                .then()
                .statusCode(404); 
    }


}