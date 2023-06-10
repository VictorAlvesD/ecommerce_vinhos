package br.unitins.service;

import java.util.List;

import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.model.Usuario;

public interface UsuarioService {
    List<UsuarioResponseDTO> getAll();

    void delete(Long id);
    
    UsuarioResponseDTO findById(Long id);

    List<UsuarioResponseDTO> findByCpf(String cpf);

    public Usuario findByLoginAndSenha(String email, String senha);

    public UsuarioResponseDTO findByLogin(String email);

    long count();
}
