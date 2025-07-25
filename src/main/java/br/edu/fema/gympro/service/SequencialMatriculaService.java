package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.SequencialMatricula;
import br.edu.fema.gympro.repository.SequencialMatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SequencialMatriculaService {
    private final SequencialMatriculaRepository sequencialMatriculaRepository;

    public SequencialMatriculaService(SequencialMatriculaRepository sequencialMatriculaRepository) {
        this.sequencialMatriculaRepository = sequencialMatriculaRepository;
    }

    @Transactional
    public String gerarNovaMatricula() {
        SequencialMatricula seq = sequencialMatriculaRepository.findById(1L)
                .orElseGet(() -> {
                    SequencialMatricula novo = new SequencialMatricula();
                    novo.setId(1L);
                    novo.setValorAtual(0L);
                    return sequencialMatriculaRepository.save(novo);
                });

        Long novoValor = seq.proximoValor();
        sequencialMatriculaRepository.save(seq);

        return String.format("%06d", novoValor); // 000001, 000002, ...
    }
}
