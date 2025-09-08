package br.edu.fema.gympro.dto.aula;

import java.util.List;

public record AulaDetailsDTO(
        Long id,
        Long modalidadeId,
        String modalidadeNome,
        Long professorId,
        String professorNome,
        String diaDaSemana,
        String horario,
        Integer numeroInscricoes,
        Integer maximoInscricoes,
        List<AlunosInscritosDTO> alunosInscritos
) {}
