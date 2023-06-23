package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.TelefoneResponseDTO;
import br.unitins.service.TelefoneService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class TelefoneResourceTest {
    @Inject
    TelefoneService telefoneService;
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
                .when().get("/telefones")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertTelefoneTest() {
        TelefoneDTO telefoneDTO = new TelefoneDTO("(63)", "99999-0044");

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(telefoneDTO)
                .when().post("/telefones")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma endereco no banco de dados
        TelefoneDTO telefoneDTO = new TelefoneDTO(
                "(63)", "99999-0044");
        Long id = telefoneService.insert(telefoneDTO).id();

        // Criando outro endereco para atuailzacao
        TelefoneDTO telefoneUpdate = new TelefoneDTO(
                "(63)", "99999-0044");

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(telefoneUpdate)
                .when().put("/telefones/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        TelefoneResponseDTO enderecoResponse = telefoneService.findById(id);
        assertThat(enderecoResponse.codigoArea(), is("(63)"));
        assertThat(enderecoResponse.numero(), is("99999-0044"));
    }

    @Test
    public void testDelete() {
        // Adicionando um endereco no banco de dados
        TelefoneDTO endereco = new TelefoneDTO(
                "(63)", "99999-0044");
        Long id = telefoneService.insert(endereco).id();

        given()
        .header("Authorization", "Bearer " + token)
                .when().delete("/telefones/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        TelefoneResponseDTO telefoneResponse = null;
        try {
            telefoneResponse = telefoneService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(telefoneResponse);
        }
    }

}
