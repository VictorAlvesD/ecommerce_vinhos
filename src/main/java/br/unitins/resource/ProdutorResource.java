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
import br.unitins.dto.ProdutorDTO;
import br.unitins.dto.ProdutorResponseDTO;
import br.unitins.service.ProdutorService;
import org.jboss.logging.Logger;

@Path("/produtores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutorResource {

    @Inject
    ProdutorService produtorService;
    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<ProdutorResponseDTO> getAll() {
        LOG.info("Buscando todos os cidades.");
        LOG.debug("ERRO DE DEBUG.");
        return produtorService.getAll();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public ProdutorResponseDTO findById(@PathParam("id") Long id) {
        return produtorService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin", "User" })
    public Response insert(ProdutorDTO dto) {
        LOG.infof("Inserindo uma cidade: %s", dto.getClass());
        Result result = null;
        try {
            ProdutorResponseDTO produtor = produtorService.insert(dto);
            LOG.infof("Cidade (%d) criado com sucesso.", produtor.id());
            return Response.status(Status.CREATED).entity(produtor).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma cidade.");
            LOG.debug(e.getMessage());
             result = new Result(e.getConstraintViolations());
        }catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);}
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @RolesAllowed({ "Admin", "User"})
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ProdutorDTO dto) {
         LOG.infof("Atualizando uma cidade: %s", dto.getClass());
        Result result = null;
        try {
            ProdutorResponseDTO produtor = produtorService.update(id, dto);
            LOG.infof("Cidade (%d) atualizada com sucesso.", produtor.id());
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
             LOG.error("Erro ao atualizar uma cidade.");
            LOG.debug(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        }  catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage());
        }
            return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            produtorService.delete(id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/count")
    public long count() {
        return produtorService.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<ProdutorResponseDTO> search(@PathParam("nome") String nome) {
        return produtorService.findByNome(nome);
    }
}