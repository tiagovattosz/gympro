package br.edu.fema.gympro.dto.plano;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PlanoRequestDTO(@NotBlank(message = "A descricao do plano não pode estar em branco!")
                              @Size(max = 255, message = "A descrição plano não pode ter mais de 255 caracteres!")
                              String descricao,

                              @Min(value = 0, message = "O número máximo de inscrições não pode ser negativo!")
                              Integer maximoInscricoes,

                              @DecimalMin(value = "0", message = "O valor não pode ser negativo!")
                              @Digits(integer = 9, fraction = 2, message = "O valor não pode ter mais que 2 casas decimais!")
                              BigDecimal valor,

                              @Min(value = 0, message = "A duração em meses não pode ser negativa!")
                              Integer duracaoEmMeses,

                              @Size(max = 1000, message = "Os detalhes do plano não podem ter mais de 255 caracteres!")
                              String detalhes
){}
