package br.edu.fema.gympro.dto.funcionario;

public record FuncionarioResponseDTO(Long id,
                                     String username,
                                     String nome,
                                     String cpf,
                                     String celular,
                                     String email,
                                     String dataNascimento) {
}
