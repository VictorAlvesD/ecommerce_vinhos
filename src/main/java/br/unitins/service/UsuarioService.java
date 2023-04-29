package br.unitins.service;

import java.util.List;

import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.UsuarioResponseDTO;

public interface UsuarioService {
    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO insert(UsuarioDTO usuarioDTO);

    UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO);

    void delete(Long id);
    
    UsuarioResponseDTO findById(Long id);

    List<UsuarioResponseDTO> findByCpf(String cpf);

    long count();
}
