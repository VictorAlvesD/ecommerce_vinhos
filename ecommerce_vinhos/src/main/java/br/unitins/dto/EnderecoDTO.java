package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(
        @NotBlank(message = "O campo rua deve ser informado.") String rua,
        @NotBlank(message = "O campo numero deve ser informado.") String numero,
        @NotBlank(message = "O campo bairro deve ser informado.") String bairro,
        String complemento,
        @NotBlank @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999") String cep,
        Integer cidade) {

}
