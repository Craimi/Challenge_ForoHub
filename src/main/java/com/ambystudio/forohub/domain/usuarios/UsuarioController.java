package com.ambystudio.forohub.domain.usuarios;

import com.ambystudio.forohub.infra.security.RoleValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Listado de todos los usuarios (SOLO MODERADORES)
    @GetMapping
    public ResponseEntity<Page<DTOListarUsuario>> listarUsuario(Pageable pagina) {
        if(!RoleValidator.esModerador()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(usuarioRepository.findAll(pagina).map(DTOListarUsuario::new));
    }

    //Detallado de un usuario especifico (SOLO MODERADORES)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DTOListarUsuario>> detallarUsuario(@PathVariable Long id) {
        if(!RoleValidator.esModerador()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if(!usuarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarioRepository.findById(id).stream().map(DTOListarUsuario::new).findFirst());
    }

    //Registrar un usuario (Todos los usuarios)
    @PostMapping
    public ResponseEntity<DTORespuestaUsuario> registrarUsuario(@RequestBody @Valid DTORegistroUsuario registroUsuario, UriComponentsBuilder uriComponentsBuilder){
        Usuario usuario = usuarioRepository.save(new Usuario(registroUsuario));

        DTORespuestaUsuario respuestaUsuario = new DTORespuestaUsuario(usuario.getNombre(), usuario.getLogin(), usuario.getClave(), usuario.getPerfil());

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaUsuario);
    }

    //Actualizar los valores de un usuario (SOLO MODERADORES [Por ahora])
    @PutMapping("/{id}")
//    @Transactional
    public ResponseEntity actualizarUsuario(@PathVariable Long id, @RequestBody DTOActualizarUsuarioMOD actualizarUsuarioMOD){
        if(!RoleValidator.esModerador()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizarDatos(actualizarUsuarioMOD);
        return ResponseEntity.ok(new DTORespuestaUsuario(usuario.getNombre(), usuario.getLogin(), usuario.getClave(), usuario.getPerfil()));
    }

    //Eliminar un usuario (SOLO MODERADORES)
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        if(!RoleValidator.esModerador()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

//        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
