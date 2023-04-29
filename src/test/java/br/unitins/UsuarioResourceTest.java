package br.unitins;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.service.UsuarioService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class UsuarioResourceTest {
    @Inject
    UsuarioService usuarioService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/usuarios")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertUsuarioTest() {
        UsuarioDTO usuario = new UsuarioDTO("Duda Delo Russo","001.002.003-78","DudaRusoo95844",
        "dudadelo@gmail.com",(Long) 1L,(Long) 1L,(Long) 1L);

        given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when().post("/usuarios")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma usuario no banco de dados
        UsuarioDTO usuario = new UsuarioDTO(
            "Duda Delo Russo","001.002.003-78","DudaRusoo95844",
        "dudadelo@gmail.com",(Long) 1L,(Long) 1L,(Long) 1L);
        Long id = usuarioService.insert(usuario).id();

        // Criando outro usuario para atuailzacao
        UsuarioDTO usuarioUpdate = new UsuarioDTO(
            "Duda  Russo","001.002.113-78","DudaRusoo95844",
            "dudadelo@gmail.com",(Long) 2L,(Long) 1L,(Long) 1L);

        given()
                .contentType(ContentType.JSON)
                .body(usuarioUpdate)
                .when().put("/usuarios/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        UsuarioResponseDTO usuarioResponse = usuarioService.findById(id);
        assertThat(usuarioResponse.nome(), is("Valtier Reserva"));
        assertThat(usuarioResponse.cpf(), is("001.002.113-78"));
        assertThat(usuarioResponse.senha(), is("DudaRusoo95844"));
        assertThat(usuarioResponse.email(), is("dudadelo@gmail.com"));
        assertThat(usuarioResponse.telefone(), is((Long) 2L));
        assertThat(usuarioResponse.endereco(), is((Long) 1L));
        assertThat(usuarioResponse.endereco(), is((Long) 1L));
    }

    @Test
    public void testDelete() {
        // Adicionando um usuario no banco de dados
        UsuarioDTO usuario = new UsuarioDTO(
            "Duda Delo Russo","001.002.003-78","DudaRusoo95844",
            "dudadelo@gmail.com",(Long) 1L,(Long) 1L,(Long) 1L);
        Long id = usuarioService.insert(usuario).id();

        given()
                .when().delete("/usuarios/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        UsuarioResponseDTO usuarioResponse = null;
        try {
            usuarioResponse = usuarioService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(usuarioResponse);
        }
    }

    @Test
    public void testFindById() {
        given()
                .when().get("/usuarios/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .when().get("/usuarios/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .when().get("/usuarios/search/João")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

 

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        UsuarioDTO dto = new UsuarioDTO("","001.002.003-78","DudaRusoo95844",
        "dudadelo@gmail.com",(Long) 1L,(Long) 1L,(Long) 1L); // dto sem nome
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/usuarios")
                .then()
                .statusCode(404); 
    }

    // Teste de método PUT com validação de existência de usuario:
    @Test
    public void testUpdateIdInexistente() {
        UsuarioDTO dto = new UsuarioDTO("Duda Delo Russo","001.002.003-78","DudaRusoo95844",
        "dudadelo@gmail.com",(Long) 1L,(Long) 1L,(Long) 1L);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/usuarios/12") // id inválido
                .then()
                .statusCode(404); 
    }

    // Teste de método DELETE com validação de existência de usuario:
    @Test
    public void testDeleteIdInexistente() {
        given()
                .when().delete("/usuarios/115") // id inválido
                .then()
                .statusCode(404); 
    }
}
