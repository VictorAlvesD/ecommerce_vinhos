package br.unitins.dto;

import br.unitins.model.Cidade;
import br.unitins.model.Endereco;

public record EnderecoResponseDTO(
        Long id,
        String cep,
        String rua,
        String numero,
        String complemento,
        Cidade cidade) {
    public EnderecoResponseDTO(Endereco endereco) {
        this(endereco.getId(),endereco.getCep(), endereco.getRua(), endereco.getBairro(), endereco.getComplemento(),
                endereco.getCidade());
    }


}