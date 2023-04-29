package br.unitins.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record VinhoDTO(
        @NotBlank(message = "O campo nome deve ser informado.") String nome,
        @NotNull int estoque,
        @NotNull @Positive(message = "O preco deve ser maior que zero!") Double preco,
        @NotBlank(message = "O Teor Alcoolico deve ser informado.") String teorAlcoolico,
        @NotBlank(message = "O tipo uva deve ser informada.") String tipoUva,
        @NotBlank(message = "A descricao deve ser informada.") String descricao,
        Integer tipoVinho,
        Integer produtor) {
}
