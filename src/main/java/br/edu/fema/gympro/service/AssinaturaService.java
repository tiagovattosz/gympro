package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.dto.assinatura.AssinaturaRequestDTO;
import br.edu.fema.gympro.dto.assinatura.AssinaturaResponseDTO;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.util.mapper.ClienteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssinaturaService {
    private final PlanoService planoService;
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public AssinaturaService(PlanoService planoService, ClienteService clienteService, ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.planoService = planoService;
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteResponseDTO> findAssinaturasAtivas() {
        List<Cliente> clientes = clienteRepository.findClientesComAssinaturaAtiva();
        return clientes
                .stream()
                .map(clienteMapper::toClienteResponseDTO)
                .toList();
    }

    public List<ClienteResponseDTO> findAssinaturasVencidas() {
        List<Cliente> clientes = clienteRepository.findClientesComAssinaturaVencida();
        return clientes
                .stream()
                .map(clienteMapper::toClienteResponseDTO)
                .toList();
    }

    @Transactional
    public AssinaturaResponseDTO save(AssinaturaRequestDTO data) {
        Plano plano = planoService.findPlanoOrThrow(data.planoId());
        Cliente cliente = clienteService.findClienteOrThrow(data.clienteId());

        LocalDate dataInicioAssinatura;
        if(data.dataInicioAssinatura() != null) {
            dataInicioAssinatura = LocalDate.parse(data.dataInicioAssinatura());
        } else {
            dataInicioAssinatura = LocalDate.now();
        }

        LocalDate dataTerminoAssinatura = dataInicioAssinatura.plusMonths(plano.getDuracaoEmMeses());

        cliente.setPlano(plano);
        cliente.setDataInicioAssinatura(dataInicioAssinatura);
        cliente.setDataTerminoAssinatura(dataTerminoAssinatura);
        clienteRepository.save(cliente);

        return new AssinaturaResponseDTO(
                cliente.getNome(),
                plano.getDescricao(),
                dataInicioAssinatura.toString(),
                dataTerminoAssinatura.toString()
        );
    }

    @Transactional
    public void removerAssinatura(Long clienteId) {
        Cliente cliente = clienteService.findClienteOrThrow(clienteId);
        cliente.setPlano(null);
        cliente.setDataInicioAssinatura(null);
        cliente.setDataTerminoAssinatura(null);
        clienteRepository.save(cliente);
    }


}
