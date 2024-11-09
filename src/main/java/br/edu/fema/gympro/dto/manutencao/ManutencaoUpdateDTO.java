package br.edu.fema.gympro.dto.manutencao;

import br.edu.fema.gympro.domain.enums.Situacao;
import jakarta.validation.constraints.NotNull;

public record ManutencaoUpdateDTO(
        Long funcionarioId,

        @NotNull(message = "O equipamento não pode ser nulo!")
        Long equipamentoId,

        String descricao,

        Situacao situacao,

        Boolean realizada
) {
}
