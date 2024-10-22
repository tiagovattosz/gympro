package br.edu.fema.gympro.dto.planocliente;

public record PlanoClienteResponseDTO(
        Long id,
        String nome,
        String plano,
        String dataInicio,
        String dataTermino
) {
}
