package com.ambystudio.forohub.domain.respuestas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @GetMapping
    public String prueba() {
        return "Respuestas Controller";
    }

    @PostMapping
    public Respuesta registrarRespuesta(@RequestBody DTORegistroRespuesta registroRespuesta){
//        Respuesta respuesta = new Respuesta(
//                registroRespuesta.mensaje(),
//                registroRespuesta.topico(),
//                registroRespuesta.autor(),
//                LocalDateTime.now()
//        );

        //respuestaRepository.save(respuesta);

//        return respuesta;
        return null;
    }
}
