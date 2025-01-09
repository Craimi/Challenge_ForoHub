package com.ambystudio.forohub.domain.usuarios;

import com.ambystudio.forohub.domain.cursos.DTORegistroCurso;
import com.ambystudio.forohub.domain.topicos.DTOListarTopico;
import com.ambystudio.forohub.domain.topicos.Topico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Listado de todos los usuarios
    @GetMapping
    public Page<DTOListarUsuario> listarUsuario(Pageable pagina) {
        return usuarioRepository.findAll(pagina).map(DTOListarUsuario::new);
    }

    //Detallado de un usuario especifico
    @GetMapping("/{id}")
    public Optional<DTOListarUsuario> detallarUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id).stream().map(DTOListarUsuario::new).findFirst();
    }

    //Registrar un usuario
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

    //Actualizar los valores de un usuario
    @PutMapping("/{id}")
    @Transactional
    public void actualizarUsuario(@PathVariable Long id, @RequestBody DTOActualizarUsuario actualizarUsuario){
        if(usuarioRepository.findById(id).isPresent()){
            Usuario usuario = usuarioRepository.getReferenceById(id);
            usuario.actualizarDatos(actualizarUsuario);
        }
        else{
            System.out.println("Usuario no existe");
        }
    }

    //Eliminar un curso
    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Long id){
        if(usuarioRepository.findById(id).isPresent()){
            usuarioRepository.deleteById(id);
            return "Usuario eliminado";
        }
        else{
            return "Usuario no existe";
        }
    }
}
