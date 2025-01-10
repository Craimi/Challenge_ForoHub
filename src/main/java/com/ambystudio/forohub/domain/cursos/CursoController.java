package com.ambystudio.forohub.domain.cursos;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    //Listado de todos los cursos
    @GetMapping
    public ResponseEntity<Page<DTOListarCurso>> listarCurso(@PageableDefault(sort = "categoria") Pageable pagina) {
        return ResponseEntity.ok(cursoRepository.findAll(pagina).map(DTOListarCurso::new));
    }

    //Detallado de un curso especifico
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DTOListarCurso>> detallarCurso(@PathVariable Long id){
        if(cursoRepository.existsById(id)){
            return ResponseEntity.ok(cursoRepository.findById(id).stream().map(DTOListarCurso::new).findFirst());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Registrar un curso
    @PostMapping
    public ResponseEntity<DTORespuestaCurso> registrarCurso(@RequestBody @Valid DTORegistroCurso registroCurso, UriComponentsBuilder uriComponentsBuilder){
        Curso curso = cursoRepository.save(new Curso(registroCurso));

        DTORespuestaCurso respuestaCurso = new DTORespuestaCurso(curso.getNombre(), curso.getCategoria());

        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaCurso);
    }

    //Actualizar los valores de un curso
    @PutMapping("/{id}")
//    @Transactional
    public ResponseEntity actualizarCurso(@PathVariable Long id, @RequestBody DTOActualizarCurso actualizarCurso){
        if(cursoRepository.existsById(id)){
            Curso curso = cursoRepository.getReferenceById(id);
            curso.actualizarDatos(actualizarCurso);
            return ResponseEntity.ok(new DTORespuestaCurso(curso.getNombre(), curso.getCategoria()));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar un curso
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarCurso(@PathVariable Long id){
        if(cursoRepository.existsById(id)){
//            cursoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
