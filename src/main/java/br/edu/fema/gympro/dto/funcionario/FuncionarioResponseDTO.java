package br.edu.fema.gympro.dto.funcionario;

public record FuncionarioResponseDTO(Long id,
                                     String username,
                                     Boolean admin,
                                     String nome,
                                     String cpf,
                                     String celular,
                                     String email,
                                     String dataNascimento,
                                     String cargo,
                                     String matricula) {
}
