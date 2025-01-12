package com.ambystudio.forohub.domain.usuarios;

public record DTOListarUsuario(String nombre, String login, Perfil perfil) {

    public DTOListarUsuario (Usuario usuario){
        this(usuario.getNombre(), usuario.getLogin(), usuario.getPerfil());
    }

}
