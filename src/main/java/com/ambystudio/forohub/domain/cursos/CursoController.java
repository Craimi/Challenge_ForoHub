package com.ambystudio.forohub.domain.cursos;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    //Listado de todos los cursos
    @GetMapping
    public Page<DTOListarCurso> listarCurso(@PageableDefault(sort = "categoria") Pageable pagina) {
        return cursoRepository.findAll(pagina).map(DTOListarCurso::new);
    }

    //Detallado de un curso especifico
    @GetMapping("/{id}")
    public Optional<DTOListarCurso> detallarCurso(@PathVariable Long id){
        return cursoRepository.findById(id).stream().map(DTOListarCurso::new).findFirst();
    }

    //Registrar un curso
    @PostMapping
    public Curso registrarCurso(@RequestBody @Valid DTORegistroCurso registroCurso){
        Curso curso = new Curso(
                registroCurso.nombre(),
                registroCurso.categoria()
        );

        cursoRepository.save(curso);

        return curso;
    }

    //Actualizar los valores de un curso
    @PutMapping("/{id}")
    @Transactional
    public void actualizarCurso(@PathVariable Long id, @RequestBody DTOActualizarCurso actualizarCurso){
        if(cursoRepository.findById(id).isPresent()){
            Curso curso = cursoRepository.getReferenceById(id);
            curso.actualizarDatos(actualizarCurso);
        }
        else{
            System.out.println("Curso no existe");
        }
    }

    //Eliminar un curso
    @DeleteMapping("/{id}")
    public String eliminarCurso(@PathVariable Long id){
        if(cursoRepository.findById(id).isPresent()){
            cursoRepository.deleteById(id);
            return "Curso eliminado";
        }
        else{
            return "Curso no existe";
        }
    }
}
