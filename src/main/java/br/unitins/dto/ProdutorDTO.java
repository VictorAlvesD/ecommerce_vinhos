package br.unitins.dto;

import br.unitins.model.Produtor;
import jakarta.validation.constraints.NotBlank;

public record ProdutorDTO(
        @NotBlank(message = "O campo nome deve ser informado.") 
        String nome,
        @NotBlank(message = "O campo nome deve ser informado.") 
        String pais) {
    }
