package com.ambystudio.forohub.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTORegistroTopico(
        @NotBlank(message = "Titulo es obligatorio")
        String titulo,
        @NotBlank(message = "Mensaje es obligatorio")
        String mensaje,
        @NotNull(message = "Autor es obligatorio")
        Integer autor,
        @NotNull(message = "Curso es obligatorio")
        Integer curso
) {
}
