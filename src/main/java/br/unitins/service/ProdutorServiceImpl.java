package br.unitins.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.dto.ProdutorDTO;
import br.unitins.dto.ProdutorResponseDTO;
import br.unitins.model.Produtor;
import br.unitins.repository.ProdutorRepository;

@ApplicationScoped
public class ProdutorServiceImpl implements ProdutorService {

    @Inject
    ProdutorRepository produtorRepository;

    @Inject
    Validator validator;

    @Override
    public List<ProdutorResponseDTO> getAll() {
        List<Produtor> list = produtorRepository.listAll();
        return list.stream().map(ProdutorResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProdutorResponseDTO findById(Long id) {
        Produtor produtor = produtorRepository.findById(id);
        if (produtor == null)
            throw new NotFoundException("Produtor não encontrado.");
        return new ProdutorResponseDTO(produtor);
    }

    @Override
    @Transactional
    public ProdutorResponseDTO insert(ProdutorDTO produtorDTO) throws ConstraintViolationException {
        validar(produtorDTO);

        Produtor entity = new Produtor();
        entity.setNome(produtorDTO.nome());
        entity.setPais(produtorDTO.pais());

        produtorRepository.persist(entity);

        return new ProdutorResponseDTO(entity);
    }

    @Override
    @Transactional
    public ProdutorResponseDTO update(Long id, ProdutorDTO produtorDTO) throws ConstraintViolationException{
        validar(produtorDTO);
   
        Produtor entity = produtorRepository.findById(id);
        validarId(entity);
        entity.setNome(produtorDTO.nome());
        entity.setPais(produtorDTO.pais());
        return new ProdutorResponseDTO(entity);
    }

    private void validar(ProdutorDTO produtorDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ProdutorDTO>> violations = validator.validate(produtorDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }
    private void validarId(Produtor produtor) throws ConstraintViolationException {
        if (produtor.getId() == null)
            throw new NullPointerException("Id inválido");
    }
    @Override
    @Transactional
    public void delete(Long id) {
        validarId(produtorRepository.findById(id));
        produtorRepository.deleteById(id);
    }

    @Override
    public List<ProdutorResponseDTO> findByNome(String nome) {
        List<Produtor> list = produtorRepository.findByNome(nome);
        return list.stream().map(ProdutorResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return produtorRepository.count();
    }

}