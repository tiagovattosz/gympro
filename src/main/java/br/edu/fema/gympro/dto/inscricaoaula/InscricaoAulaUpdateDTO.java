package br.edu.fema.gympro.dto.inscricaoaula;

import jakarta.validation.constraints.NotNull;

public record InscricaoAulaUpdateDTO(
        @NotNull(message = "O cliente não pode ser nulo!")
        Long clienteId,

        @NotNull(message = "A aula não pode ser nula!")
        Long aulaId
) {}
