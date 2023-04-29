package br.unitins;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import br.unitins.dto.CidadeDTO;
import br.unitins.dto.CidadeResponseDTO;
import br.unitins.service.CidadeService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class CidadeResourceTest {
    @Inject
    CidadeService cidadeService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/cidades")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertcidadeTest() {
        CidadeDTO cidade = new CidadeDTO("Palmas",1);

        given()
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
                1);
        Long id = cidadeService.insert(cidade).id();

        // Criando outro cidade para atuailzacao
        CidadeDTO cidadeUpdate = new CidadeDTO(
                "Goiatins",
                2);

        given()
                .contentType(ContentType.JSON)
                .body(cidadeUpdate)
                .when().put("/cidades/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        CidadeResponseDTO cidadeResponse = cidadeService.findById(id);
        assertThat(cidadeResponse.nome(), is("Palmas"));
        assertThat(cidadeResponse.estado(), is(1));
    }

    @Test
    public void testDelete() {
        // Adicionando um cidade no banco de dados
        CidadeDTO cidade = new CidadeDTO(
                "Paraiso",
                1);
        Long id = cidadeService.insert(cidade).id();

        given()
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

    @Test
    public void testFindById() {
        given()
                .when().get("/cidades/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .when().get("/cidades/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .when().get("/cidades/search/João")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }



    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        CidadeDTO dto = new CidadeDTO("",1); // dto sem nome
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/cidades")
                .then()
                .statusCode(404); 
    }

    // Teste de método PUT com validação de existência de cidade:
    @Test
    public void testUpdateIdInexistente() {
        CidadeDTO dto = new CidadeDTO("Fatima", 2);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/cidades/22") // id inválido
                .then()
                .statusCode(404); 
    }

    // Teste de método DELETE com validação de existência de cidade:
    @Test
    public void testDeleteIdInexistente() {
        given()
                .when().delete("/cidades/999") // id inválido
                .then()
                .statusCode(404); 
    }
}
