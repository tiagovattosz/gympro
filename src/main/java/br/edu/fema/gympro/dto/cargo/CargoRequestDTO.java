package br.edu.fema.gympro.dto.cargo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CargoRequestDTO(@NotBlank(message = "A descrição não pode estar em branco!")
                              @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres!")
                              String descricao) {
}
