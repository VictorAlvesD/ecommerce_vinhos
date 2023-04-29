package br.unitins.resource;
import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import br.unitins.application.Result;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.service.EnderecoService;

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {
    @Inject
    EnderecoService enderecoService;

    @GET
    public List<EnderecoResponseDTO> getAll() {
        return enderecoService.getAll();
    }

    @POST
    public Response insert(EnderecoDTO dto) {
        try {
            EnderecoResponseDTO endereco = enderecoService.insert(dto);
            return Response.status(Status.CREATED).entity(endereco).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EnderecoDTO dto) {
        try {
            EnderecoResponseDTO endereco = enderecoService.update(id, dto);
            return Response.ok(endereco).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }catch (NullPointerException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            enderecoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/{id}")
    public EnderecoResponseDTO findById(@PathParam("id") Long id) {
        return enderecoService.findById(id);
    }

    @GET
    @Path("/count")
    public long count() {
        return enderecoService.count();
    }

    @GET
    @Path("/search/{cep}")
    public List<EnderecoResponseDTO> search(@PathParam("cep") String cep) {
        return enderecoService.findByCep(cep);
    }
}
