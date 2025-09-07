package br.edu.fema.gympro.dto.aula;

public record AulaResponseDTO(
        Long id,
        Long modalidadeId,
        String modalidadeNome,
        Long professorId,
        String professorNome,
        String diaDaSemana,
        String horario,
        Integer numeroInscricoes,
        Integer maximoInscricoes
) {}
