package cdg.oriente.projeto;

import cdg.oriente.aluno.Aluno;
import cdg.oriente.aluno.DadosListagemAluno;
import cdg.oriente.professor.DadosListagemProfessor;
import cdg.oriente.professor.Professor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record DadosListagemProjeto(
        Long id,
        String titulo,
        String descricao,
        Float nota,
        boolean aprovado,
        List<DadosListagemAluno> alunos,
        Map<String, DadosListagemProfessor> professores
) {
    public DadosListagemProjeto(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getNota(),
                projeto.isAprovado(),
                mapAlunos(projeto.getAlunos()),
                mapProfessores(projeto.getProfessores())
        );
    }

    private static List<DadosListagemAluno> mapAlunos(List<Aluno> alunos) {
        if (alunos == null) {
            return List.of();
        }
        return alunos.stream()
                .map(DadosListagemAluno::new)
                .collect(Collectors.toList());
    }

    private static Map<String, DadosListagemProfessor> mapProfessores(Map<String, Professor> professores) {
        return professores.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new DadosListagemProfessor(entry.getValue())
                ));
    }
}
