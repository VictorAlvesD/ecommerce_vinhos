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

import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;
import br.unitins.dto.ItemCompraResponseDTO;
import br.unitins.dto.PagamentoResponseDTO;
import br.unitins.model.Compra;
import br.unitins.model.ItemCompra;
import br.unitins.model.StatusPagamento;
import br.unitins.model.TipoPagamento;
import br.unitins.repository.CompraRepository;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {

    @Inject
    CompraRepository compraRepository;

    @Inject
    Validator validator;

    @Override
    public List<CompraResponseDTO> getAll() {
        List<Compra> list = compraRepository.listAll();
        return list.stream()
            .map(u -> CompraResponseDTO.valueOf(u))
            .collect(Collectors.toList());
    }

    @Override
    public CompraResponseDTO findById(Long id) {
        Compra compra = compraRepository.findById(id);
        if (compra == null)
            throw new NotFoundException("Compra não encontrada!");
        return CompraResponseDTO.valueOf(compra);
    }

    private void validarId(Compra compra) throws ConstraintViolationException {
        if (compra.getId() == null)
            throw new NullPointerException("Id inválido");
    }

    @Override
    @Transactional
    public CompraResponseDTO insert(CompraDTO compraDTO) throws ConstraintViolationException {
        validar(compraDTO);

        Compra entity = new Compra();
        entity.setStatus(StatusPagamento.valueOf(compraDTO.statusPagamento()));
        entity.setTipo(TipoPagamento.valueOf(compraDTO.tipoPagamento()));

        compraRepository.persist(entity);

        return CompraResponseDTO.valueOf(entity);
    }

    private void validar(CompraDTO compraDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<CompraDTO>> violations = validator.validate(compraDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public CompraResponseDTO update(Long id, CompraDTO compraDTO) throws ConstraintViolationException {
        validar(compraDTO);

        Compra entity = compraRepository.findById(id);
        if (entity == null)
            throw new NotFoundException("Compra não encontrada!");

        entity.setStatus(StatusPagamento.valueOf(compraDTO.statusPagamento()));
        entity.setTipo(TipoPagamento.valueOf(compraDTO.tipoPagamento()));

        return CompraResponseDTO.valueOf(entity);
    }


    @Override
    public void delete(Long id) {
        Compra compra = compraRepository.findById(id);
        validarId(compra);
        compraRepository.delete(compra);
    }
}
