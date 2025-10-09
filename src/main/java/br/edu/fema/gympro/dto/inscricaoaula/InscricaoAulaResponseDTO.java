package br.edu.fema.gympro.dto.inscricaoaula;

public record InscricaoAulaResponseDTO(
        Long id,
        String clienteNome,
        String professorNome,
        String aulaDescricao,
        String dataInscricao
) {}
