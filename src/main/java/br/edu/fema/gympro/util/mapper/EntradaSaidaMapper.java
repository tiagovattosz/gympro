package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.EntradaSaida;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.repository.FuncionarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntradaSaidaMapper {
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    public EntradaSaidaMapper(ClienteRepository clienteRepository, FuncionarioRepository funcionarioRepository) {
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public EntradaSaidaResponseDTO toEntradaSaidaResponseDTO(EntradaSaida entradaSaida) {
        String nome;
        if ("C".equals(entradaSaida.getTipoPessoa())) {
            Optional<Cliente> clienteOptional = clienteRepository.findById(entradaSaida.getPessoaId());
            if (clienteOptional.isPresent()) {
                nome = clienteOptional.get().getNome();
            } else {
                nome = "EXCLUIDO";
            }
        } else if ("F".equals(entradaSaida.getTipoPessoa())) {
            Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(entradaSaida.getPessoaId());
            if(funcionarioOptional.isPresent()) {
                nome = funcionarioOptional.get().getNome();
            } else {
                nome = "EXCLUIDO";
            }
        } else {
            nome = "TIPO INVÁLIDO";
        }
        return new EntradaSaidaResponseDTO(
                entradaSaida.getId(),
                entradaSaida.getPessoaId(),
                nome,
                entradaSaida.getTipoPessoa(),
                entradaSaida.getDataHora().toLocalDate().toString(),
                entradaSaida.getDataHora().toLocalTime().toString(),
                entradaSaida.getTipoMovimento().toString()
        );
    }
}
