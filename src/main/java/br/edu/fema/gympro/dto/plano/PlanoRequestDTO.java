package br.edu.fema.gympro.dto.plano;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlanoRequestDTO(@NotBlank(message = "A descricao do plano não pode estar em branco!")
                              @Size(max = 255, message = "A descrição plano não pode ter mais de 255 caracteres!")
                              String descricao,

                              @Min(value = 0, message = "O número máximo de inscrições não pode ser negativo!")
                              Integer maximoInscricoes) {
}
