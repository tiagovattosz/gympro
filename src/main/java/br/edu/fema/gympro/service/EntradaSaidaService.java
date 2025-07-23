package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.EntradaSaida;
import br.edu.fema.gympro.domain.enums.TipoMovimento;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaCreateDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
import br.edu.fema.gympro.exception.domain.AssinaturaVencidaException;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.repository.EntradaSaidaRepository;
import br.edu.fema.gympro.util.mapper.EntradaSaidaMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EntradaSaidaService {
    private final EntradaSaidaRepository entradaSaidaRepository;
    private final ClienteRepository clienteRepository;
    private final EntradaSaidaMapper entradaSaidaMapper;
    private final ClienteService clienteService;

    public EntradaSaidaService(EntradaSaidaRepository entradaSaidaRepository, ClienteRepository clienteRepository, EntradaSaidaMapper entradaSaidaMapper, ClienteService clienteService) {
        this.entradaSaidaRepository = entradaSaidaRepository;
        this.clienteRepository = clienteRepository;
        this.entradaSaidaMapper = entradaSaidaMapper;
        this.clienteService = clienteService;
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

    public List<EntradaSaidaResponseDTO> findByCliente(Long idCliente) {
        Cliente cliente = clienteService.findClienteOrThrow(idCliente);
        return entradaSaidaRepository.findByCliente(cliente).stream()
                .map(entradaSaidaMapper::toEntradaSaidaResponseDTO)
                .toList();
    }

    public EntradaSaidaResponseDTO registrarEntrada(EntradaSaidaCreateDTO data) {
        Cliente cliente = clienteRepository.findByCpf(data.cpf()).orElseThrow(() ->
                new ObjetoNaoEncontrado("Cliente não encontrado!"));

        if (cliente.getDataTerminoAssinatura().isBefore(LocalDate.now())) {
            throw new AssinaturaVencidaException("O cliente está com a assinatura vencida");
        }

        EntradaSaida entradaSaida = new EntradaSaida();
        entradaSaida.setTipoMovimento(TipoMovimento.ENTRADA);
        entradaSaida.setDataHora(LocalDateTime.now());
        entradaSaida.setCliente(cliente);
        entradaSaidaRepository.save(entradaSaida);

        return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
    }

    public EntradaSaidaResponseDTO registrarSaida(EntradaSaidaCreateDTO data) {
        Cliente cliente = clienteRepository.findByCpf(data.cpf()).orElseThrow(() ->
                new ObjetoNaoEncontrado("Cliente não encontrado!"));

        if (cliente.getDataTerminoAssinatura().isBefore(LocalDate.now())) {
            throw new AssinaturaVencidaException("O cliente está com a assinatura vencida");
        }

        EntradaSaida entradaSaida = new EntradaSaida();
        entradaSaida.setTipoMovimento(TipoMovimento.SAIDA);
        entradaSaida.setDataHora(LocalDateTime.now());
        entradaSaida.setCliente(cliente);
        entradaSaidaRepository.save(entradaSaida);

        return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
    }

}
