package br.unitins.service;

import java.util.List;

import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.model.Usuario;

public interface UsuarioService {
    List<UsuarioResponseDTO> getAll();

    void delete(Long id);
    
    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO insert(UsuarioDTO usuarioDTO);

    UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO);

    UsuarioResponseDTO update(Long id, String nomeImagem);

    UsuarioResponseDTO findByLogin(String login);

    public Usuario findByLoginAndSenha(String email, String senha);

    long count();
}
