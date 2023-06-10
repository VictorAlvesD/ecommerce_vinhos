package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;


public record CidadeDTO(
    @NotBlank(message = "O campo nome deve ser informado.") String nome,
    Integer estado
    ){
}
