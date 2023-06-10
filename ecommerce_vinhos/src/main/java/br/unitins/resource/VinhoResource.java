package br.unitins.resource;
import java.util.List;

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
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        vinhoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
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
