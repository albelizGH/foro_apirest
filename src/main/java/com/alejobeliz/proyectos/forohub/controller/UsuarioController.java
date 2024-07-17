package com.alejobeliz.proyectos.forohub.controller;

import com.alejobeliz.proyectos.forohub.domain.usuario.ActualizarUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.RegistroUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.RespuestaUsuarioDTO;
import com.alejobeliz.proyectos.forohub.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios")
public class UsuarioController {

    private UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en el sistema.")
    @PostMapping
    public ResponseEntity<RespuestaUsuarioDTO> crearUsuario(@RequestBody @Valid RegistroUsuarioDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        RespuestaUsuarioDTO response = service.registrarUsuario(datos);
        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista paginada de todos los usuarios registrados.")
    @GetMapping
    public ResponseEntity<Page<RespuestaUsuarioDTO>> listarUsuarios(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page page = service.listarUsuarios(paginacion);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve un usuario basado en el ID proporcionado.")
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        RespuestaUsuarioDTO response = service.getUsuarioPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza la información de un usuario existente.")
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid ActualizarUsuarioDTO datos) {
        RespuestaUsuarioDTO response = service.actualizarUsuario(id, datos);
        return ResponseEntity.ok(response);
    }

}
