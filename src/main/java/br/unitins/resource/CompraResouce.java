package br.unitins.resource;

import java.util.List;

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
import br.unitins.service.CompraService;
import org.jboss.logging.Logger;
@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResouce {
    @Inject
    CompraService compraService;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<CompraResponseDTO> getAll() {
        LOG.info("Buscando todas as compras.");
        LOG.debug("ERRO DE DEBUG.");
        return compraService.getAll();
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response insert(CompraDTO dto) {
        LOG.infof("Inserindo uma compra: %s", dto.getClass());
        Result result = null;
        try {
            CompraResponseDTO compra = compraService.insert(dto);
            return Response.status(Status.CREATED).entity(compra).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma cidade.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
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
        LOG.infof("Atualizando um brinquedo: %s", dto.getClass());
        Result result = null;
        try {
            CompraResponseDTO compra = compraService.update(id, dto);
            LOG.infof("Brinquedo (%d) apagado com sucesso.", compra.getClass());
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
             LOG.error("Erro ao apagar um brinquedo.");
            LOG.debug(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }
        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }
    
    @DELETE
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            compraService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public CompraResponseDTO findById(@PathParam("id") Long id) {
        return compraService.findById(id);
    }
}
