package br.edu.fema.gympro.dto.inscricaoaula;

public record InscricaoAulaResponseDTO(
        Long id,
        String clienteNome,
        String aulaDescricao,
        String dataInscricao
) {}
