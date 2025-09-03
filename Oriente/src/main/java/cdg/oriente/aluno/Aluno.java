package cdg.oriente.aluno;

import cdg.oriente.projeto.Projeto;
import cdg.oriente.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.text.Normalizer;
import java.util.Locale;

@Entity(name = "Aluno")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "matricula")
public class Aluno extends Usuario {

    @Id
    private Long matricula;
    private String curso;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "projeto_id", nullable = true)
    private Projeto projeto;


    public Aluno(DadosCadastroAluno dados) {
        this.matricula = dados.matricula();
        this.nome = dados.nome();
        this.curso = dados.curso();
        configurarCredenciais();
    }

    public void update(DadosAtualizacaoAluno dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.curso()!=null){
            this.curso = dados.curso();
        }

    }

    @Override
    public void configurarCredenciais() {
        String nome = Normalizer.normalize(this.nome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase(Locale.ROOT).replace(" ", "");
        // this.email = nome + "@aluno.inatel.br";
        this.senha = this.matricula.toString();
    }

    @Override
    public String toString() {
        return "[Aluno] Nome: " + this.getNome() + " | Matricula: " + this.getMatricula() + " | Email: " + this.email;
    }
}
