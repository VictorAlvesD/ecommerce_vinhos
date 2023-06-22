package br.unitins.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.dto.CidadeDTO;
import br.unitins.dto.CidadeResponseDTO;
import br.unitins.dto.EstadoResponseDTO;
import br.unitins.model.Cidade;
import br.unitins.repository.CidadeRepository;
import br.unitins.repository.EstadoRepository;

@ApplicationScoped
public class CidadeServiceImpl implements CidadeService {
    @Inject
    CidadeRepository cidadeRepository;
    @Inject
    EstadoRepository estadoRepository;
    @Inject
    Validator validator;

    @Override
    public List<CidadeResponseDTO> getAll() {
        return cidadeRepository.findAll()
                .stream()
                .map(CidadeResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CidadeResponseDTO findById(Long id) {
        Cidade cidade = cidadeRepository.findById(id);
        if (cidade == null)
            throw new NotFoundException("Cidade não encontrada no banco.");
        return new CidadeResponseDTO(cidade);
    }

    @Override
    @Transactional
    public CidadeResponseDTO insert(CidadeDTO cidadeDTO) throws ConstraintViolationException {
        validar(cidadeDTO);

        Cidade entity = new Cidade();
        entity.setNome(cidadeDTO.nome());
        entity.setEstado(estadoRepository.findByID(cidadeDTO.estado().longValue()));

        cidadeRepository.persist(entity);

        return new CidadeResponseDTO(entity);
    }

    private void validar(CidadeDTO cidadeDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<CidadeDTO>> violations = validator.validate(cidadeDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public CidadeResponseDTO update(Long id, CidadeDTO cidadeDTO) throws ConstraintViolationException {
        validar(cidadeDTO);

        Cidade entity = cidadeRepository.findById(id);

        validarId(entity);
        entity.setNome(cidadeDTO.nome());
        entity.setEstado(estadoRepository.findByID(cidadeDTO.estado().longValue()));

        cidadeRepository.persist(entity);

        return new CidadeResponseDTO(entity);
    }

    private void validarId(Cidade cidade) throws ConstraintViolationException {
        if (cidade.getId() == null)
            throw new NullPointerException("Id inválido");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(cidadeRepository.findById(id));
        cidadeRepository.deleteById(id);
    }

    @Override
    public List<CidadeResponseDTO> findByNome(String nome) {
        List<Cidade> list = cidadeRepository.findByNome(nome);
        return list.stream().map(CidadeResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return cidadeRepository.count();
    }

}
