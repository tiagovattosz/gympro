package br.edu.fema.gympro.dto.manutencao;

import br.edu.fema.gympro.domain.enums.Situacao;

public record ManutencaoUpdateDTO(
        Long funcionarioId,
        Long equipamentoId,
        String descricao,
        Situacao situacao,
        Boolean realizada
) {
}
