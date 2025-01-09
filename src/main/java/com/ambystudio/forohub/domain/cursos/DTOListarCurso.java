package com.ambystudio.forohub.domain.cursos;

public record DTOListarCurso(String nombre, Categorias categoria) {

    public DTOListarCurso(Curso curso){
        this(curso.getNombre(), curso.getCategoria());
    }
}
