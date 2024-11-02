package br.edu.fema.gympro.dto.manutencao;

public record ManutencaoCreateDTO(
        Long funcionarioId,
        Long equipamentoId,
        String descricao
) {
}
