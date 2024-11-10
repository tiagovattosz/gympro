package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.EntradaSaida;
import br.edu.fema.gympro.domain.enums.TipoMovimento;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaCreateDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
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

    public EntradaSaidaService(EntradaSaidaRepository entradaSaidaRepository, ClienteRepository clienteRepository, EntradaSaidaMapper entradaSaidaMapper) {
        this.entradaSaidaRepository = entradaSaidaRepository;
        this.clienteRepository = clienteRepository;
        this.entradaSaidaMapper = entradaSaidaMapper;
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
        EntradaSaida entradaSaida = new EntradaSaida();
        entradaSaida.setTipoMovimento(TipoMovimento.ENTRADA);

        entradaSaida.setDataHora(LocalDateTime.now());
        entradaSaida.setCliente(clienteRepository.findById(data.idCliente()).orElseThrow(() ->
                new ObjetoNaoEncontrado("Cliente não encontrado!")));

        entradaSaidaRepository.save(entradaSaida);
        return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
    }

    public EntradaSaidaResponseDTO registrarSaida(EntradaSaidaCreateDTO data) {
        EntradaSaida entradaSaida = new EntradaSaida();
        entradaSaida.setTipoMovimento(TipoMovimento.SAIDA);

        entradaSaida.setDataHora(LocalDateTime.now());
        entradaSaida.setCliente(clienteRepository.findById(data.idCliente()).orElseThrow(() ->
                new ObjetoNaoEncontrado("Cliente não encontrado!")));

        entradaSaidaRepository.save(entradaSaida);
        return entradaSaidaMapper.toEntradaSaidaResponseDTO(entradaSaida);
    }

}
