package com.ambystudio.forohub.domain.topicos;

public record DTOActualizarTopico(
        String titulo,
        String mensaje,
        Long curso,
        String status
) {
}
