package br.unitins.service;

import java.util.List;

import br.unitins.dto.VinhoDTO;
import br.unitins.dto.VinhoResponseDTO;
import br.unitins.model.Vinho;

public interface VinhoService {
    List<VinhoResponseDTO> getAll();

    VinhoResponseDTO insert(VinhoDTO vinhoDTO);

    VinhoResponseDTO update(Long id, VinhoDTO vinhoDTO);

    void delete(Long id);
    
    VinhoResponseDTO findById(Long id);

    List<VinhoResponseDTO> findByNome(String nome);

    long count();

    Vinho alterarImagem(Long id, String nomeImagem);

}
