package com.ambystudio.forohub.domain.usuarios;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
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

    public Usuario(String nombre, String login, String password) {
        this.nombre = nombre;
        this.login = login;
        this.password = password;
    }

    public Usuario(@Valid DTORegistroUsuario registroUsuario, String password) {
        this(registroUsuario.nombre(), registroUsuario.login(), password);
    }

    public void actualizarDatos(DTOActualizarUsuarioMOD actualizarUsuarioMOD) {
        if(actualizarUsuarioMOD.actualizarUsuario().nombre() != null){
            this.nombre = actualizarUsuarioMOD.actualizarUsuario().nombre();
        }
        if(actualizarUsuarioMOD.actualizarUsuario().login() != null){
            this.login = actualizarUsuarioMOD.actualizarUsuario().login();
        }
        if(actualizarUsuarioMOD.perfil() != null){
            this.perfil = actualizarUsuarioMOD.perfil();
        }
    }

    @PrePersist
    public void prePersist() {
        this.perfil = this.perfil == null ? Perfil.USUARIO : this.perfil;
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

    public String getClave() {
        return password;
    }

    public void setClave(String clave) {
        this.password = clave;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.perfil.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
