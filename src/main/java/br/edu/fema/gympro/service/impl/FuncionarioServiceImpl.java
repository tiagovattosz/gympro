package br.edu.fema.gympro.service.impl;

import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.dto.funcionario.FuncionarioCreateDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioResponseDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioUpdateDTO;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.FuncionarioRepository;
import br.edu.fema.gympro.security.domain.user.User;
import br.edu.fema.gympro.security.domain.user.UserRole;
import br.edu.fema.gympro.security.dto.RegisterDTO;
import br.edu.fema.gympro.security.service.AuthenticationService;
import br.edu.fema.gympro.service.FuncionarioService;
import br.edu.fema.gympro.util.mapper.FuncionarioMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final AuthenticationService authenticationService;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper, AuthenticationService authenticationService) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository.findAll().stream()
                .map(funcionarioMapper::toFuncionarioResponseDTO)
                .toList();
    }

    @Override
    public FuncionarioResponseDTO findById(Long id) {
        Funcionario funcionario = findFuncionarioOrThrow(id);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    @Override
    public FuncionarioResponseDTO save(FuncionarioCreateDTO data) {
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(data.nome());
        funcionario.setCelular(data.celular());
        funcionario.setEmail(data.email());
        funcionario.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        funcionario.setCpf(data.cpf());

        User user = authenticationService.register(new RegisterDTO(data.username(), data.password(), UserRole.ADMIN.getValue()));
        funcionario.setUser(user);

        funcionarioRepository.save(funcionario);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    @Override
    public FuncionarioResponseDTO update(FuncionarioUpdateDTO data, Long id) {
        Funcionario funcionario = findFuncionarioOrThrow(id);
        funcionario.setNome(data.nome());
        funcionario.setCelular(data.celular());
        funcionario.setEmail(data.email());
        funcionario.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        funcionarioRepository.save(funcionario);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    @Override
    public void deleteById(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Funcionário não encontrado!");
        }
        funcionarioRepository.deleteById(id);
    }

    private Funcionario findFuncionarioOrThrow(Long id) {
        return funcionarioRepository.findById(id).orElseThrow(() ->
                new ObjetoNaoEncontrado("Funcionário não encontrado!"));
    }
}
