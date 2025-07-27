package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.EntradaSaida;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.domain.enums.TipoMovimento;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaCreateDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
import br.edu.fema.gympro.exception.domain.AssinaturaVencidaException;
import br.edu.fema.gympro.exception.domain.ClienteSemPlanoException;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.repository.EntradaSaidaRepository;
import br.edu.fema.gympro.repository.FuncionarioRepository;
import br.edu.fema.gympro.util.mapper.EntradaSaidaMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntradaSaidaService {
    private final EntradaSaidaRepository entradaSaidaRepository;
    private final ClienteRepository clienteRepository;
    private final EntradaSaidaMapper entradaSaidaMapper;
    private final FuncionarioRepository funcionarioRepository;

    public EntradaSaidaService(EntradaSaidaRepository entradaSaidaRepository, ClienteRepository clienteRepository, EntradaSaidaMapper entradaSaidaMapper, FuncionarioRepository funcionarioRepository) {
        this.entradaSaidaRepository = entradaSaidaRepository;
        this.clienteRepository = clienteRepository;
        this.entradaSaidaMapper = entradaSaidaMapper;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<EntradaSaidaResponseDTO> findAll() {
        return entradaSaidaRepository.findAll().stream()
                .map(entradaSaidaMapper::toEntradaSaidaResponseDTO)
                .toList();
    }

    public List<EntradaSaidaResponseDTO> findByData(LocalDate dataInicio, LocalDate dataFinal) {
        LocalDateTime inicio;
        LocalDateTime fim;
        if(dataInicio != null) {
            inicio = dataInicio.atStartOfDay();
        } else {
            inicio = LocalDateTime.of(1970, 1, 1, 0, 0);
        }

        if(dataFinal != null) {
            fim = dataFinal.atTime(LocalTime.MAX);
        } else {
            fim = LocalDateTime.now();
        }

        return entradaSaidaRepository.findByDataHoraBetween(inicio, fim).stream()
                .map(entradaSaidaMapper::toEntradaSaidaResponseDTO)
                .toList();
    }

    public EntradaSaidaResponseDTO registrarEntrada(EntradaSaidaCreateDTO data) {
        String matricula = data.matricula();
        Optional<Cliente> clienteOptional = clienteRepository.findByMatricula(matricula);
        if(clienteOptional.isPresent()){
            Cliente cliente = clienteOptional.get();
            if(cliente.getPlano() == null) {
                throw new ClienteSemPlanoException("O cliente não tem plano");
            }
            if (cliente.getDataTerminoAssinatura().isBefore(LocalDate.now())) {
                throw new AssinaturaVencidaException("O cliente está com a assinatura vencida");
            }

            EntradaSaida entradaSaida = new EntradaSaida();
            entradaSaida.setTipoMovimento(TipoMovimento.ENTRADA);
            entradaSaida.setDataHora(LocalDateTime.now());
            entradaSaida.setTipoPessoa("C");
            entradaSaida.setPessoaId(cliente.getId());
            entradaSaidaRepository.save(entradaSaida);
            return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
        } else {
            Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByMatricula(matricula);
            if(funcionarioOptional.isPresent()) {
                Funcionario funcionario = funcionarioOptional.get();
                EntradaSaida entradaSaida = new EntradaSaida();
                entradaSaida.setTipoMovimento(TipoMovimento.ENTRADA);
                entradaSaida.setDataHora(LocalDateTime.now());
                entradaSaida.setTipoPessoa("F");
                entradaSaida.setPessoaId(funcionario.getId());
                entradaSaidaRepository.save(entradaSaida);
                return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
            } else {
                throw new ObjetoNaoEncontrado("Id da pessoa não encontrado");
            }
        }
    }

    public EntradaSaidaResponseDTO registrarSaida(EntradaSaidaCreateDTO data) {
        String matricula = data.matricula();
        Optional<Cliente> clienteOptional = clienteRepository.findByMatricula(matricula);
        if(clienteOptional.isPresent()){
            Cliente cliente = clienteOptional.get();
            if(cliente.getPlano() == null) {
                throw new ClienteSemPlanoException("O cliente não tem plano");
            }
            if (cliente.getDataTerminoAssinatura().isBefore(LocalDate.now())) {
                throw new AssinaturaVencidaException("O cliente está com a assinatura vencida");
            }

            EntradaSaida entradaSaida = new EntradaSaida();
            entradaSaida.setTipoMovimento(TipoMovimento.SAIDA);
            entradaSaida.setDataHora(LocalDateTime.now());
            entradaSaida.setTipoPessoa("C");
            entradaSaida.setPessoaId(cliente.getId());
            entradaSaidaRepository.save(entradaSaida);
            return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
        } else {
            Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByMatricula(matricula);
            if(funcionarioOptional.isPresent()) {
                Funcionario funcionario = funcionarioOptional.get();
                EntradaSaida entradaSaida = new EntradaSaida();
                entradaSaida.setTipoMovimento(TipoMovimento.SAIDA);
                entradaSaida.setDataHora(LocalDateTime.now());
                entradaSaida.setTipoPessoa("F");
                entradaSaida.setPessoaId(funcionario.getId());
                entradaSaidaRepository.save(entradaSaida);
                return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
            } else {
                throw new ObjetoNaoEncontrado("Id da pessoa não encontrado");
            }
        }
    }

}
