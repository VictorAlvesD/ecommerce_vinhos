package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.CidadeDTO;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.dto.EstadoDTO;
import br.unitins.model.Cidade;
import br.unitins.service.CidadeService;
import br.unitins.service.EnderecoService;
import br.unitins.service.EstadoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class EnderecoResourceTest {
        @Inject
        EnderecoService enderecoService;

        @Inject
        CidadeService cidadeService;

        @Inject
        EstadoService estadoService;

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
                                .when().get("/enderecos")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void insertEnderecoTest() {
                Long idestado = estadoService.insert(new EstadoDTO("Pára", "PA")).id();
                Long idcidade = cidadeService.insert(new CidadeDTO("Belém", idestado)).id();
                EnderecoDTO endereco = new EnderecoDTO(
                                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", idcidade);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(endereco)
                                .when().post("/enderecos")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(),
                                                "rua", is("Alameda 01"),
                                                "numero", is("35"),
                                                "bairro", is("Taquari"),
                                                "complemento", is("Sem complemento"),
                                                "cep", is("99999-999"),
                                                "cidade", notNullValue(Cidade.class));
        }

        @Test
        public void testUpdate() {
                // Adicionando um endereço no banco de dados
                Long idestado = estadoService.insert(new EstadoDTO("Pára", "PA")).id();
                Long idcidade = cidadeService.insert(new CidadeDTO("Belém", idestado)).id();
                EnderecoDTO endereco = new EnderecoDTO(
                                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", idcidade);

                Long id = enderecoService.insert(endereco).id();

                // Criando outro endereço para atualização
                EnderecoDTO enderecoUpdate = new EnderecoDTO(
                                "Rua Victor Alves", "35", "Taquari", "Sem complemento", "99987-999", idcidade);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(enderecoUpdate)
                                .when().put("/enderecos/" + id)
                                .then()
                                .statusCode(204);

                // Verificando se os dados foram atualizados no banco de dados
                EnderecoResponseDTO enderecoResponse = enderecoService.findById(id);
                assertThat(enderecoResponse.rua(), is("Rua Victor Alves"));
                assertThat(enderecoResponse.numero(), is("35"));
                assertThat(enderecoResponse.bairro(), is("Taquari"));
                assertThat(enderecoResponse.complemento(), is("Sem complemento"));
                assertThat(enderecoResponse.cep(), is("99987-999"));
                assertThat(enderecoResponse.cidade(), notNullValue());
        }

        @Test
        public void testDelete() {
                // Adicionando um endereco no banco de dados
                Long idestado = estadoService.insert(new EstadoDTO("Pára", "PA")).id();
                Long idcidade = cidadeService.insert(new CidadeDTO("Belém", idestado)).id();
                EnderecoDTO endereco = new EnderecoDTO(
                                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", idcidade);
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
}
