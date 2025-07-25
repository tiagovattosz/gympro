package br.edu.fema.gympro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "celular", length = 11)
    private String celular;

    @Column(name = "email")
    private String email;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "data_hora_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataHoraCadastro = LocalDateTime.now();

    @Column(name = "data_inicio_assinatura")
    private LocalDate dataInicioAssinatura;

    @Column(name = "data_termino_assinatura")
    private LocalDate dataTerminoAssinatura;

    @Column(name = "numero_incricoes_ativas", nullable = false)
    private Integer numeroIncricoesAtivas = 0;

    @Column(name = "matricula", nullable = false)
    private String matricula;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
