
package br.unitins.resource;

import java.util.List;
import org.jboss.logging.Logger;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.application.Result;
import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;
import br.unitins.dto.PagamentoResponseDTO;
import br.unitins.service.PagamentoService;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {
    @Inject
    PagamentoService pagamentoService;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<PagamentoResponseDTO> getAll() {
        LOG.info("Buscando todas as compras.");
        LOG.debug("ERRO DE DEBUG.");
        return pagamentoService.getAll();
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response insert(CompraDTO dto) {
        LOG.infof("Inserindo um brinquedo: %s", dto.getClass());
        Result result = null;
        try {
            CompraResponseDTO pagamento = pagamentoService.insert(dto);
            LOG.infof("Pagamento (%d) criado com sucesso.", pagamento.id());
            return Response.status(Status.CREATED).entity(pagamento).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um brinquedo.");
            LOG.debug(e.getMessage());
        }catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CompraDTO dto) {
        try {
            CompraResponseDTO pagamento = pagamentoService.update(id, dto);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        } catch (NullPointerException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        }
    }
    
    @DELETE
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Result result = null;
        try {
            pagamentoService.delete(id);
            LOG.infof("Pagamento (%d) apagada com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
           LOG.error("Erro ao apagar uma cidade.");
            LOG.debug(e.getMessage());
            result = new Result(e.getMessage());
            
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }


}
