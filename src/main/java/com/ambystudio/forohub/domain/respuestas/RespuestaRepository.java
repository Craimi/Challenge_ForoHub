package com.ambystudio.forohub.domain.respuestas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Optional<Respuesta> findByTopicoAndId(Long id, Long idRespuesta);
}
