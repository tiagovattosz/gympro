package br.edu.fema.gympro.dto.entradasaida;

import jakarta.validation.constraints.NotBlank;

public record EntradaSaidaCreateDTO (
        @NotBlank(message = "A matrícula não pode estar em branco!")
        String matricula
) {
}
