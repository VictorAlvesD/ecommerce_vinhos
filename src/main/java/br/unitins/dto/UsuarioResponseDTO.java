package br.unitins.dto;

import br.unitins.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        TelefoneResponseDTO telefone,
        EnderecoResponseDTO endereco,
        String nomeImagem
        )
        {

    public UsuarioResponseDTO(Usuario usuario)
    {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getCpf(),
            usuario.getEmail(),
            new TelefoneResponseDTO(usuario.getTelefone()),
            new EnderecoResponseDTO(usuario.getEndereco()),
            usuario.getNomeImagem()
            );
    }
}
