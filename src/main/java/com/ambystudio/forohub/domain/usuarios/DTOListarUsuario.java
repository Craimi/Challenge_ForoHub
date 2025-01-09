package com.ambystudio.forohub.domain.usuarios;

public record DTOListarUsuario(String nombre) {

    public DTOListarUsuario (Usuario usuario){
        this(usuario.getNombre());
    }

}
