package com.alejobeliz.proyectos.forohub.controller;


import com.alejobeliz.proyectos.forohub.domain.topico.*;
import com.alejobeliz.proyectos.forohub.service.TopicoService;
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
@RequestMapping("/topicos")
public class TopicoController {

    private TopicoService service;

    @Autowired
    public TopicoController(TopicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RespuestTopicoDTO> registrarTopico(@RequestBody @Valid RegistroTopicoDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        RespuestTopicoDTO response = service.registrarTopico(datos);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<RespuestaTopicoExtensaDTO>> listarConsultas(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page page = service.listaTopicos(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopicoExtensaDTO> obtenerTopicoPorId(@PathVariable Long id) {
        RespuestaTopicoExtensaDTO response = service.getTopicoPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaTopicoExtensaDTO> actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActualizacionTopicoDTO datos) {
        RespuestaTopicoExtensaDTO response = service.actualizarTopico(id, datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarTopico(@PathVariable Long id) {
        service.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
