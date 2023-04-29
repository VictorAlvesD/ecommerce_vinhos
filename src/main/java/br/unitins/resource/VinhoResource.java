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
import br.unitins.dto.VinhoDTO;
import br.unitins.dto.VinhoResponseDTO;
import br.unitins.service.VinhoService;
@Path("/vinhos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VinhoResource {
    @Inject
    VinhoService vinhoService;

    
    @GET
    public List<VinhoResponseDTO> getAll() {
        return vinhoService.getAll();
    }

    @POST
    public Response insert(VinhoDTO dto) {
        try {
            VinhoResponseDTO vinho = vinhoService.insert(dto);
            return Response.status(Status.CREATED).entity(vinho).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, VinhoDTO dto) {
        try {
            VinhoResponseDTO vinho = vinhoService.update(id, dto);
            return Response.ok(vinho).build();
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
            vinhoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/{id}")
    public VinhoResponseDTO findById(@PathParam("id") Long id) {
        return vinhoService.findById(id);
    }

    @GET
    @Path("/count")
    public long count() {
        return vinhoService.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<VinhoResponseDTO> search(@PathParam("nome") String nome) {
        return vinhoService.findByNome(nome);
    }
}
