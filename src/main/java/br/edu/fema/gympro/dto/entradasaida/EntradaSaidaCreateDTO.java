package br.edu.fema.gympro.dto.entradasaida;

import jakarta.validation.constraints.NotBlank;

public record EntradaSaidaCreateDTO (@NotBlank Long idCliente) {
}
