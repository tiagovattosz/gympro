package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.EntradaSaida;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.domain.enums.TipoMovimento;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaCreateDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradasPorDiaDTO;
import br.edu.fema.gympro.dto.entradasaida.PessoaDTO;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return entradaSaidaRepository.findAllByOrderByDataHoraDesc().stream()
                .map(entradaSaidaMapper::toEntradaSaidaResponseDTO)
                .toList();
    }

    public List<EntradaSaidaResponseDTO> findByData(LocalDate dataInicio, LocalDate dataFinal, String matricula) {
        LocalDateTime inicio = (dataInicio != null) ? dataInicio.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime fim = (dataFinal != null) ? dataFinal.atTime(LocalTime.MAX) : LocalDateTime.now();

        List<EntradaSaida> registros;

        if (matricula != null) {
            registros = entradaSaidaRepository.findByDataHoraBetweenAndMatricula(inicio, fim, matricula);
        } else {
            registros = entradaSaidaRepository.findByDataHoraBetween(inicio, fim);
        }

        return registros.stream()
                .map(entradaSaidaMapper::toEntradaSaidaResponseDTO)
                .toList();
    }

    public PessoaDTO findByMatricula(String matricula) {
        // Tenta encontrar Cliente
        Optional<Cliente> clienteOptional = clienteRepository.findByMatricula(matricula);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            return new PessoaDTO(cliente.getId(), cliente.getCpf(), cliente.getNome(), "CLIENTE");
        }

        // Tenta encontrar Funcionário
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByMatricula(matricula);
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();

            return new PessoaDTO(funcionario.getId(), funcionario.getCpf(), funcionario.getNome(), "FUNCIONARIO");
        }

        throw new ObjetoNaoEncontrado("Matrícula não encontrada");
    }


    public EntradaSaidaResponseDTO registrarEntrada(EntradaSaidaCreateDTO data) {
        return registrarMovimento(data, TipoMovimento.ENTRADA);
    }

    public EntradaSaidaResponseDTO registrarSaida(EntradaSaidaCreateDTO data) {
        return registrarMovimento(data, TipoMovimento.SAIDA);
    }

    public List<EntradasPorDiaDTO> getEntradasPorPeriodo(LocalDate inicio, LocalDate fim) {
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(LocalTime.MAX);

        // Busca todas as movimentações no período
        List<EntradaSaida> movimentacoes = entradaSaidaRepository.findByDataHoraBetween(inicioDateTime, fimDateTime);

        // Filtra apenas as ENTRADAS e agrupa por data (ignorando hora)
        Map<LocalDate, Long> contagemPorDia = movimentacoes.stream()
                .filter(e -> e.getTipoMovimento().equals(TipoMovimento.ENTRADA))
                .collect(Collectors.groupingBy(
                        e -> e.getDataHora().toLocalDate(),
                        Collectors.counting()
                ));

        // Converte o Map para uma lista ordenada de DTOs
        return contagemPorDia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // ordena por data
                .map(entry -> new EntradasPorDiaDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    private EntradaSaidaResponseDTO registrarMovimento(EntradaSaidaCreateDTO data, TipoMovimento tipoMovimento) {
        String matricula = data.matricula();

        // Tenta encontrar Cliente
        Optional<Cliente> clienteOptional = clienteRepository.findByMatricula(matricula);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            validarAssinatura(cliente);

            return salvarMovimento(tipoMovimento, "C", cliente.getId(), matricula, cliente.getCpf(), cliente.getNome());
        }

        // Tenta encontrar Funcionário
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByMatricula(matricula);
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();

            return salvarMovimento(tipoMovimento, "F", funcionario.getId(), matricula, funcionario.getCpf(), funcionario.getNome());
        }

        throw new ObjetoNaoEncontrado("Matrícula não encontrada");
    }


    private void validarAssinatura(Cliente cliente) {
        if (cliente.getPlano() == null) {
            throw new ClienteSemPlanoException("O cliente não tem plano");
        }
        if (cliente.getDataTerminoAssinatura().isBefore(LocalDate.now())) {
            throw new AssinaturaVencidaException("O cliente está com a assinatura vencida");
        }
    }

    private EntradaSaidaResponseDTO salvarMovimento(TipoMovimento tipo, String tipoPessoa, Long pessoaId, String matricula, String cpf, String nome) {
        EntradaSaida entradaSaida = new EntradaSaida();
        entradaSaida.setTipoMovimento(tipo);
        entradaSaida.setDataHora(LocalDateTime.now());
        entradaSaida.setTipoPessoa(tipoPessoa);
        entradaSaida.setPessoaId(pessoaId);
        entradaSaida.setMatricula(matricula);
        entradaSaida.setCpf(cpf);
        entradaSaida.setNome(nome);

        entradaSaidaRepository.save(entradaSaida);
        return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
    }


}
