package br.edu.fema.gympro.dto.entradasaida;

public record EntradaSaidaResponseDTO (
        Long id,
        Long pessoaId,
        String matricula,
        String nome,
        String cpf,
        String tipoPessoa,
        String data,
        String hora,
        String tipoMovimentacao
        ) {
}
