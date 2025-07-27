package br.edu.fema.gympro.dto.entradasaida;

public record EntradaSaidaResponseDTO (
        Long id,
        Long pessoaId,
        String nome,
        String tipoPessoa,
        String data,
        String hora,
        String tipoMovimentacao
        ) {
}
