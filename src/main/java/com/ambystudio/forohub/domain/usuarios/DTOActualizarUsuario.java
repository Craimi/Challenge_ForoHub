package com.ambystudio.forohub.domain.usuarios;

import jakarta.validation.constraints.Email;

public record DTOActualizarUsuario(
        String nombre,
        @Email
        String login) {

}