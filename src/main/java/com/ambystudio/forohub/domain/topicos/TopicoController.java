package com.ambystudio.forohub.domain.topicos;

import com.ambystudio.forohub.domain.cursos.Curso;
import com.ambystudio.forohub.domain.cursos.DTOActualizarCurso;
import com.ambystudio.forohub.domain.cursos.DTOListarCurso;
import com.ambystudio.forohub.domain.cursos.DTORespuestaCurso;
import com.ambystudio.forohub.domain.respuestas.DTORegistroRespuesta;
import com.ambystudio.forohub.domain.respuestas.Respuesta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    //Listado de todos los topicos
    @GetMapping
    public ResponseEntity<Page<DTOListarTopico>> listarTopico(@PageableDefault(sort = "fechacreacion") Pageable pagina) {
        return ResponseEntity.ok(topicoRepository.findAll(pagina).map(DTOListarTopico::new));
    }

    //Detallado de un topico especifico
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DTOListarTopico>> detallarTopico(@PathVariable Long id) {
        if(topicoRepository.existsById(id)){
            return ResponseEntity.ok(topicoRepository.findById(id).stream().map(DTOListarTopico::new).findFirst());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Registrar un topico
    @PostMapping
    public ResponseEntity<DTORespuestaTopico> registrarTopico(@RequestBody @Valid DTORegistroTopico registroTopico, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoRepository.save(new Topico(registroTopico));

        DTORespuestaTopico respuestaTopico = new DTORespuestaTopico(topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaTopico);
    }

    //Actualizar los valores de un topico
    @PutMapping("/{id}")
//    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody DTOActualizarTopico actualizarTopico){
        if(topicoRepository.findById(id).isPresent()){
            Topico topico = topicoRepository.getReferenceById(id);
            topico.actualizarDatos(actualizarTopico);
            return ResponseEntity.ok(new DTORespuestaTopico(topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso()));

        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar un topico
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        if(topicoRepository.findById(id).isPresent()){
//            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/respuesta")
    public Respuesta agregarRespuesta(@PathVariable Long id, @RequestBody DTORegistroRespuesta registroRespuesta) {
        Respuesta respuesta = new Respuesta(
                registroRespuesta.mensaje(),
                LocalDateTime.now(),
                id,
                null
        );

        return respuesta;
//        respuestaRepository.save(respuesta);
    }
}
