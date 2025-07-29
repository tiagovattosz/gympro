package br.edu.fema.gympro.dto.equipamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipamentoCreateDTO(
        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(max = 255, message = "O nome não pode ter mais de 255 caracteres.")
        String nome,
        String descricao
) {}
