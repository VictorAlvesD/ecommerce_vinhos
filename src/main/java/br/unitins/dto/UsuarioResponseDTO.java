package br.unitins.dto;

import java.util.List;

import br.unitins.model.Endereco;
import br.unitins.model.Telefone;
import br.unitins.model.Usuario;
import br.unitins.model.Vinho;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        Telefone telefone,
        Endereco endereco) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return new UsuarioResponseDTO(
                    usuario.getId(),
                    null,
                    null,
                    usuario.getEmail(),
                    null,
                    null);
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getEndereco()
                );
    }
}
