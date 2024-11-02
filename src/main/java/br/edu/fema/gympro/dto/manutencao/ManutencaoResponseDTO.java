package br.edu.fema.gympro.dto.manutencao;

import br.edu.fema.gympro.domain.enums.Situacao;

public record ManutencaoResponseDTO(
        Long id,
        String nomeFuncionario,
        String nomeEquipamento,
        String descricao,
        Situacao situacao,
        Boolean realizada,
        String dataSolicitacao,
        String dataResposta,
        String dataRealizacao
) {
}
