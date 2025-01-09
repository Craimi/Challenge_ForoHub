package com.ambystudio.forohub.domain.usuarios;

import com.ambystudio.forohub.domain.cursos.DTORegistroCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String prueba() {
        return "Usuarios Controller";
    }

    @PostMapping
    public Usuario registrarUsuario(@RequestBody @Valid DTORegistroUsuario registroUsuario){
        Usuario usuario = new Usuario(
                registroUsuario.nombre(),
                registroUsuario.login(),
                registroUsuario.password(),
                registroUsuario.perfil()
        );

        usuarioRepository.save(usuario);

        return usuario;
    }
}
