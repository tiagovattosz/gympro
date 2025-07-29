package br.edu.fema.gympro.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteCreateDTO(
                               @NotBlank(message = "O nome não pode estar em branco.")
                               @Size(max = 255, message = "O nome não pode ter mais de 255 caracteres.")
                               String nome,

                               @NotBlank(message = "O CPF não pode estar em branco.")
                               @CPF
                               String cpf,

                               @NotBlank(message = "O celular não pode estar em branco.")
                               @Size(max = 11, message = "O celular não pode ter mais de 11 caracteres.")
                               String celular,

                               @NotBlank(message = "A data de nascimento não pode estar em branco.")
                               String dataNascimento,

                               @Email(message = "O email deve ser válido.")
                               @Size(max = 255, message = "O e-mail não pode ter mais de 255 caracteres.")
                               String email
) {
}
