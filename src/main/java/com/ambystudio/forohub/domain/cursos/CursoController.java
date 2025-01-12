package com.ambystudio.forohub.domain.cursos;

import com.ambystudio.forohub.infra.security.RoleValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

    //Listado de todos los cursos (Todos los usuarios)
    @GetMapping
    public ResponseEntity<Page<DTOListarCurso>> listarCurso(@PageableDefault(sort = "categoria") Pageable pagina) {
        return ResponseEntity.ok(cursoRepository.findAll(pagina).map(DTOListarCurso::new));
    } //Completo

    //Detallado de un curso especifico (Todos los usuarios)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DTOListarCurso>> detallarCurso(@PathVariable Long id){
        if(cursoRepository.existsById(id)){
            return ResponseEntity.ok(cursoRepository.findById(id).stream().map(DTOListarCurso::new).findFirst());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    } //Completo

    //Registrar un curso (SOLO MODERADORES)
    @PostMapping
    public ResponseEntity<DTORespuestaCurso> registrarCurso(@RequestBody @Valid DTORegistroCurso registroCurso, UriComponentsBuilder uriComponentsBuilder){
        Curso curso = cursoRepository.save(new Curso(registroCurso));

        DTORespuestaCurso respuestaCurso = new DTORespuestaCurso(curso.getNombre(), curso.getCategoria());

        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaCurso);
    } //Completo

    //Actualizar los valores de un curso (SOLO MODERADORES)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DTORespuestaCurso> actualizarCurso(@PathVariable Long id, @RequestBody DTOActualizarCurso actualizarCurso){
        Curso curso = cursoRepository.getReferenceById(id);
        curso.actualizarDatos(actualizarCurso);
        return ResponseEntity.ok(new DTORespuestaCurso(curso.getNombre(), curso.getCategoria()));
    } //Completo

    //Eliminar un curso (SOLO MODERADORES)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    } //Completo
}
