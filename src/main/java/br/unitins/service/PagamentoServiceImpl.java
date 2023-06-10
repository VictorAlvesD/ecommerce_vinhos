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
import br.unitins.dto.PagamentoDTO;
import br.unitins.dto.PagamentoResponseDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.model.Pagamento;
import br.unitins.model.StatusPagamento;
import br.unitins.model.TipoPagamento;
import br.unitins.model.TipoVinho;
import br.unitins.repository.PagamentoRepository;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {
    @Inject
    PagamentoRepository pagamentoRepository;
    @Inject
    Validator validator;

    @Override
    public List<PagamentoResponseDTO> getAll() {
        List<Pagamento> list = pagamentoRepository.listAll();
        return list.stream().map(u -> PagamentoResponseDTO.valueOf(u)).collect(Collectors.toList());
    }

    @Override
    public PagamentoResponseDTO findById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null)
            throw new NotFoundException("Pagamento não encontrado!");
        return new PagamentoResponseDTO(pagamento);
    }

    private void validarId(Pagamento pagamento) throws ConstraintViolationException {
        if (pagamento.getId() == null)
            throw new NullPointerException("Id inválido");
    }

    @Override
    @Transactional
    public PagamentoResponseDTO insert(PagamentoDTO pagamentoDTO) throws ConstraintViolationException {
        validar(pagamentoDTO);

        Pagamento entity = new Pagamento();
        entity.setQuantidadeParcelas(pagamentoDTO.quantidadeParcelas());
        entity.setValorParcelas(pagamentoDTO.valorParcelas());
        entity.setStatusPagamento(StatusPagamento.valueOf(pagamentoDTO.statusPagamento()));
        entity.setTipoPagamento(TipoPagamento.valueOf(pagamentoDTO.tipoPagamento()));

        pagamentoRepository.persist(entity);

        return new PagamentoResponseDTO(entity);
    }

    private void validar(PagamentoDTO pagamentoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<PagamentoDTO>> violations = validator.validate(pagamentoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public PagamentoResponseDTO update(Long id, PagamentoDTO pagamentoDTO) throws ConstraintViolationException {
        validar(pagamentoDTO);

        Pagamento entity = pagamentoRepository.findById(id);
        validarId(entity);
        entity.setQuantidadeParcelas(pagamentoDTO.quantidadeParcelas());
        entity.setValorParcelas(pagamentoDTO.valorParcelas());
        entity.setStatusPagamento(StatusPagamento.valueOf(pagamentoDTO.statusPagamento()));
        entity.setTipoPagamento(TipoPagamento.valueOf(pagamentoDTO.tipoPagamento()));

        pagamentoRepository.persist(entity);

        return new PagamentoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(pagamentoRepository.findById(id));
        pagamentoRepository.deleteById(id);
    }

    @Override
    public CompraResponseDTO insert(CompraDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public CompraResponseDTO update(Long id, CompraDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
