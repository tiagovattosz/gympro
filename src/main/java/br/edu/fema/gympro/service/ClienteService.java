package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.dto.cliente.ClienteCreateDTO;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import br.edu.fema.gympro.dto.cliente.ClienteUpdateDTO;
import br.edu.fema.gympro.exception.domain.CpfDuplicadoException;
import br.edu.fema.gympro.exception.domain.MenorDeIdadeException;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.util.mapper.ClienteMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PlanoService planoService;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, PlanoService planoService) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.planoService = planoService;
    }

    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toClienteResponseDTO)
                .toList();
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = findClienteOrThrow(id);
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> findByPlano(Long idPlano) {
        Plano plano = planoService.findPlanoOrThrow(idPlano);
        return clienteRepository.findByPlano(plano).stream()
                .map(clienteMapper::toClienteResponseDTO)
                .toList();
    }

    public ClienteResponseDTO save(ClienteCreateDTO data) {
        if(clienteRepository.existsByCpf(data.cpf())){
            throw new CpfDuplicadoException("CPF já cadastrado no sistema!");
        }

        LocalDate hoje = LocalDate.now();
        LocalDate dataNascimentoParsed = LocalDate.parse(data.dataNascimento());
        Period idade = Period.between(dataNascimentoParsed, hoje);

        if(idade.getYears() < 18) {
            throw new MenorDeIdadeException("Cliente menor de idade!");
        }

        Cliente cliente = new Cliente();

        cliente.setNome(data.nome());
        cliente.setCpf(data.cpf());
        cliente.setCelular(data.celular());
        cliente.setEmail(data.email());
        cliente.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        cliente.setNumeroIncricoesAtivas(0);
        cliente.setDataHoraCadastro(LocalDateTime.now());

        clienteRepository.save(cliente);
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO update(ClienteUpdateDTO data, Long id) {
        LocalDate hoje = LocalDate.now();
        LocalDate dataNascimentoParsed = LocalDate.parse(data.dataNascimento());
        Period idade = Period.between(dataNascimentoParsed, hoje);

        if(idade.getYears() < 18) {
            throw new MenorDeIdadeException("Cliente menor de idade!");
        }

        Cliente cliente = findClienteOrThrow(id);
        cliente.setNome(data.nome());
        cliente.setCelular(data.celular());
        cliente.setEmail(data.email());
        cliente.setDataNascimento(LocalDate.parse(data.dataNascimento()));
        clienteRepository.save(cliente);
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Cliente não encontrado!");
        }
        clienteRepository.deleteById(id);
    }

    public Cliente findClienteOrThrow(Long id) {
        return clienteRepository.findById(id).orElseThrow(() ->
                new ObjetoNaoEncontrado("Cliente não encontrado!"));
    }
}
