package com.ambystudio.forohub.domain.cursos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTORegistroCurso(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,
        @NotNull(message = "Categoria es obligatoria y todo en MAYUS")
        Categorias categoria
) {
}
