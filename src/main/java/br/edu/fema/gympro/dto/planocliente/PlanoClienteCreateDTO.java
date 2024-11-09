package br.edu.fema.gympro.dto.planocliente;

import jakarta.validation.constraints.NotNull;

public record PlanoClienteCreateDTO(
        @NotNull(message = "O cliente não pode ser nulo!")
        Long clienteId,

        @NotNull(message = "O plano não pode ser nulo!")
        Long planoId
) {
}
