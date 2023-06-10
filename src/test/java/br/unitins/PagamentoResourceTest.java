
package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.PagamentoDTO;
import br.unitins.dto.PagamentoResponseDTO;
import br.unitins.service.PagamentoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class PagamentoResourceTest {
    @Inject
    PagamentoService pagamentoService;
    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new AuthUsuarioDTO("dudadelo@gmail.com", "123");

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
                .when().get("/pagamentos")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertpagamentoTest() {
        PagamentoDTO pagamento = new PagamentoDTO("Palmas", (long) 2);

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(pagamento)
                .when().post("/pagamentos")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma pagamento no banco de dados
        PagamentoDTO pagamento = new PagamentoDTO(
                "Palmas",
                (long) 1);
        Long id = pagamentoService.insert(pagamento).id();

        // Criando outro pagamento para atuailzacao
        PagamentoDTO pagamentoUpdate = new PagamentoDTO(
                "Goiatins",
                (long) 2);

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(pagamentoUpdate)
                .when().put("/pagamentos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        PagamentoResponseDTO pagamentoResponse = pagamentoService.findById(id);
        assertThat(pagamentoResponse.nome(), is("Goiatins"));
    }

    @Test
    public void testDelete() {
        // Adicionando um pagamento no banco de dados
        PagamentoDTO pagamento = new PagamentoDTO(
                "Paraiso",
                (long) 1);
        Long id = pagamentoService.insert(pagamento).id();

        given()
        .header("Authorization", "Bearer " + token)
                .when().delete("/pagamentos/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        PagamentoResponseDTO pagamentoResponse = null;
        try {
            pagamentoResponse = pagamentoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(pagamentoResponse);
        }
    }

    @Test
    public void testFindById() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/pagamentos/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/pagamentos/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/pagamentos/search/João")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        PagamentoDTO dto = new PagamentoDTO("", (long) 1); // dto sem nome
        given()
        .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/pagamentos")
                .then()
                .statusCode(404);
    }

    // Teste de método PUT com validação de existência de pagamento:
    @Test
    public void testUpdateIdInexistente() {
        PagamentoDTO dto = new PagamentoDTO("Fatima", (long) 2);
        given()
        .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/pagamentos/22") // id inválido
                .then()
                .statusCode(404);
    }

    // Teste de método DELETE com validação de existência de pagamento:
    @Test
    public void testDeleteIdInexistente() {
        given()
        .header("Authorization", "Bearer " + token)
                .when().delete("/pagamentos/999") // id inválido
                .then()
                .statusCode(404);
    }
}
