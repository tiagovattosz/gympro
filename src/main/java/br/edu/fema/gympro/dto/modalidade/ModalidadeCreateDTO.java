package br.edu.fema.gympro.dto.modalidade;

import jakarta.validation.constraints.NotNull;

public record ModalidadeCreateDTO(
        @NotNull(message = "A descrição não pode estar em branco!")
        String descricao) {
}
