package com.ambystudio.forohub.domain.usuarios;

import jakarta.validation.constraints.NotNull;

public record DTOActualizarUsuarioMOD(
        DTOActualizarUsuario actualizarUsuario,
        Perfil perfil){
}
