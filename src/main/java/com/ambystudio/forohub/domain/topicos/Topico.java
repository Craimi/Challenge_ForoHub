package com.ambystudio.forohub.domain.topicos;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechacreacion;
    private String status;
    private Integer autor; //Foraneo
    private Integer curso; //Foraneo

    public Topico() {
    }

    public Topico(String titulo, String mensaje, LocalDateTime fechacreacion, Integer autor, Integer curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechacreacion = fechacreacion;
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(@Valid DTORegistroTopico registroTopico) {
        this(registroTopico.titulo(), registroTopico.mensaje(), LocalDateTime.now(), registroTopico.autor(), registroTopico.curso());
    }

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? "ABIERTO" : this.status;
    }

    public void actualizarDatos(DTOActualizarTopico actualizarTopico) {
        if(actualizarTopico.titulo() != null){
            this.titulo = actualizarTopico.titulo();
        }
        if(actualizarTopico.mensaje() != null){
            this.mensaje = actualizarTopico.mensaje();
        }
        if(actualizarTopico.curso() != null){
            this.curso = actualizarTopico.curso();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechacreacion;
    }

    public void setFechaCreacion(LocalDateTime fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAutor() {
        return autor;
    }

    public void setAutor(Integer autor) {
        this.autor = autor;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }
}
