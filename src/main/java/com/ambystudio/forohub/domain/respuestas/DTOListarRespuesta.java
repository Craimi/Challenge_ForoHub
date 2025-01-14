package com.ambystudio.forohub.domain.respuestas;

public record DTOListarRespuesta(
        Long topico,
        String mensaje,
        Boolean solucion,
        Long autor
) {
    public DTOListarRespuesta (Respuesta respuesta){
        this(respuesta.getTopico(), respuesta.getMensaje(), respuesta.getSolucion(), respuesta.getAutor());
    }
}
