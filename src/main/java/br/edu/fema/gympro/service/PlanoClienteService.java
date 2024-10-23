package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.domain.PlanoCliente;
import br.edu.fema.gympro.dto.planocliente.PlanoClienteCreateDTO;
import br.edu.fema.gympro.dto.planocliente.PlanoClienteResponseDTO;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.PlanoClienteRepository;
import br.edu.fema.gympro.util.mapper.PlanoClienteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PlanoClienteService {

    private final PlanoClienteRepository planoClienteRepository;
    private final PlanoService planoService;
    private final ClienteService clienteService;
    private final PlanoClienteMapper planoClienteMapper;

    public PlanoClienteService(PlanoClienteRepository planoClienteRepository, PlanoService planoService, ClienteService clienteService, PlanoClienteMapper planoClienteMapper) {
        this.planoClienteRepository = planoClienteRepository;
        this.planoService = planoService;
        this.clienteService = clienteService;
        this.planoClienteMapper = planoClienteMapper;
    }

    @Transactional
    public PlanoClienteResponseDTO save(PlanoClienteCreateDTO data) {
        Plano plano = planoService.findPlanoOrThrow(data.planoId());
        Cliente cliente = clienteService.findClienteOrThrow(data.clienteId());

        PlanoCliente planoCliente = new PlanoCliente();
        planoCliente.setPlano(plano);
        planoCliente.setCliente(cliente);
        planoCliente.setDataInicio(LocalDate.now());
        planoCliente.setDataTermino(planoCliente.getDataInicio().plusMonths(plano.getDuracaoEmMeses()));

        planoClienteRepository.save(planoCliente);

        return planoClienteMapper.toPlanoClienteResponseDTO(planoCliente);
    }

    @Transactional
    public PlanoClienteResponseDTO update(PlanoClienteCreateDTO data, Long idCliente) {

        Cliente cliente = clienteService.findClienteOrThrow(data.planoId());
        PlanoCliente planoCliente = findPlanoClienteByClienteOrThrow(cliente);

        Plano plano = planoService.findPlanoOrThrow(data.planoId());
        planoCliente.setPlano(plano);
        planoCliente.setDataInicio(LocalDate.now());
        planoCliente.setDataTermino(planoCliente.getDataInicio().plusMonths(plano.getDuracaoEmMeses()));

        planoClienteRepository.save(planoCliente);

        return planoClienteMapper.toPlanoClienteResponseDTO(planoCliente);
    }

    public PlanoCliente findPlanoClienteByClienteOrThrow(Cliente cliente) {
        return planoClienteRepository.findByCliente(cliente).orElseThrow(() ->
                new ObjetoNaoEncontrado("Cliente sem plano!"));
    }
}
