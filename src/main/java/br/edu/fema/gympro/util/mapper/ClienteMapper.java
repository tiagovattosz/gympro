package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.dto.cliente.AreaDoClienteDTO;
import br.edu.fema.gympro.dto.cliente.ClienteDetailsDTO;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaResponseDTO;
import br.edu.fema.gympro.repository.InscricaoAulaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {
    private final InscricaoAulaRepository inscricaoAulaRepository;
    private final InscricaoAulaMapper inscricaoAulaMapper;

    public ClienteMapper(InscricaoAulaRepository inscricaoAulaRepository, InscricaoAulaMapper inscricaoAulaMapper) {
        this.inscricaoAulaRepository = inscricaoAulaRepository;
        this.inscricaoAulaMapper = inscricaoAulaMapper;
    }

    public ClienteResponseDTO toClienteResponseDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getCelular(),
                cliente.getEmail(),
                cliente.getDataNascimento().toString(),
                cliente.getNumeroIncricoesAtivas(),
                cliente.getPlano() != null ? cliente.getPlano().getMaximoInscricoes() : 0,
                cliente.getPlano() != null ? cliente.getPlano().getDescricao() : null,
                cliente.getDataInicioAssinatura() != null ? cliente.getDataInicioAssinatura().toString() : null,
                cliente.getDataTerminoAssinatura() != null ? cliente.getDataTerminoAssinatura().toString() : null,
                cliente.getMatricula()
        );
    }

    public ClienteDetailsDTO toClienteDetailsDTO(Cliente cliente){
        List<InscricaoAulaResponseDTO> inscricoes = inscricaoAulaRepository.findInscricaoAulaByCliente(cliente)
                .stream()
                .map(inscricaoAulaMapper::toInscricaoAulaResponseDTO)
                .toList();

        return new ClienteDetailsDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getCelular(),
                cliente.getEmail(),
                cliente.getDataNascimento().toString(),
                cliente.getNumeroIncricoesAtivas(),
                cliente.getPlano() != null ? cliente.getPlano().getMaximoInscricoes() : 0,
                cliente.getPlano() != null ? cliente.getPlano().getDescricao() : null,
                cliente.getDataInicioAssinatura() != null ? cliente.getDataInicioAssinatura().toString() : null,
                cliente.getDataTerminoAssinatura() != null ? cliente.getDataTerminoAssinatura().toString() : null,
                cliente.getMatricula(),
                inscricoes
        );
    }

    public AreaDoClienteDTO toAreaDoClienteDTO(Cliente cliente){
        List<InscricaoAulaResponseDTO> inscricoes = inscricaoAulaRepository.findInscricaoAulaByCliente(cliente)
                .stream()
                .map(inscricaoAulaMapper::toInscricaoAulaResponseDTO)
                .toList();

        return new AreaDoClienteDTO(
                cliente.getId(),
                cliente.getNome().split(" ")[0],
                cliente.getNumeroIncricoesAtivas(),
                cliente.getPlano() != null ? cliente.getPlano().getMaximoInscricoes() : 0,
                cliente.getPlano() != null ? cliente.getPlano().getDescricao() : null,
                cliente.getDataInicioAssinatura() != null ? cliente.getDataInicioAssinatura().toString() : null,
                cliente.getDataTerminoAssinatura() != null ? cliente.getDataTerminoAssinatura().toString() : null,
                cliente.getMatricula(),
                inscricoes
        );
    }
}
