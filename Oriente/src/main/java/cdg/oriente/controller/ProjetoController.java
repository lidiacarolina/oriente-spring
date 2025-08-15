package cdg.oriente.controller;

import cdg.oriente.aluno.Aluno;
import cdg.oriente.aluno.AlunoRepository;
import cdg.oriente.professor.Professor;
import cdg.oriente.professor.ProfessorRepository;
import cdg.oriente.projeto.*;
import cdg.oriente.usuario.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoRepository repository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<Projeto> create(@RequestBody DadosCadastroProjeto dados) {
        try {
            if (dados.matriculas().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body((Projeto) Map.of("erro", "Não foi informado nenhum aluno no projeto"));
            }

            Projeto projeto = new Projeto(dados);
            List<Aluno> alunos = alunoRepository.findAllById(dados.matriculas());

            for (Aluno aluno : alunos) {
                if (aluno.getProjeto() != null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body((Projeto) Map.of("erro", "Aluno com matrícula " + aluno.getMatricula() + " já possui um projeto."));
                }
            }
            alunos.forEach(aluno -> aluno.setProjeto(projeto));
            alunoRepository.saveAll(alunos);

            HashMap<String, Professor> professores = new HashMap<>();

            for (Map.Entry<String, Long> dadoProfessor : dados.professores().entrySet()) {
                String cargo = dadoProfessor.getKey();
                Long idProfessor = dadoProfessor.getValue();

                Professor professor = professorRepository.findById(idProfessor)
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + idProfessor));

                professores.put(cargo, professor);
                professor.getProjetos().put(cargo, projeto);
            }

            projeto.setProfessores(professores);

            repository.save(projeto);
            return ResponseEntity.ok(projeto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<DadosListagemProjeto> get() {
        try {
            List<Projeto> projetos = repository.findAll();

            return projetos.stream()
                    .map(projeto -> new DadosListagemProjeto(projeto))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }


    @GetMapping("/{id}")
    public DadosListagemProjeto getById(@PathVariable Long id) {
        try {
            Projeto projeto = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Projeto não encontrado com id: " + id));
            projeto.setAlunos(alunoRepository.findByProjeto(projeto));
            return new DadosListagemProjeto(projeto);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}/usuarios")
    public ResponseEntity<List<String>> getPartipantes(@PathVariable Long id) {
        try {
            Projeto projeto = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Projeto não encontrado com id: " + id));

            List<Usuario> usuarios = new ArrayList<>();

            usuarios.addAll(projeto.getAlunos());
            usuarios.addAll(projeto.getProfessores().values());

            List<String> listaUsuarios = usuarios.stream()
                    .map(Usuario::toString)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(listaUsuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((List<String>) Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((List<String>) Map.of("erro", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Projeto> update(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoProjeto dados) {
        try {
            Projeto projeto = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Projeto não encontrado com id: " + id));

            projeto.update(dados);


            repository.save(projeto);

            return ResponseEntity.ok(projeto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Projeto) Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Projeto) Map.of("erro", e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            Projeto projeto = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Projeto não encontrado com id: " + id));


            List<Aluno> alunos = projeto.getAlunos();
            for (Aluno aluno : alunos) {
                aluno.setProjeto(null);
            }

            alunoRepository.saveAll(alunos);

            repository.delete(projeto);

            return ResponseEntity.ok("Projeto deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Aluno) Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Aluno) Map.of("erro", e.getMessage()));
        }
    }

}
