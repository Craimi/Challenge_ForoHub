package com.ambystudio.forohub.domain.usuarios;

import com.ambystudio.forohub.infra.security.RoleValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Listado de todos los usuarios (SOLO MODERADORES)
    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<DTOListarUsuario>> listarUsuario(Pageable pagina) {
        return ResponseEntity.ok(usuarioRepository.findAll(pagina).map(DTOListarUsuario::new));
    } //Completo

    //Detallado de un usuario especifico (SOLO MODERADORES)
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Optional<DTOListarUsuario>> detallarUsuario(@PathVariable Long id) {
        if(!usuarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarioRepository.findById(id).stream().map(DTOListarUsuario::new).findFirst());
    } //Completo

    //Registrar un usuario (Todos los usuarios (Incluso sin autenticar))
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid DTORegistroUsuario registroUsuario, UriComponentsBuilder uriComponentsBuilder){
        String password = passwordEncoder.encode(registroUsuario.password());

        Usuario usuario = usuarioRepository.save(new Usuario(registroUsuario, password));

        DTORespuestaUsuario respuestaUsuario = new DTORespuestaUsuario(usuario.getNombre(), usuario.getLogin(), usuario.getPerfil());

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaUsuario);
    } //Completo

    //Actualizar los valores de un usuario (SOLO MODERADORES)
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @Transactional
    public ResponseEntity<DTORespuestaUsuario> actualizarUsuario(@PathVariable Long id, @RequestBody DTOActualizarUsuarioMOD actualizarUsuarioMOD){
        System.out.println(actualizarUsuarioMOD.toString());
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizarDatos(actualizarUsuarioMOD);
        return ResponseEntity.ok(new DTORespuestaUsuario(usuario.getNombre(), usuario.getLogin(), usuario.getPerfil()));
    } //Completo

    //Eliminar un usuario (SOLO MODERADORES)
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    } //Completo
}
