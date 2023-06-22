
package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;
import br.unitins.dto.PagamentoDTO;
import br.unitins.service.CompraService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class CompraResourceTest {
    @Inject
    CompraService compraService;
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
                .when().get("/compras")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertcompraTest() {
        CompraDTO compra = new CompraDTO();
        PagamentoDTO compra = new PagamentoDTO();

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(compra)
                .when().post("/compras")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma compra no banco de dados
        CompraDTO compra = new CompraDTO(
                "Palmas",
                (long) 1);
        Long id = compraService.insert(compra).id();

        // Criando outro compra para atuailzacao
        CompraDTO compraUpdate = new CompraDTO(
                "Goiatins",
                (long) 2);

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(compraUpdate)
                .when().put("/compras/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        CompraResponseDTO compraResponse = compraService.findById(id);
        assertThat(compraResponse.nome(), is("Goiatins"));
    }

    @Test
    public void testDelete() {
        // Adicionando um compra no banco de dados
        CompraDTO compra = new CompraDTO(
                "Paraiso",
                (long) 1);
        Long id = compraService.insert(compra).id();

        given()
        .header("Authorization", "Bearer " + token)
                .when().delete("/compras/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        CompraResponseDTO compraResponse = null;
        try {
            compraResponse = compraService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(compraResponse);
        }
    }

}
