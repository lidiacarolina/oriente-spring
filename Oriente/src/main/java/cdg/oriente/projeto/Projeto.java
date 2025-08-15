package cdg.oriente.projeto;

import cdg.oriente.aluno.Aluno;
import cdg.oriente.professor.Professor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name = "Projeto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private Float nota;
    private boolean aprovado;

    @Setter
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Aluno> alunos;

    @ManyToMany
    @JoinTable(
            name = "projeto_professor",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    @MapKeyColumn(name = "cargo")
    private Map<String, Professor> professores = new HashMap<>();

    public Projeto(DadosCadastroProjeto dados) {
        this.id = dados.id();
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
        this.aprovado = dados.aprovado();
    }

    public void update(DadosAtualizacaoProjeto dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if(dados.descricao()!=null){
            this.descricao = dados.descricao();
        }
        if(dados.nota()!=null){
            this.nota = dados.nota();
        }

    }

}
