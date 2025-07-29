package br.edu.fema.gympro.dto.entradasaida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EntradaSaidaCreateDTO (
        @NotBlank(message = "A matrícula não pode estar em branco.")
        @Size(min = 6, max = 6, message = "A matricula deve ter 6 caracteres.")
        String matricula
) {
}
