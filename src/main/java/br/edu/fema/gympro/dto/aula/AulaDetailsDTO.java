package br.edu.fema.gympro.dto.aula;

import java.util.List;

public record AulaDetailsDTO(
        Long id,
        String modalidadeNome,
        String professorNome,
        String diaDaSemana,
        String horario,
        Integer numeroInscricoes,
        Integer maximoInscricoes,
        List<String> alunosInscritos
) {}
