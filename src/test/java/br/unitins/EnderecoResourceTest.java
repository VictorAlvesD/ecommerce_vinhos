package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.service.EnderecoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class EnderecoResourceTest {
    @Inject
    EnderecoService enderecoService;
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
                .when().get("/enderecos")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertEnderecoTest() {
        EnderecoDTO endereco = new EnderecoDTO("Alameda 01", "35", "Taquari"
        , "Sem complemento", "99999-999", 1);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(endereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .body(
                    "id", notNullValue(),
              	 "rua", is("Alameda 01"),
             	 "numero", is("35"),
                 "bairro", is("Taquari")
                 "complemento", is("Sem complemento")
                 "cep", is("99999-999")
                 "cidade", 1);
    }

    @Test
    public void testUpdate() {
        // Adicionando um endereço no banco de dados
        EnderecoDTO endereco = new EnderecoDTO(
                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", 1);
        Long id = enderecoService.insert(endereco).id();

        // Criando outro endereço para atualização
        EnderecoDTO enderecoUpdate = new EnderecoDTO(
                "Alameda 36", "30", "Centro", "Perto dali", "25879-999", 1);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(enderecoUpdate)
                .when().put("/enderecos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        EnderecoResponseDTO enderecoResponse = enderecoService.findById(id);
        assertThat(enderecoResponse.cep(), is("25879-999"));
    }

    @Test
    public void testDelete() {
        // Adicionando um endereco no banco de dados
        EnderecoDTO endereco = new EnderecoDTO(
                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", 1);
        Long id = enderecoService.insert(endereco).id();

        given()
                .header("Authorization", "Bearer " + token)
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
        // Adicionando um endereco no banco de dados
        EnderecoDTO endereco = new EnderecoDTO(
                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", 1);
        Long id = enderecoService.insert(endereco).id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/enderecos/" + id)
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    private ResponseAwareMatcher<Response> equalTo(String string) {
        return null;
    }

    @Test
    public void testCount() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/enderecos/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/enderecos/search/97859-256")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        EnderecoDTO dto = new EnderecoDTO("", "35", "Taquari", "Sem complemento", "99999-999", 1); // dto sem rua
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/enderecoes")
                .then()
                .statusCode(404);
    }

    // Teste de método PUT com validação de existência de endereco:
    @Test
    public void testUpdateIdInexistente() {
        EnderecoDTO dto = new EnderecoDTO("Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", 1);
        given()
                .header("Authorization", "Bearer " + token)
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
                .header("Authorization", "Bearer " + token)
                .when().delete("/enderecos/999") // id inválido
                .then()
                .statusCode(404);
    }
}
