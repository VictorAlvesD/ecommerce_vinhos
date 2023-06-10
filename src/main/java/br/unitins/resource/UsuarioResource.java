package br.unitins.resource;

import java.util.List;

import jakarta.annotation.security.PermitAll;
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
import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.service.UsuarioService;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class UsuarioResource {
    @Inject
    UsuarioService usuarioService;
private static final Logger LOG = Logger.getLogger(CidadeResource.class);
    @GET
    @RolesAllowed({"Admin", "User"})
    public List<UsuarioResponseDTO> getAll() {
        LOG.info("Buscando todos os cidades.");
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.getAll();
    }

    @DELETE
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Result result = null;
        try {
            usuarioService.delete(id);
            LOG.infof("usuario (%d) apagado com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao apagar um usuario.");
            LOG.debug(e.getMessage());
            result = new Result(e.getMessage());
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public UsuarioResponseDTO findById(@PathParam("id") Long id) {
        return usuarioService.findById(id);
    }
    
    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/count")
    public long count() {
        return usuarioService.count();
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/search/{cpf}")
    public List<UsuarioResponseDTO> search(@PathParam("cpf") String cpf) {
        return usuarioService.findByCpf(cpf);
    }
    
}
