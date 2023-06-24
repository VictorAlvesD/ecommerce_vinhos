package br.unitins.service;
import java.util.List;
import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;

public interface CompraService {
   List<CompraResponseDTO> getAll();

    CompraResponseDTO findById(Long id);

    CompraResponseDTO create(CompraDTO compradto);

    CompraResponseDTO update(Long id, CompraDTO compradto);

    void delete(Long id);


    Long count();
}
