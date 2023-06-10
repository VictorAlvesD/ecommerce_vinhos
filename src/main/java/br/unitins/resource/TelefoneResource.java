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
import org.jboss.logging.Logger;

import br.unitins.application.Result;
import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.TelefoneResponseDTO;
import br.unitins.service.TelefoneService;

@Path("/telefones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    @Inject
    TelefoneService telefoneService;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<TelefoneResponseDTO> getAll() {
        LOG.info("Buscando todos os telefones.");
        LOG.debug("ERRO DE DEBUG.");
        return telefoneService.getAll();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public TelefoneResponseDTO findById(@PathParam("id") Long id) {
        return telefoneService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin", "User" })
    public Response insert(TelefoneDTO dto) {
        LOG.infof("Inserindo uma cidade: %s", dto.getClass());
        Result result = null;
        try {
            TelefoneResponseDTO telefone = telefoneService.insert(dto);
            LOG.infof("Telefone (%d) criado com sucesso.", telefone.id());
            return Response.status(Status.CREATED).entity(telefone).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma cidade.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TelefoneDTO dto) {
        try {
            TelefoneResponseDTO telefone = telefoneService.update(id, dto);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        } catch (NullPointerException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Result result = null;
        try {
            telefoneService.delete(id);
             LOG.infof("telefone (%d) apagada com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        }catch (NullPointerException e) {
            LOG.error("Erro ao apagar uma cidade.");
            LOG.debug(e.getMessage());
            result = new Result(e.getMessage());
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/count")
    public long count() {
        return telefoneService.count();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/search/{numero}")
    public List<TelefoneResponseDTO> search(@PathParam("numero") String numero) {
        return telefoneService.findByNumero(numero);
    }
}
