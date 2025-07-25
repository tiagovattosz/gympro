package br.edu.fema.gympro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "public", name = "sequencial_matriculas")
@Getter
@Setter
public class SequencialMatricula {
    @Id
    private Long id = 1L;

    private Long valorAtual = 0L;

    public Long proximoValor() {
        this.valorAtual++;
        return this.valorAtual;
    }

}
