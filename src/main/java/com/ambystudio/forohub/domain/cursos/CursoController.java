package com.ambystudio.forohub.domain.cursos;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String prueba() {
        return "Cursos Controller";
    }

    @PostMapping
    public Curso registrarCurso(@RequestBody @Valid DTORegistroCurso registroCurso){
        Curso curso = new Curso(
                registroCurso.nombre(),
                registroCurso.categoria()
        );

        cursoRepository.save(curso);

        return curso;
    }
}
