package br.edu.fema.gympro.dto.manutencao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ManutencaoCreateDTO(
        Long funcionarioId,

        @NotNull(message = "O equipamento não pode ser nulo!")
        Long equipamentoId,

        @NotBlank(message = "A descrição não pode estar em branco!")
        String descricao
) {
}
