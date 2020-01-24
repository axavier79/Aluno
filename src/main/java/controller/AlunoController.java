/**
 *
 * @author Anderson Xavier
 */
package controller;

import java.util.List;
import model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.AlunoRepository;

@RestController
@RequestMapping({"/alunos"})
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    AlunoController(AlunoRepository alunoRepository) {
        this.repository = alunoRepository;
    }

    //RECUPERAR TODOS OS ALUNOS
    @GetMapping
    public List findAll() {
        return repository.findAll();
    }

    //RECUPERAR UM ALUNO POR id
    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    //CRIAR UM NOVO ALUNO
    @PostMapping
    public Aluno create(@RequestBody Aluno aluno) {
        return repository.save(aluno);
    }

    //ATUALIZAR UM ALUNO
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
            @RequestBody Aluno aluno) {
        return repository.findById(id)
                .map(record -> {
                    record.setNome(aluno.getNome());
                    record.setIdade(aluno.getIdade());
                    Aluno updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    //EXCLUIR UM ALUNO
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
