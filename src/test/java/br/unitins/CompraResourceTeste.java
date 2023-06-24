package br.unitins;

import static io.restassured.RestAssured.given;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.CidadeDTO;
import br.unitins.dto.CompraDTO;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EstadoDTO;
import br.unitins.dto.ItemCompraDTO;
import br.unitins.dto.ProdutorDTO;
import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.VinhoDTO;
import br.unitins.service.CidadeService;
import br.unitins.service.CompraService;
import br.unitins.service.CompraServiceImpl;
import br.unitins.service.EnderecoService;
import br.unitins.service.EstadoService;
import br.unitins.service.ItemCompraService;
import br.unitins.service.ProdutorService;
import br.unitins.service.TelefoneService;
import br.unitins.service.UsuarioService;
import br.unitins.service.VinhoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

@QuarkusTest
public class CompraResourceTeste {
    @Inject
    CompraService compraService;

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

    @Inject
    ItemCompraService itemCompraService;

    @Inject
    VinhoService vinhoService;

    @Inject
    ProdutorService produtorService;

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
    public void testGetAll() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/compras")
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

        Long idusuario = usuarioService.insert(new UsuarioDTO("Luahr", "999.999.999-99", "victor", "lulu", 1, idTelefone, idendereco,
                idListaDesejo)).id();

        Long idProduto = vinhoService.insert(new VinhoDTO("Valtier Reserva", 15, (double) 50, "13%",
                "Produzido a partir das uvas Tempranillo Bobal.", "Tempranillo Bobal", 1, 1)).id();

        Long iditemcompra = itemCompraService.insert(new ItemCompraDTO(idProduto, (int) 2)).id();

        CompraDTO compraDTO = new CompraDTO(LocalDate.now(), (double) 12, iditemcompra, idusuario);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(compraDTO)
                .when().post("/compras")
                .then()
                .statusCode(201);
    }
}
