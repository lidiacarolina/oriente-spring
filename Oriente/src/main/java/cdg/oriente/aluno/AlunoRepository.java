package cdg.oriente.aluno;

import cdg.oriente.projeto.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByProjeto(Projeto projeto);
}
