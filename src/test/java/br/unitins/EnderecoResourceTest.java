package br.unitins;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.service.EnderecoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class EnderecoResourceTest {
    @Inject
    EnderecoService enderecoService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/enderecos")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertEnderecoTest() {
        EnderecoDTO endereco = new EnderecoDTO("Alameda 01","35","Taquari","Sem complemento","99999-999",1);

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
        EnderecoDTO endereco = new EnderecoDTO(
            "Alameda 01","35","Taquari","Sem complemento","99999-999",1);
        Long id = enderecoService.insert(endereco).id();

        // Criando outro endereco para atuailzacao
        EnderecoDTO enderecoUpdate = new EnderecoDTO(
            "Alameda 36","35","Centro","Perto dali","25879-999",1);

        given()
                .contentType(ContentType.JSON)
                .body(enderecoUpdate)
                .when().put("/enderecos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        EnderecoResponseDTO enderecoResponse = enderecoService.findById(id);
        assertThat(enderecoResponse.rua(), is("Alameda 36"));
        assertThat(enderecoResponse.numero(), is("35"));
        assertThat(enderecoResponse.complemento(), is("Perto dali"));
        assertThat(enderecoResponse.cep(), is("25879-999"));
        assertThat(enderecoResponse.cidade(), is(1));
    }

    @Test
    public void testDelete() {
        // Adicionando um endereco no banco de dados
        EnderecoDTO endereco = new EnderecoDTO(
            "Alameda 01","35","Taquari","Sem complemento","99999-999",1);
        Long id = enderecoService.insert(endereco).id();

        given()
                .when().delete("/enderecos/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        EnderecoResponseDTO enderecoResponse = null;
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
                .when().get("/enderecos/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .when().get("/enderecos/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .when().get("/enderecos/search/97859-256")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        EnderecoDTO dto = new EnderecoDTO("","35","Taquari","Sem complemento","99999-999",1); // dto sem rua
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
        EnderecoDTO dto = new EnderecoDTO("Alameda 01","35","Taquari","Sem complemento","99999-999",1);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/enderecos/22") // id inválido
                .then()
                .statusCode(404); 
    }

    // Teste de método DELETE com validação de existência de endereco:
    @Test
    public void testDeleteIdInexistente() {
        given()
                .when().delete("/enderecos/999") // id inválido
                .then()
                .statusCode(404); 
    }
}
