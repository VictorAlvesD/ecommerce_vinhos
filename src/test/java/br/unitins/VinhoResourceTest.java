package br.unitins;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import br.unitins.dto.VinhoDTO;
import br.unitins.dto.VinhoResponseDTO;
import br.unitins.service.VinhoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class VinhoResourceTest {
    @Inject
    VinhoService vinhoService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/vinhos")
                .then()
                .statusCode(200);
    }

    @Test
    public void insertVinhoTest() {
        VinhoDTO vinho = new VinhoDTO("Valtier Reserva",15,(double) 50,"13%","Produzido a partir das uvas Tempranillo Bobal.","Tempranillo Bobal",1,1);

        given()
                .contentType(ContentType.JSON)
                .body(vinho)
                .when().post("/vinhos")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        // Adicionando uma vinho no banco de dados
        VinhoDTO vinho = new VinhoDTO(
            "Valtier Reserva",15,(double) 50,"13%","Produzido a partir das uvas Tempranillo Bobal.","Tempranillo Bobal",1,1);
        Long id = vinhoService.insert(vinho).id();

        // Criando outro vinho para atuailzacao
        VinhoDTO vinhoUpdate = new VinhoDTO(
            "Valtier Reserva",15,(double) 50,"13%",
            "Produzido a partir das uvas Tempranillo Bobal.","Tempranillo Bobal",1,1);

        given()
                .contentType(ContentType.JSON)
                .body(vinhoUpdate)
                .when().put("/vinhos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        VinhoResponseDTO vinhoResponse = vinhoService.findById(id);
        assertThat(vinhoResponse.nome(), is("Valtier Reserva"));
        assertThat(vinhoResponse.estoque(), is(15));
        assertThat(vinhoResponse.teorAlcoolico(), is("13%"));
        assertThat(vinhoResponse.tipoUva(), is("Produzido a partir das uvas Tempranillo Bobal."));
        assertThat(vinhoResponse.tipoVinho(), is(1));
        assertThat(vinhoResponse.produtor(), is(1));
    }

    @Test
    public void testDelete() {
        // Adicionando um vinho no banco de dados
        VinhoDTO vinho = new VinhoDTO(
            "Valtier Reserva",15,(double) 50,"13%","Produzido a partir das uvas Tempranillo Bobal.","Tempranillo Bobal",1,1);
        Long id = vinhoService.insert(vinho).id();

        given()
                .when().delete("/vinhos/" + id)
                .then()
                .statusCode(204);

        // verificando se a pessoa fisica foi excluida
        VinhoResponseDTO vinhoResponse = null;
        try {
            vinhoResponse = vinhoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(vinhoResponse);
        }
    }

    @Test
    public void testFindById() {
        given()
                .when().get("/vinhos/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .when().get("/vinhos/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .when().get("/vinhos/search/João")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

 

    // Teste de método POST com validação de campos obrigatórios:
    @Test
    public void testInsertSemCamposPreenchidos() {
        VinhoDTO dto = new VinhoDTO("",15,(double) 50,"13%","Produzido a partir das uvas Tempranillo Bobal.","Tempranillo Bobal",1,1); // dto sem nome
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().post("/vinhos")
                .then()
                .statusCode(404); 
    }

    // Teste de método PUT com validação de existência de vinho:
    @Test
    public void testUpdateIdInexistente() {
        VinhoDTO dto = new VinhoDTO("Valtier Reserva Utiel-Requena DOP",15,(double) 50,"13%","Produzido a partir das uvas Tempranillo Bobal.","Tempranillo Bobal",1,1);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .when().put("/vinhos/12") // id inválido
                .then()
                .statusCode(404); 
    }

    // Teste de método DELETE com validação de existência de vinho:
    @Test
    public void testDeleteIdInexistente() {
        given()
                .when().delete("/vinhos/115") // id inválido
                .then()
                .statusCode(404); 
    }
}
