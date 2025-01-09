package com.ambystudio.forohub.domain.topicos;

import com.ambystudio.forohub.domain.respuestas.DTORegistroRespuesta;
import com.ambystudio.forohub.domain.respuestas.Respuesta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public String prueba() {
        return "Topicos Controller";
    }

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
