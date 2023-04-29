package br.unitins.service;

import java.util.List;

import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.TelefoneResponseDTO;

public interface TelefoneService {
    List<TelefoneResponseDTO> getAll();

    TelefoneResponseDTO findById(Long id);

    TelefoneResponseDTO insert(TelefoneDTO telefoneDTO);

    TelefoneResponseDTO update(Long id, TelefoneDTO telefoneDTO);

    void delete(Long id);

    List<TelefoneResponseDTO> findByNumero(String numero);

    long count();
}
