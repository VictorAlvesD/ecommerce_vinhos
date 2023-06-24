package br.unitins.service;

import java.util.List;

import br.unitins.dto.PagamentoDTO;
import br.unitins.dto.PagamentoResponseDTO;

public interface PagamentoService {
    List<PagamentoResponseDTO> getAll();

    PagamentoResponseDTO findById(Long id);

    PagamentoResponseDTO create(PagamentoDTO pagamentoDTO);

    PagamentoResponseDTO update(Long id, PagamentoDTO pagamentoDTO);

    void delete(Long id);

    List<PagamentoResponseDTO> findByPagamentos(String nome);

    long count();
}
