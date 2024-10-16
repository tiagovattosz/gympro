package br.edu.fema.gympro.domain;

import br.edu.fema.gympro.security.domain.user.User;
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
    @JoinColumn(name = "usuario_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "celular", length = 11)
    private String celular;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "data_hora_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataHoraCadastro;

    @Column(name = "numero_incricoes_ativas", nullable = false)
    private Integer numeroIncricoesAtivas;

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
