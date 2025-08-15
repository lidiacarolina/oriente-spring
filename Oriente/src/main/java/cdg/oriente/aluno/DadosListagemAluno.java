package cdg.oriente.aluno;

public record DadosListagemAluno(Long matricula, String nome) {

    public DadosListagemAluno(Aluno aluno) {
        this(aluno.getMatricula(), aluno.getNome());
    }
}
