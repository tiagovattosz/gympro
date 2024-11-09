package br.edu.fema.gympro.dto.entradasaida;

import jakarta.validation.constraints.NotNull;

public record EntradaSaidaCreateDTO (
        @NotNull(message = "O cliente não pode ser nulo!")
        Long idCliente
) {
}
