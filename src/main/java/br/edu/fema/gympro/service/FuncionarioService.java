package br.edu.fema.gympro.service;

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
import br.edu.fema.gympro.util.mapper.FuncionarioMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final AuthenticationService authenticationService;
    private final CargoService cargoService;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper, AuthenticationService authenticationService, CargoService cargoService) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
        this.authenticationService = authenticationService;
        this.cargoService = cargoService;
    }

    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository.findAll().stream()
                .map(funcionarioMapper::toFuncionarioResponseDTO)
                .toList();
    }

    public FuncionarioResponseDTO findById(Long id) {
        Funcionario funcionario = findFuncionarioOrThrow(id);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    public FuncionarioResponseDTO save(FuncionarioCreateDTO data) {
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(data.nome());
        funcionario.setCelular(data.celular());
        funcionario.setEmail(data.email());
        funcionario.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        funcionario.setCpf(data.cpf());
        if(data.idCargo() != null) {
            funcionario.setCargo(cargoService.findCargoOrThrow(data.idCargo()));
        }

        User user = authenticationService.register(new RegisterDTO(data.username(), data.password(), UserRole.USER.getValue()));
        funcionario.setUser(user);

        funcionarioRepository.save(funcionario);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    public FuncionarioResponseDTO update(FuncionarioUpdateDTO data, Long id) {
        Funcionario funcionario = findFuncionarioOrThrow(id);
        funcionario.setNome(data.nome());
        funcionario.setCelular(data.celular());
        funcionario.setEmail(data.email());
        funcionario.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        if(data.idCargo() != null) {
            funcionario.setCargo(cargoService.findCargoOrThrow(data.idCargo()));
        }

        funcionarioRepository.save(funcionario);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    public void deleteById(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Funcionário não encontrado!");
        }
        funcionarioRepository.deleteById(id);
    }

    public Funcionario findFuncionarioOrThrow(Long id) {
        return funcionarioRepository.findById(id).orElseThrow(() ->
                new ObjetoNaoEncontrado("Funcionário não encontrado!"));
    }

    public List<FuncionarioResponseDTO> findProfessores() {
        return funcionarioRepository.findProfessores().stream()
                .map(funcionarioMapper::toFuncionarioResponseDTO)
                .toList();
    }
}
