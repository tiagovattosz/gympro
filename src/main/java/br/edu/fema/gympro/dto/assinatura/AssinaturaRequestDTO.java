package br.edu.fema.gympro.dto.assinatura;

import jakarta.validation.constraints.NotNull;

public record AssinaturaRequestDTO(
        @NotNull(message = "O cliente não pode ser nulo!")
        Long clienteId,

        @NotNull(message = "O plano não pode ser nulo!")
        Long planoId,

        String dataInicioAssinatura
) {
}
