package br.unitins.service;

import java.util.List;
import br.unitins.dto.CidadeDTO;
import br.unitins.dto.CidadeResponseDTO;

public interface CidadeService {
    List<CidadeResponseDTO> getAll();

    CidadeResponseDTO findById(Long id);

    CidadeResponseDTO insert(CidadeDTO cidadeDTO);

    CidadeResponseDTO update(Long id, CidadeDTO cidadeDTO);

    void delete(Long id);

    List<CidadeResponseDTO> findByNome(String nome);

    long count();
}
