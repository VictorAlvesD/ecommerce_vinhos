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
import br.unitins.dto.ProdutorDTO;
import br.unitins.dto.ProdutorResponseDTO;
import br.unitins.service.ProdutorService;

@Path("/produtores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutorResource {
    
    @Inject
    ProdutorService produtorService;

    @GET
    public List<ProdutorResponseDTO> getAll() {
        return produtorService.getAll();
    }

    @GET
    @Path("/{id}")
    public ProdutorResponseDTO findById(@PathParam("id") Long id) {
        return produtorService.findById(id);
    }

    @POST
    public Response insert(ProdutorDTO dto) {
        try {
            ProdutorResponseDTO produtor = produtorService.insert(dto);
            return Response.status(Status.CREATED).entity(produtor).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ProdutorDTO dto) {
        try {
            ProdutorResponseDTO produtor = produtorService.update(id, dto);
            return Response.ok(produtor).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }      
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        produtorService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }


    @GET
    @Path("/count")
    public long count(){
        return produtorService.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<ProdutorResponseDTO> search(@PathParam("nome") String nome){
        return produtorService.findByNome(nome);  
    }
}