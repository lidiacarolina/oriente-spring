package cdg.oriente.usuario;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Usuario {
    protected String nome;
    protected String email;
    protected String senha;

    public abstract void configurarCredenciais();

}
