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
import br.unitins.dto.CompraResponseDTO;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.service.EnderecoService;
import org.jboss.logging.Logger;

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {
    @Inject
    EnderecoService enderecoService;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<EnderecoResponseDTO> getAll() {
        LOG.info("Buscando todas os endereços.");
        LOG.debug("ERRO DE DEBUG.");
        return enderecoService.getAll();
    }

    @POST
    @RolesAllowed({ "Admin", "User" })
    public Response insert(EnderecoDTO dto) {
        LOG.infof("Inserindo um endereço: %s", dto.getClass());
        Result result = null;
        try {
            EnderecoResponseDTO endereco = enderecoService.insert(dto);
            LOG.infof("Endereço (%d) criado com sucesso.", endereco.id());
            EnderecoResponseDTO enderecoPersistido = enderecoService.findById(endereco.id());
            if (enderecoPersistido == null) {
                result = new Result("Falha na persistência do endereço");
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }
            return Response.status(Status.CREATED).entity(endereco).build();
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
    public Response update(@PathParam("id") Long id, EnderecoDTO dto) {
        LOG.infof("Atualizando endereço: %s", dto.getClass());
        Result result = null;
        try {
            EnderecoResponseDTO endereco = enderecoService.update(id, dto);
            LOG.infof("Cidade (%d) atualizada com sucesso.", endereco.id());
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar uma cidade.");
            LOG.debug(e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        } catch (NullPointerException e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage());
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();

    }

    @DELETE
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Result result = null;
        try {
            enderecoService.delete(id);
            LOG.infof("Endereço (%d) apagado com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao apagar um endereço.");
            LOG.debug(e.getMessage());
            result = new Result(e.getMessage());
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/{id}")
    public EnderecoResponseDTO findById(@PathParam("id") Long id) {
        return enderecoService.findById(id);
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/count")
    public long count() {
        return enderecoService.count();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/search/{cep}")
    public List<EnderecoResponseDTO> search(@PathParam("cep") String cep) {
        return enderecoService.findByCep(cep);
    }
}
