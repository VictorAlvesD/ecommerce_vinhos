package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.CidadeDTO;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EstadoDTO;
import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.dto.VinhoDTO;
import br.unitins.service.CidadeService;
import br.unitins.service.EnderecoService;
import br.unitins.service.EstadoService;
import br.unitins.service.TelefoneService;
import br.unitins.service.UsuarioService;
import br.unitins.service.VinhoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class UsuarioResourceTest {
    @Inject
    UsuarioService usuarioService;

    @Inject
    TelefoneService telefoneService;

    @Inject
    EstadoService estadoService;

    @Inject
    CidadeService cidadeService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    VinhoService vinhosListaDesejos;

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
                        .when().get("/usuarios")
                                .then()
                                        .statusCode(200);
    }

    @Test
    public void insertUsuarioTest() {
        Long idestado = estadoService.insert(new EstadoDTO("Pára", "PA")).id();

        Long idcidade = cidadeService.insert(new CidadeDTO("Belém", idestado)).id();

        Long idendereco = enderecoService.insert(new EnderecoDTO(
                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", idcidade)).id();

        Long idTelefone = telefoneService.insert(new TelefoneDTO("63", "99999-9999")).id();

        Long idListaDesejo = vinhosListaDesejos.insert(new VinhoDTO("Valtier Reserva", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1)).id();

        UsuarioDTO usuarioDTO = new UsuarioDTO("Luahr", "999.999.999-99", "victor", "lulu", 1, idTelefone, idendereco,
                idListaDesejo);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(usuarioDTO)
                .when().post("/usuarios")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {

        Long idestado = estadoService.insert(new EstadoDTO("Pára", "PA")).id();

        Long idcidade = cidadeService.insert(new CidadeDTO("Belém", idestado)).id();

        Long idendereco = enderecoService.insert(new EnderecoDTO(
                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", idcidade)).id();

        Long idTelefone = telefoneService.insert(new TelefoneDTO("63", "99999-9999")).id();

        Long idListaDesejo = vinhosListaDesejos.insert(new VinhoDTO("Valtier Reserva", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1)).id();

        UsuarioDTO usuarioDTO = new UsuarioDTO("Luahr", "999.999.999-99", "victor", "lulu", 1, idTelefone, idendereco,
                idListaDesejo);
                
        Long idUser = usuarioService.insert(usuarioDTO).id();

        UsuarioDTO usuarioDTOUpdate = new UsuarioDTO("Vithin", "999.999.999-99", "victor", "vito", 1, idTelefone, idendereco,
                idListaDesejo);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(usuarioDTOUpdate)
                .when()
                .put("/usuarios/" + idUser)
                .then()
                .statusCode(204);

        UsuarioResponseDTO usuarioResponseDTO = usuarioService.findById(idUser);
        assertThat(usuarioResponseDTO.nome(), is("Vithin"));
        assertThat(usuarioResponseDTO.email(), is("vito"));
    }

    @Test
    public void testDelete() {
        Long idestado = estadoService.insert(new EstadoDTO("Pára", "PA")).id();

        Long idcidade = cidadeService.insert(new CidadeDTO("Belém", idestado)).id();

        Long idendereco = enderecoService.insert(new EnderecoDTO(
                "Alameda 01", "35", "Taquari", "Sem complemento", "99999-999", idcidade)).id();

        Long idTelefone = telefoneService.insert(new TelefoneDTO("63", "99999-9999")).id();

        Long idListaDesejo = vinhosListaDesejos.insert(new VinhoDTO("Valtier Reserva", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1)).id();

        UsuarioDTO usuarioDTO = new UsuarioDTO("Luahr", "999.999.999-99", "victor", "lulu", 1, idTelefone, idendereco,
                idListaDesejo);

        Long id = usuarioService.insert(usuarioDTO).id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().delete("/usuarios/" + id)
                .then()
                .statusCode(204);

        UsuarioResponseDTO usuarioResponseDTO = null;
        try {
            usuarioResponseDTO = usuarioService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(usuarioResponseDTO);
        }
    }
        
}
