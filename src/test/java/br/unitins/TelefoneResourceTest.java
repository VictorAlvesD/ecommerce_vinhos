package br.unitins;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.TelefoneResponseDTO;
import br.unitins.service.TelefoneService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class TelefoneResourceTest {
    @Inject
    TelefoneService enderecoService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/telefones")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertTelefoneTest() {
        TelefoneDTO endereco = new TelefoneDTO("(63)","99999-0044");

        given()
                .contentType(ContentType.JSON)
                .body(endereco)
                .when().post("/enderecoes")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma endereco no banco de dados
        TelefoneDTO endereco = new TelefoneDTO(
            "(63)","99999-0044");
        Long id = enderecoService.insert(endereco).id();

        // Criando outro endereco para atuailzacao
        TelefoneDTO enderecoUpdate = new TelefoneDTO(
            "(63)","99999-0044");

        given()
                .contentType(ContentType.JSON)
                .body(enderecoUpdate)
                .when().put("/telefones/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        TelefoneResponseDTO enderecoResponse = enderecoService.findById(id);
        assertThat(enderecoResponse.codigoArea(), is("(63)"));
        assertThat(enderecoResponse.numero(), is("99999-0044"));
    }

    @Test
    public void testDelete() {
        // Adicionando um endereco no banco de dados
        TelefoneDTO endereco = new TelefoneDTO(
            "(63)","99999-0044");
        Long id = enderecoService.insert(endereco).id();

        given()
                .when().delete("/telefones/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        TelefoneResponseDTO enderecoResponse = null;
        try {
            enderecoResponse = enderecoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(enderecoResponse);
        }
    }

    @Test
    public void testFindById() {
        given()
                .when().get("/telefones/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .when().get("/telefones/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .when().get("/telefones/search/97859-256")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        TelefoneDTO dto = new TelefoneDTO("","99999-0044"); // dto sem codigo 
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/enderecoes")
                .then()
                .statusCode(404); 
    }

    // Teste de método PUT com validação de existência de endereco:
    @Test
    public void testUpdateIdInexistente() {
        TelefoneDTO dto = new TelefoneDTO("(63)","99999-0044");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/telefones/22") // id inválido
                .then()
                .statusCode(404); 
    }

    // Teste de método DELETE com validação de existência de endereco:
    @Test
    public void testDeleteIdInexistente() {
        given()
                .when().delete("/telefones/999") // id inválido
                .then()
                .statusCode(404); 
    }
}
