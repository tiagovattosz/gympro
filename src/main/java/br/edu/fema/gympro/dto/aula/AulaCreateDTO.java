package br.edu.fema.gympro.dto.aula;

import br.edu.fema.gympro.domain.enums.DiaDaSemana;

public record AulaCreateDTO(
        Long modalidadeId,
        Long professorId,
        DiaDaSemana diaDaSemana,
        String horario
) {}
