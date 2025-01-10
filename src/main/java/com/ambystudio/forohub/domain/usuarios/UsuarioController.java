package com.ambystudio.forohub.domain.usuarios;

import com.ambystudio.forohub.domain.cursos.DTORegistroCurso;
import com.ambystudio.forohub.domain.topicos.DTOListarTopico;
import com.ambystudio.forohub.domain.topicos.Topico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Optional<DTOListarUsuario>> detallarUsuario(@PathVariable Long id) {
        if(usuarioRepository.existsById(id)){
            return ResponseEntity.ok(usuarioRepository.findById(id).stream().map(DTOListarUsuario::new).findFirst());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Registrar un usuario
    @PostMapping
    public ResponseEntity<DTORespuestaUsuario> registrarUsuario(@RequestBody @Valid DTORegistroUsuario registroUsuario, UriComponentsBuilder uriComponentsBuilder){
        Usuario usuario = usuarioRepository.save(new Usuario(registroUsuario));

        DTORespuestaUsuario respuestaUsuario = new DTORespuestaUsuario(usuario.getNombre());

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaUsuario);
    }

    //Actualizar los valores de un usuario
    @PutMapping("/{id}")
//    @Transactional
    public ResponseEntity actualizarUsuario(@PathVariable Long id, @RequestBody DTOActualizarUsuario actualizarUsuario){
        if(usuarioRepository.findById(id).isPresent()){
            Usuario usuario = usuarioRepository.getReferenceById(id);
            usuario.actualizarDatos(actualizarUsuario);
            return ResponseEntity.ok(new DTORespuestaUsuario(usuario.getNombre()));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar un curso
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        if(usuarioRepository.findById(id).isPresent()){
//            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
