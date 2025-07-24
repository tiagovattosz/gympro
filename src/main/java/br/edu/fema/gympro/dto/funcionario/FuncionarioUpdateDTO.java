package br.edu.fema.gympro.dto.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FuncionarioUpdateDTO(@NotBlank(message = "O nome não pode estar em branco!")
                                   @Size(max = 255, message = "O nome não pode ter mais de 255 caracteres!")
                                   String nome,

                                   @NotBlank(message = "O celular não pode estar em branco!")
                                   @Size(max = 255, message = "O celular não pode ter mais de 255 caracteres!")
                                   String celular,

                                   @Email(message = "O e-mail deve ser válido!")
                                   @Size(max = 255, message = "O e-mail não pode ter mais de 255 caracteres!")
                                   String email,

                                   @NotBlank(message = "A data de nascimento não pode ser nula!")
                                   String dataNascimento,

                                   Long idCargo,

                                   Boolean admin
) {
}
