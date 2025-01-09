package com.ambystudio.forohub.domain.respuestas;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Long topico; //Foraneo
    private Integer autor; //Foraneo
    private Boolean solucion;

    public Respuesta(String mensaje, LocalDateTime fechaCreacion, Long topico, Integer autor) {
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.topico = topico;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getTopico() {
        return topico;
    }

    public void setTopico(Long topico) {
        this.topico = topico;
    }

    public Integer getAutor() {
        return autor;
    }

    public void setAutor(Integer autor) {
        this.autor = autor;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }
}
