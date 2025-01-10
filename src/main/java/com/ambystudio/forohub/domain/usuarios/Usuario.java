package com.ambystudio.forohub.domain.usuarios;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public Usuario() {
    }

    public Usuario(String nombre, String login, String password, Perfil perfil) {
        this.nombre = nombre;
        this.login = login;
        this.password = password;
        this.perfil = perfil;
    }

    public Usuario(@Valid DTORegistroUsuario registroUsuario) {
        this(registroUsuario.nombre(), registroUsuario.login(), registroUsuario.password(), registroUsuario.perfil());
    }

    public void actualizarDatos(DTOActualizarUsuario actualizarUsuario) {
        if(actualizarUsuario.nombre() != null){
            this.nombre = actualizarUsuario.nombre();
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
