package com.ambystudio.forohub.domain.topicos;

import com.ambystudio.forohub.domain.respuestas.DTORegistroRespuesta;
import com.ambystudio.forohub.domain.respuestas.Respuesta;
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
