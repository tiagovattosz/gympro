package br.edu.fema.gympro.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteCreateDTO(@NotBlank(message = "O nome de usuário não pode estar em branco!")
                               @Size(max = 255, message = "O nome de usuário não pode ter mais de 255 caracteres!")
                               String username,

                               @NotBlank(message = "A senha não pode estar em branco!")
                               @Size(max = 255, message = "A senha não pode ter mais de 255 caracteres!")
                               String password,

                               @NotBlank(message = "O nome não pode estar em branco!")
                               @Size(max = 255, message = "O nome não pode ter mais de 255 caracteres!")
                               String nome,

                               @CPF
                               String cpf,

                               @NotBlank(message = "O celular não pode estar em branco!")
                               @Size(max = 255, message = "O celular não pode ter mais de 255 caracteres!")
                               String celular,

                               @Email(message = "O e-mail deve ser válido!")
                               @Size(max = 255, message = "O e-mail não pode ter mais de 255 caracteres!")
                               String email,

                               @NotBlank(message = "A data de nascimento não pode ser nula!")
                               String dataNascimento) {
}
