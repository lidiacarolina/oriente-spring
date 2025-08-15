package cdg.oriente.professor;

public record DadosListagemProfessor(Long registro, String nome) {

    public DadosListagemProfessor(Professor professor){
        this(professor.getRegistro(), professor.getNome());
    }
}
