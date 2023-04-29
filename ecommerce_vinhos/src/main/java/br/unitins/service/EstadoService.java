package br.unitins.service;

import java.util.List;
import br.unitins.dto.EstadoResponseDTO;
import br.unitins.dto.EstadoDTO;

public interface EstadoService {
    List<EstadoResponseDTO> getAll();

    EstadoResponseDTO findById(Long id);

    EstadoResponseDTO insert(EstadoDTO estadoDTO);

    EstadoResponseDTO update(Long id, EstadoDTO estadoDTO);

    void delete(Long id);

    List<EstadoResponseDTO> findBySigla(String sigla);

    long count();
}
