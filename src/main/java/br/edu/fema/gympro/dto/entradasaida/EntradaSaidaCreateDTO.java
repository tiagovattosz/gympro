package br.edu.fema.gympro.dto.entradasaida;

import jakarta.validation.constraints.NotBlank;

public record EntradaSaidaCreateDTO (
        @NotBlank(message = "O cpf não pode estar em branco!")
        String cpf
) {
}
