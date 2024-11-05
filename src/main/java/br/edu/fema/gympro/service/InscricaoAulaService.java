package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.InscricaoAula;
import br.edu.fema.gympro.domain.Aula;
import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaCreateDTO;
import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaResponseDTO;
import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaUpdateDTO;
import br.edu.fema.gympro.exception.domain.ClienteSemPlanoException;
import br.edu.fema.gympro.exception.domain.InscricoesExcedidasException;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.repository.InscricaoAulaRepository;
import br.edu.fema.gympro.util.mapper.InscricaoAulaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscricaoAulaService {
    private final InscricaoAulaRepository inscricaoAulaRepository;
    private final ClienteRepository clienteRepository;
    private final InscricaoAulaMapper inscricaoAulaMapper;
    private final ClienteService clienteService;
    private final AulaService aulaService;

    public InscricaoAulaService(InscricaoAulaRepository inscricaoAulaRepository, ClienteRepository clienteRepository,
                                InscricaoAulaMapper inscricaoAulaMapper,
                                ClienteService clienteService,
                                AulaService aulaService) {
        this.inscricaoAulaRepository = inscricaoAulaRepository;
        this.clienteRepository = clienteRepository;
        this.inscricaoAulaMapper = inscricaoAulaMapper;
        this.clienteService = clienteService;
        this.aulaService = aulaService;
    }

    public List<InscricaoAulaResponseDTO> findAll() {
        return inscricaoAulaRepository.findAll().stream()
                .map(inscricaoAulaMapper::toInscricaoAulaResponseDTO)
                .toList();
    }

    public InscricaoAulaResponseDTO findById(Long id) {
        InscricaoAula inscricaoAula = findInscricaoAulaOrThrow(id);
        return inscricaoAulaMapper.toInscricaoAulaResponseDTO(inscricaoAula);
    }

    @Transactional
    public InscricaoAulaResponseDTO save(InscricaoAulaCreateDTO data) {
        Cliente cliente = clienteService.findClienteOrThrow(data.clienteId());
        Aula aula = aulaService.findAulaOrThrow(data.aulaId());

        if(cliente.getPlano() == null) {
            throw new ClienteSemPlanoException("O cliente não possui plano.");
        } else if (cliente.getNumeroIncricoesAtivas() + 1 > cliente.getPlano().getMaximoInscricoes()) {
            throw new InscricoesExcedidasException("Número de inscrições excedidas. Máximo de inscrições: " + cliente.getPlano().getMaximoInscricoes());
        }

        InscricaoAula inscricaoAula = new InscricaoAula();
        inscricaoAula.setCliente(cliente);
        inscricaoAula.setAula(aula);
        inscricaoAula.setDataInscricao(LocalDate.now());
        inscricaoAulaRepository.save(inscricaoAula);

        cliente.setNumeroIncricoesAtivas(cliente.getNumeroIncricoesAtivas() + 1);
        clienteRepository.save(cliente);

        return inscricaoAulaMapper.toInscricaoAulaResponseDTO(inscricaoAula);
    }

    @Transactional
    public InscricaoAulaResponseDTO update(InscricaoAulaUpdateDTO data, Long id) {
        InscricaoAula inscricaoAula = findInscricaoAulaOrThrow(id);

        Cliente cliente = clienteService.findClienteOrThrow(data.clienteId());
        Aula aula = aulaService.findAulaOrThrow(data.aulaId());

        inscricaoAula.setCliente(cliente);
        inscricaoAula.setAula(aula);
        inscricaoAula.setDataInscricao(LocalDate.now());

        inscricaoAulaRepository.save(inscricaoAula);
        return inscricaoAulaMapper.toInscricaoAulaResponseDTO(inscricaoAula);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!inscricaoAulaRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Inscrição não encontrada!");
        }
        InscricaoAula inscricao = findInscricaoAulaOrThrow(id);
        Cliente cliente = inscricao.getCliente();
        cliente.setNumeroIncricoesAtivas(cliente.getNumeroIncricoesAtivas() - 1);
        clienteRepository.save(cliente);
        inscricaoAulaRepository.deleteById(id);
    }

    public InscricaoAula findInscricaoAulaOrThrow(Long id) {
        return inscricaoAulaRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Inscrição não encontrada!"));
    }
}
