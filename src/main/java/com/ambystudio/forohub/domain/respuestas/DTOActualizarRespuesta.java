package com.ambystudio.forohub.domain.respuestas;

public record DTOActualizarRespuesta(
        String mensaje,
        Long topico,
        Boolean solucion
) {
}
