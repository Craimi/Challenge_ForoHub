package com.ambystudio.forohub.domain.usuarios;

public record DTORespuestaUsuario(
        String nombre,
        String login,
//        String password,
        Perfil perfil
) {
}
