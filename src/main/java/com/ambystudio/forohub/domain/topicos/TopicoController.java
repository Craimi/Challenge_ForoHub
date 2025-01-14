package com.ambystudio.forohub.domain.topicos;

import com.ambystudio.forohub.domain.respuestas.*;
import com.ambystudio.forohub.domain.usuarios.Usuario;
import com.ambystudio.forohub.infra.security.RoleValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    //Listado de todos los topicos (Todos los usuarios)
    @GetMapping
    public ResponseEntity<Page<DTOListarTopico>> listarTopico(@PageableDefault(sort = "fechacreacion") Pageable pagina) {
        return ResponseEntity.ok(topicoRepository.findAll(pagina).map(DTOListarTopico::new));
    } //Completo

    //Detallado de un topico especifico (Todos los usuarios)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DTOListarTopico>> detallarTopico(@PathVariable Long id) {
        if(topicoRepository.existsById(id)){
            return ResponseEntity.ok(topicoRepository.findById(id).stream().map(DTOListarTopico::new).findFirst());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    } //Completo

    //Registrar un topico (Todos los usuarios)
    @PostMapping
    public ResponseEntity<DTORespuestaTopico> registrarTopico(@RequestBody @Valid DTORegistroTopico registroTopico, UriComponentsBuilder uriComponentsBuilder){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();

        Topico topico = topicoRepository.save(new Topico(registroTopico, usuarioId));

        DTORespuestaTopico respuestaTopico = new DTORespuestaTopico(topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso(), topico.getStatus());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaTopico);
    } //Completo

    //Actualizar los valores de un topico (SOLO MODERADORES)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DTORespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody DTOActualizarTopico actualizarTopico){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarDatos(actualizarTopico);
        return ResponseEntity.ok(new DTORespuestaTopico(topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso(), topico.getStatus()));
    } //Completo

    //Eliminar un topico (SOLO MODERADORES)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id){
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    } //Completo

    //Respuesta Controller

    @Autowired
    private RespuestaRepository respuestaRepository;

    //Listado de respuestas (Todos los usuarios)
    @GetMapping("/{id}/respuestas")
    public ResponseEntity<Page<DTOListarRespuesta>> listarRespuesta(@PageableDefault(sort = "fechacreacion") Pageable pagina) {
        return ResponseEntity.ok(respuestaRepository.findAll(pagina).map(DTOListarRespuesta::new));
    } //Completo

    //Detallado de una respuesta (Todos los usuarios)
    @GetMapping("/{id}/respuestas/{idRespuesta}")
    public ResponseEntity<Optional<DTOListarRespuesta>> detallarRespuesta(@PathVariable Long id, @PathVariable Long idRespuesta) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Optional<Respuesta> respuesta = respuestaRepository.findByTopicoAndId(id, idRespuesta);

        if (respuesta.isPresent()) {
            return ResponseEntity.ok(respuestaRepository.findByTopicoAndId(id, idRespuesta).stream().map(DTOListarRespuesta::new).findFirst());
        } else {
            return ResponseEntity.notFound().build();
        }
    } //Completo

    //Registrar una respuesta (Todos los usuarios)
    @PostMapping("/{id}/respuestas")
    public ResponseEntity<DTORespuesta> agregarRespuesta(@PathVariable Long id, @RequestBody DTORegistroRespuesta registroRespuesta, UriComponentsBuilder uriComponentsBuilder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();

        Respuesta respuesta = respuestaRepository.save(new Respuesta(registroRespuesta, id, usuarioId));

        DTORespuesta respuestaConsulta = new DTORespuesta(respuesta.getMensaje(), respuesta.getFechaCreacion(), respuesta.getTopico(), respuesta.getAutor(), respuesta.getSolucion());

        URI url = uriComponentsBuilder.path("/{id}/{idRespuesta}").buildAndExpand(id ,respuesta.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaConsulta);
    } //Completo

    //Actualizar los valores de una respuesta (SOLO MODERADORES)
    @PutMapping("/{id}/respuestas/{idRespuesta}")
    @Transactional
    public ResponseEntity<DTORespuesta> actualizarRespuesta(@PathVariable Long id, @PathVariable Long idRespuesta, @RequestBody DTOActualizarRespuesta actualizarRespuesta){
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Respuesta respuesta = respuestaRepository.findByTopicoAndId(id, idRespuesta).get();
        respuesta.actualizarDatos(actualizarRespuesta);
        return ResponseEntity.ok(new DTORespuesta(respuesta.getMensaje(), respuesta.getFechaCreacion(), respuesta.getTopico(), respuesta.getAutor(), respuesta.getSolucion()));
    } //Completo

    //Eliminar una respuesta (SOLO MODERADORES)
    @DeleteMapping("/{id}/respuestas/{idRespuesta}")
    public ResponseEntity<?> eliminarRespuesta(@PathVariable Long id, @PathVariable Long idRespuesta){
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Respuesta> respuestaOptional = respuestaRepository.findByTopicoAndId(id, idRespuesta);

        if (respuestaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        respuestaRepository.deleteById(idRespuesta);

        return ResponseEntity.noContent().build();
    } //Completo
}
