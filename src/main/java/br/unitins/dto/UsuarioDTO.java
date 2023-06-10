package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsuarioDTO(
        @NotBlank(message = "O campo nome deve ser informado.") String nome,

        @NotBlank(message = "O campo CPF deve ser informado.") 
        @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}",
         message = "O Número deve estar no formato 999.999.999-99") String cpf,

        @NotBlank(message = "O campo senha deve ser informado.") @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "A senha deve ter 6 caracteres alfanuméricos") String senha,
        @NotBlank(message = "O campo email deve ser informado.") String email,
        @NotNull Long telefone,
        @NotNull Long endereco,
        @NotNull Long vinhosListaDesejos) {


    }


