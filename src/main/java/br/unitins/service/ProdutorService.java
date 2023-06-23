package br.unitins.service;

import java.util.List;

import br.unitins.dto.ProdutorDTO;
import br.unitins.dto.ProdutorResponseDTO;

public interface ProdutorService {

    List<ProdutorResponseDTO> getAll();

    ProdutorResponseDTO findByLogin(String login);

    ProdutorResponseDTO findById(Long id);

    ProdutorResponseDTO insert(ProdutorDTO produtorDTO);

    ProdutorResponseDTO update(Long id, ProdutorDTO produtorDTO);

    void delete(Long id);

    List<ProdutorResponseDTO> findByNome(String nome);

    long count();
}
