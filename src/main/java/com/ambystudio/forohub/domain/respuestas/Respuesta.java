package com.ambystudio.forohub.domain.respuestas;

import com.ambystudio.forohub.domain.topicos.DTOActualizarTopico;
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
    private LocalDateTime fechacreacion;
    private Long topico; //Foraneo
    private Long autor; //Foraneo
    private Boolean solucion;

    public Respuesta() {
    }

    public Respuesta(DTORegistroRespuesta registroRespuesta, Long id, Long usuarioId) {
        this.mensaje = registroRespuesta.mensaje();
        this.fechacreacion = LocalDateTime.now();
        this.topico = id;
        this.autor = usuarioId;
    }

    @PrePersist
    public void prePersist() {
        this.solucion = this.solucion == null ? Boolean.FALSE : this.solucion;
    }

    public void actualizarDatos(DTOActualizarRespuesta actualizarRespuesta) {
        if(actualizarRespuesta.mensaje() != null){
            this.mensaje = actualizarRespuesta.mensaje();
        }
        if(actualizarRespuesta.topico() != null){
            this.topico = actualizarRespuesta.topico();
        }
        if(actualizarRespuesta.solucion() != null){
            this.solucion = actualizarRespuesta.solucion();
        }
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
        return fechacreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechacreacion = fechaCreacion;
    }

    public Long getTopico() {
        return topico;
    }

    public void setTopico(Long topico) {
        this.topico = topico;
    }

    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }
}
