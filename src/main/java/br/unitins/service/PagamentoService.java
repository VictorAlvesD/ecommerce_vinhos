package br.unitins.service;

import java.util.List;

import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;
import br.unitins.dto.PagamentoDTO;
import br.unitins.dto.PagamentoResponseDTO;

public interface PagamentoService {
    List<PagamentoResponseDTO> getAll();

    PagamentoResponseDTO findById(Long id);

    PagamentoResponseDTO insert(PagamentoDTO pagamentoDTO);

    PagamentoResponseDTO update(Long id, PagamentoDTO pagamentoDTO);

    void delete(Long id);

    CompraResponseDTO insert(CompraDTO dto);

    CompraResponseDTO update(Long id, CompraDTO dto);

    // Outros métodos específicos, se necessário
}
