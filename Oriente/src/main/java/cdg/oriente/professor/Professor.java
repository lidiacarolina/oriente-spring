package cdg.oriente.professor;

import cdg.oriente.projeto.Projeto;
import cdg.oriente.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyColumn;
import lombok.*;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;

@Entity(name = "Professor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "registro")
public class Professor extends Usuario {
    @Id
    private Long registro;
    private String permissao;

    @Setter
    @Getter
    @ManyToMany(mappedBy = "professores")
    @MapKeyColumn(name = "cargo")
    @JsonIgnore
    private Map<String, Projeto> projetos;

    public Professor(DadosCadastroProfessor dados) {
        this.registro = dados.registro();
        this.nome = dados.nome();
        this.permissao = dados.permissao();
        configurarCredenciais();
    }

    public void update(DadosAtualizacaoProfessor dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.permissao() != null) {
            this.permissao = dados.permissao();
        }
    }

    @Override
    public void configurarCredenciais() {
        String nome = Normalizer.normalize(this.nome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase(Locale.ROOT).replace(" ", "");
        this.email = nome + "@prof.inatel.br";
        this.senha = this.registro.toString();
    }

    @Override
    public String toString() {
        return "[Professor] Nome: " + this.getNome() + " | Registro: " + this.getRegistro() + "| Email: " + this.email;
    }

}
