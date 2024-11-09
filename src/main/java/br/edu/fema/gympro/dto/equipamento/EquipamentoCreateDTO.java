package br.edu.fema.gympro.dto.equipamento;

import jakarta.validation.constraints.NotBlank;

public record EquipamentoCreateDTO(
        @NotBlank(message = "O nome do equipamento não pode estar em branco!")
        String nome,
        String descricao
) {}
