package com.alejobeliz.proyectos.forohub.controller;


import com.alejobeliz.proyectos.forohub.domain.curso.ActualizarCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.curso.RegistroCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.curso.RespuestaCursoDTO;
import com.alejobeliz.proyectos.forohub.service.CursoService;
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
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<RespuestaCursoDTO> crearCurso(@RequestBody @Valid RegistroCursoDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        RespuestaCursoDTO response = cursoService.registrarCurso(datos);
        URI uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<RespuestaCursoDTO>> listarCursos(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<RespuestaCursoDTO> page = cursoService.listarCursos(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaCursoDTO> obtenerCursoPorId(@PathVariable Long id) {
        RespuestaCursoDTO response = cursoService.getCursoPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaCursoDTO> actualizarCurso(@PathVariable Long id, @RequestBody @Valid ActualizarCursoDTO datos) {
        RespuestaCursoDTO response = cursoService.actualizarCurso(id, datos);
        return ResponseEntity.ok(response);
    }

}
