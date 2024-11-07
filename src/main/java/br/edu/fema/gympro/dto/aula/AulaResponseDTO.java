package br.edu.fema.gympro.dto.aula;

public record AulaResponseDTO(
        Long id,
        String modalidadeNome,
        String professorNome,
        String diaDaSemana,
        String horario,
        Integer numeroInscricoes,
        Integer maximoInscricoes
) {}
