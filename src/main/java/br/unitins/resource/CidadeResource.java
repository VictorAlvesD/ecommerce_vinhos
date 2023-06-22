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
import org.jboss.logging.Logger;

import br.unitins.application.Result;
import br.unitins.dto.CidadeDTO;
import br.unitins.dto.CidadeResponseDTO;
import br.unitins.service.CidadeService;

@Path("/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {
    @Inject
    CidadeService cidadeService;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<CidadeResponseDTO> getAll() {
        LOG.info("Buscando todos os cidades.");
        LOG.debug("ERRO DE DEBUG.");
        return cidadeService.getAll();
    }

    @POST
    @Transactional
    @RolesAllowed({ "Admin", "User" })
    public Response insert(CidadeDTO dto) {
        LOG.infof("Inserindo uma cidade: %s", dto.getClass());
        try {
            CidadeResponseDTO cidade = cidadeService.insert(dto);
            LOG.infof("Cidade (%d) criado com sucesso.", cidade.id());
            return Response.status(Status.CREATED).entity(cidade).build();
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
    public Response update(@PathParam("id") Long id, CidadeDTO dto) {
        LOG.infof("Atualizando uma cidade: %s", dto.getClass());
        Result result = null;
        try {
            CidadeResponseDTO cidade = cidadeService.update(id, dto);
            LOG.infof("Cidade (%d) atualizada com sucesso.", cidade.id());
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
            cidadeService.delete(id);
            LOG.infof("Cidade (%d) apagada com sucesso.");
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
    public CidadeResponseDTO findById(@PathParam("id") Long id) {
        return cidadeService.findById(id);
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/count")
    public long count() {
        return cidadeService.count();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/search/{nome}")
    public List<CidadeResponseDTO> search(@PathParam("nome") String nome) {
        return cidadeService.findByNome(nome);
    }
}
