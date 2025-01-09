package com.ambystudio.forohub.domain.topicos;

import com.ambystudio.forohub.domain.cursos.Curso;
import com.ambystudio.forohub.domain.cursos.DTOActualizarCurso;
import com.ambystudio.forohub.domain.cursos.DTOListarCurso;
import com.ambystudio.forohub.domain.respuestas.DTORegistroRespuesta;
import com.ambystudio.forohub.domain.respuestas.Respuesta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public Page<DTOListarTopico> listarTopico(@PageableDefault(sort = "fechacreacion") Pageable pagina) {
        return topicoRepository.findAll(pagina).map(DTOListarTopico::new);
    }

    //Detallado de un topico especifico
    @GetMapping("/{id}")
    public Optional<DTOListarTopico> detallarTopico(@PathVariable Long id) {
        return topicoRepository.findById(id).stream().map(DTOListarTopico::new).findFirst();
    }

    //Registrar un topico
    @PostMapping
    public Topico registrarTopico(@RequestBody @Valid DTORegistroTopico registroTopico){
        Topico topico = new Topico(
                registroTopico.titulo(),
                registroTopico.mensaje(),
                LocalDateTime.now(),
                registroTopico.autor(),
                registroTopico.curso()
        );

        topicoRepository.save(topico);

        return topico;
    }

    //Actualizar los valores de un topico
    @PutMapping("/{id}")
    @Transactional
    public void actualizarTopico(@PathVariable Long id, @RequestBody DTOActualizarTopico actualizarTopico){
        if(topicoRepository.findById(id).isPresent()){
            Topico topico = topicoRepository.getReferenceById(id);
            topico.actualizarDatos(actualizarTopico);
        }
        else{
            System.out.println("Topico no existe");
        }
    }

    //Eliminar un topico
    @DeleteMapping("/{id}")
    public String eliminarTopico(@PathVariable Long id){
        if(topicoRepository.findById(id).isPresent()){
            topicoRepository.deleteById(id);
            return "Topico eliminado";
        }
        else{
            return "Topico no existe";
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
