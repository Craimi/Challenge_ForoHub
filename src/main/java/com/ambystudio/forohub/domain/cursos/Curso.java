package com.ambystudio.forohub.domain.cursos;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "cursos")
@Entity(name = "Medico")
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categorias categoria;

    public Curso() {
    }

    public Curso(String nombre, Categorias categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Curso(@Valid DTORegistroCurso registroCurso) {
        this(registroCurso.nombre(), registroCurso.categoria());
    }

    public void actualizarDatos(DTOActualizarCurso actualizarCurso) {
        if(actualizarCurso.nombre() != null){
            this.nombre = actualizarCurso.nombre();
        }
        if(actualizarCurso.categoria() != null){
            this.categoria = actualizarCurso.categoria();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }
}
