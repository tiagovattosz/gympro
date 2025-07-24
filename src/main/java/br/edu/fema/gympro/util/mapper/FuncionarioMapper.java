package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.dto.funcionario.FuncionarioResponseDTO;
import br.edu.fema.gympro.security.domain.user.UserRole;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {
    public FuncionarioResponseDTO toFuncionarioResponseDTO(Funcionario funcionario){
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getUser().getUsername(),
                funcionario.getUser().getRole() == UserRole.ADMIN,
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getCelular(),
                funcionario.getEmail(),
                funcionario.getDataNascimento().toString(),
                funcionario.getCargo() != null ? funcionario.getCargo().getDescricao() : null
        );
    }
}
