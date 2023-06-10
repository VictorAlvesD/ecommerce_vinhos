package br.unitins.service;
import java.util.List;
import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;

public interface CompraService {
    List<CompraResponseDTO> getAll();

    CompraResponseDTO findById(Long id);

    CompraResponseDTO insert(CompraDTO compraDTO);

    CompraResponseDTO update(Long id, CompraDTO compraDTO);

    void delete(Long id);

    // Outros métodos específicos, se necessário
}
