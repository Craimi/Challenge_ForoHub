package com.ambystudio.forohub.domain.topicos;

public record DTORespuestaTopico(
        String titulo,
        String mensaje,
        Long autor,
        Long curso,
        String status
) {
}
