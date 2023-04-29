package br.unitins.dto;

import java.util.List;

import br.unitins.model.Endereco;
import br.unitins.model.Telefone;
import br.unitins.model.Usuario;
import br.unitins.model.Vinho;

public record UsuarioResponseDTO(
        String nome,
        String cpf,
        String senha,
        String email,
        Telefone telefone,
        List<Endereco> endereco,
        List<Vinho> vinhosListaDesejos)
{
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getNome(), usuario.getCpf(), usuario.getSenha(), usuario.getEmail(), usuario.getTelefone(),
                usuario.getEnderecos(), usuario.getVinhosListaDesejos());
    }
}
