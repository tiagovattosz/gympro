package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.*;
import br.edu.fema.gympro.dto.funcionario.FuncionarioCreateDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioResponseDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioUpdateDTO;
import br.edu.fema.gympro.exception.domain.MenorDeIdadeException;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.*;
import br.edu.fema.gympro.security.domain.user.User;
import br.edu.fema.gympro.security.domain.user.UserRole;
import br.edu.fema.gympro.security.dto.RegisterDTO;
import br.edu.fema.gympro.security.repository.UserRepository;
import br.edu.fema.gympro.security.service.AuthenticationService;
import br.edu.fema.gympro.util.mapper.FuncionarioMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final AuthenticationService authenticationService;
    private final CargoService cargoService;
    private final SequencialMatriculaService sequencialMatriculaService;
    private final UserRepository userRepository;
    private final AulaRepository aulaRepository;
    private final ManutencaoRepository manutencaoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper, AuthenticationService authenticationService, CargoService cargoService, SequencialMatriculaService sequencialMatriculaService, UserRepository userRepository, AulaRepository aulaRepository, ManutencaoRepository manutencaoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
        this.authenticationService = authenticationService;
        this.cargoService = cargoService;
        this.sequencialMatriculaService = sequencialMatriculaService;
        this.userRepository = userRepository;
        this.aulaRepository = aulaRepository;

        this.manutencaoRepository = manutencaoRepository;
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
        if (funcionarioRepository.existsByCpf(data.cpf())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        LocalDate hoje = LocalDate.now();
        LocalDate dataNascimentoParsed = LocalDate.parse(data.dataNascimento());
        Period idade = Period.between(dataNascimentoParsed, hoje);
        if (idade.getYears() < 18) {
            throw new MenorDeIdadeException("Funcionario menor de idade!");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(data.nome());
        funcionario.setCelular(data.celular());
        funcionario.setEmail(data.email());
        funcionario.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        funcionario.setCpf(data.cpf());

        UserRole userRole = UserRole.USER;
        if (data.admin() != null) {
            if (data.admin().equals(true)) {
                userRole = UserRole.ADMIN;
            }
        }

        if (data.idCargo() != null) {
            funcionario.setCargo(cargoService.findCargoOrThrow(data.idCargo()));
        }

        String matricula = sequencialMatriculaService.gerarNovaMatricula();
        funcionario.setMatricula(matricula);

        User user = authenticationService.register(new RegisterDTO(data.username(), data.password(), userRole.getValue()));
        funcionario.setUser(user);

        funcionarioRepository.save(funcionario);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    public FuncionarioResponseDTO update(FuncionarioUpdateDTO data, Long id) {
        LocalDate hoje = LocalDate.now();
        LocalDate dataNascimentoParsed = LocalDate.parse(data.dataNascimento());
        Period idade = Period.between(dataNascimentoParsed, hoje);
        if (idade.getYears() < 18) {
            throw new MenorDeIdadeException("Funcionario menor de idade!");
        }

        Funcionario funcionario = findFuncionarioOrThrow(id);
        funcionario.setNome(data.nome());
        funcionario.setCelular(data.celular());
        funcionario.setEmail(data.email());
        funcionario.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        if (data.idCargo() != null) {
            funcionario.setCargo(cargoService.findCargoOrThrow(data.idCargo()));
        }

        funcionarioRepository.save(funcionario);
        return funcionarioMapper.toFuncionarioResponseDTO(funcionario);
    }

    public void deleteById(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Funcionário não encontrado!");
        }
        Funcionario funcionario = findFuncionarioOrThrow(id);

        List<Aula> aulasDoProfessor = aulaRepository.findAllByProfessor(funcionario);
        for (Aula aula : aulasDoProfessor) {
            aula.setProfessor(null);
            aulaRepository.save(aula);
        }

        List<Manutencao> manutencoesDoFuncionario = manutencaoRepository.findByFuncionario(funcionario);
        for(Manutencao manutencao: manutencoesDoFuncionario) {
            manutencao.setFuncionario(null);
            manutencaoRepository.save(manutencao);
        }

        funcionarioRepository.deleteById(id);
        userRepository.delete(funcionario.getUser());
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

    public List<FuncionarioResponseDTO> findByCargo(Long idCargo) {
        Cargo cargo = cargoService.findCargoOrThrow(idCargo);
        return funcionarioRepository.findByCargo(cargo).stream()
                .map(funcionarioMapper::toFuncionarioResponseDTO)
                .toList();
    }
}
