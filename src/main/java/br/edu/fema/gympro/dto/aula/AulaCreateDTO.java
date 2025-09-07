package br.edu.fema.gympro.dto.aula;

import br.edu.fema.gympro.domain.enums.DiaDaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AulaCreateDTO(
        Long modalidadeId,

        Long professorId,

        @NotNull(message = "O dia da semana não pode ser nulo.")
        DiaDaSemana diaDaSemana,

        @NotBlank(message = "O horário não pode estar em branco.")
        String horario,

        @NotNull(message = "O máximo de inscrições não pode ser nulo.")
        @Positive(message = "O máximo de inscrições deve ser positivo.")
        Integer maximoInscricoes
) {}
