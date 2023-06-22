package br.unitins.dto;

import br.unitins.model.Cidade;
import br.unitins.model.Endereco;

public record EnderecoResponseDTO(
        Long id,
        String rua,
        String numero,
        String bairro,
        String complemento,
        String cep,
        Cidade cidade) {
    public EnderecoResponseDTO(Endereco endereco) {
        this(endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getComplemento(),
                endereco.getCep(),
                endereco.getCidade());
    }

}
