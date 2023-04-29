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

import br.unitins.dto.EstadoDTO;
import br.unitins.dto.EstadoResponseDTO;
import br.unitins.model.Estado;
import br.unitins.repository.EstadoRepository;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {
    @Inject
    EstadoRepository estadoRepository;

    @Inject
    Validator validator;

    @Override
    public List<EstadoResponseDTO> getAll() {
         List<Estado> list = estadoRepository.listAll();
        return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EstadoResponseDTO findById(Long id) {
        Estado estado = estadoRepository.findById(id);
        if (estado == null)
            throw new NotFoundException("Estado não encontrado no banco.");
        return new EstadoResponseDTO(estado);
    }

    @Override
    @Transactional
    public EstadoResponseDTO insert(EstadoDTO estadoDTO) throws ConstraintViolationException{
        validar(estadoDTO);

        Estado entity = new Estado();
        
        entity.setNome(estadoDTO.nome());
        entity.setSigla(estadoDTO.sigla());

        estadoRepository.persist(entity);

        return new EstadoResponseDTO(entity);
    }
    private void validar(EstadoDTO estadoDTO) throws ConstraintViolationException {
        Set <ConstraintViolation<EstadoDTO>> violations = validator.validate(estadoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public EstadoResponseDTO update(Long id, EstadoDTO estadoDTO)  throws ConstraintViolationException{
        validar(estadoDTO);
   
        Estado entity = estadoRepository.findById(id);
        validarId(entity);
        entity.setNome(estadoDTO.nome());
        entity.setSigla(estadoDTO.sigla());

        return new EstadoResponseDTO(entity);
    }
    private void validarId(Estado estado) throws ConstraintViolationException {
        if (estado.getId() == null)
            throw new NullPointerException("Id inválido");
    }
    @Override
    @Transactional
    public void delete(Long id) {
        validarId(estadoRepository.findById(id));
        estadoRepository.deleteById(id);
    }

    @Override
    public List<EstadoResponseDTO> findBySigla(String sigla) {
        List<Estado> list = estadoRepository.findBySigla(sigla);
        return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return estadoRepository.count();
    }

}