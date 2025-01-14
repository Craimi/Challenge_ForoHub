package com.ambystudio.forohub.domain.respuestas;

import java.time.LocalDateTime;

public record DTORespuesta(
        String mensaje,
        LocalDateTime fechacreacion,
        Long topico,
        Long autor,
        Boolean solucion
) {
}
