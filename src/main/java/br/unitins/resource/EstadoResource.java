package br.unitins.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import br.unitins.dto.EstadoResponseDTO;
import br.unitins.dto.EstadoDTO;
import br.unitins.service.EstadoService;
import org.jboss.logging.Logger;

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {
    @Inject
    EstadoService estadoService;

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<EstadoResponseDTO> getAll() {
        LOG.info("Buscando todos os estados.");
        LOG.debug("Debug de busca de lista de estados.");
        return estadoService.getAll();
    }

    @POST
    @Transactional
    @RolesAllowed({ "Admin", "User" })
    public Response insert(EstadoDTO dto) {
        LOG.infof("Inserindo uma cidade: %s", dto.getClass());
        try {
            EstadoResponseDTO estado = estadoService.insert(dto);
            LOG.infof("Estado (%d) criado com sucesso.", estado.id());
            return Response.status(Status.CREATED).entity(estado).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma estado.");
            LOG.debug(e.getMessage());
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EstadoDTO dto) {
        LOG.infof("Atualizando uma cidade: %s", dto.getClass());
        Result result = null;
        try {
            EstadoResponseDTO estado = estadoService.update(id, dto);
            LOG.infof("Cidade (%d) atualizada com sucesso.", estado.id());
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar uma cidade.");
            LOG.debug(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage());

        }
        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Result result = null;
        try {
            estadoService.delete(id);
            LOG.infof("Estado (%d) apagado com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao apagar uma cidade.");
            LOG.debug(e.getMessage());
            result = new Result(e.getMessage());
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public EstadoResponseDTO findById(@PathParam("id") Long id) {
        return estadoService.findById(id);
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/count")
    public long count() {
        return estadoService.count();
    }

    @GET
    @Path("/search/{sigla}")
    public List<EstadoResponseDTO> search(@PathParam("sigla") String sigla) {
        return estadoService.findBySigla(sigla);
    }
}
