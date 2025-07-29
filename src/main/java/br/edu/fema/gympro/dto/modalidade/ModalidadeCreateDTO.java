package br.edu.fema.gympro.dto.modalidade;

import jakarta.validation.constraints.NotBlank;

public record ModalidadeCreateDTO(
        @NotBlank(message = "A descrição não pode estar em branco!")
        String descricao) {
}
