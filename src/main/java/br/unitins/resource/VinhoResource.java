package br.unitins.resource;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.application.Result;
import br.unitins.dto.VinhoDTO;
import br.unitins.dto.VinhoResponseDTO;
import br.unitins.form.ImageForm;
import br.unitins.model.Vinho;
import br.unitins.repository.VinhoRepository;
import br.unitins.service.FileService;
import br.unitins.service.VinhoService;

@Path("/vinhos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VinhoResource {
    @Inject
    VinhoService vinhoService;

    @Inject
    FileService fileService;

    @Inject
    VinhoRepository vinhoRepository;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<VinhoResponseDTO> getAll() {
        LOG.info("Buscando todos os cidades.");
        LOG.debug("ERRO DE DEBUG.");
        return vinhoService.getAll();
    }

    @POST
    @RolesAllowed({ "Admin", "User" })
    public Response insert(VinhoDTO dto) {
        LOG.infof("Inserindo uma vinho: %s", dto.getClass());
        Result result = null;
        try {
            VinhoResponseDTO vinho = vinhoService.insert(dto);
            LOG.infof("Vinho (%d) criado com sucesso.", vinho.id());
            return Response.status(Status.CREATED).entity(vinho).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um vinho.");
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
    public Response update(@PathParam("id") Long id, VinhoDTO dto) {
        LOG.infof("Atualizando uma vinho: %s", dto.getClass());
        Result result = null;
        try {
            VinhoResponseDTO vinho = vinhoService.update(id, dto);
            LOG.infof("vinho (%d) atualizada com sucesso.", vinho.id());
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar uma vinho.");
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
            vinhoService.delete(id);
            LOG.infof("vinho (%d) apagada com sucesso.");
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
    public VinhoResponseDTO findById(@PathParam("id") Long id) {
        return vinhoService.findById(id);
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/count")
    public long count() {
        return vinhoService.count();
    }

    @GET
    @RolesAllowed({ "Admin", "User" })
    @Path("/search/{nome}")
    public List<VinhoResponseDTO> search(@PathParam("nome") String nome) {
        return vinhoService.findByNome(nome);
    }

    @PATCH
    @Path("/novaimagem")
    @RolesAllowed({ "Admin", "User" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form, VinhoResponseDTO dto) {
        String nomeImagem = "";

        try {
            nomeImagem = fileService.salvarImagemUsuario(form.getImagem(), form.getNomeImagem());
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }
        Vinho vinho = vinhoRepository.findById(dto.id());
        vinho = vinhoService.alterarImagem(vinho.getId(), nomeImagem);
        return Response.ok(vinho).build();
    }

    @GET
    @Path("/download/{nomeImagem}")
    @RolesAllowed({ "Admin", "User" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    }
}
