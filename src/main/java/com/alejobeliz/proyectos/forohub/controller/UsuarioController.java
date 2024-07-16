package com.alejobeliz.proyectos.forohub.controller;

import com.alejobeliz.proyectos.forohub.domain.usuario.ActualizarUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.RegistroUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.RespuestaUsuarioDTO;
import com.alejobeliz.proyectos.forohub.service.UsuarioService;
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
public class UsuarioController {

    private UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RespuestaUsuarioDTO> crearUsuario(@RequestBody @Valid RegistroUsuarioDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        RespuestaUsuarioDTO response = service.registrarUsuario(datos);
        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<RespuestaUsuarioDTO>> listarConsultas(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page page = service.listarUsuarios(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        RespuestaUsuarioDTO response = service.getUsuarioPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid ActualizarUsuarioDTO datos) {
        RespuestaUsuarioDTO response = service.actualizarUsuario(id, datos);
        return ResponseEntity.ok(response);
    }

}
