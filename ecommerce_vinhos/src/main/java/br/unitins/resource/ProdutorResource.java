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