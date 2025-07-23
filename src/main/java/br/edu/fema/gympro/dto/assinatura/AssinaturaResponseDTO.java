package br.edu.fema.gympro.dto.assinatura;

public record AssinaturaResponseDTO (
        String nomeCliente,
        String plano,
        String dataInicio,
        String dataTermino
) {
}
