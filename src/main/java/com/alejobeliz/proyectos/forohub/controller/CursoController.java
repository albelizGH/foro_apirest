package com.alejobeliz.proyectos.forohub.controller;


import com.alejobeliz.proyectos.forohub.domain.curso.ActualizarCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.curso.RegistroCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.curso.RespuestaCursoDTO;
import com.alejobeliz.proyectos.forohub.service.CursoService;
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
@RequestMapping("/cursos")
@Tag(name = "Cursos")
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @Operation(summary = "Crear un nuevo curso", description = "Crea un nuevo curso en el sistema.")
    @PostMapping
    public ResponseEntity<RespuestaCursoDTO> crearCurso(@RequestBody @Valid RegistroCursoDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        RespuestaCursoDTO response = cursoService.registrarCurso(datos);
        URI uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Listar todos los cursos", description = "Devuelve una lista paginada de todos los cursos disponibles.")
    @GetMapping
    public ResponseEntity<Page<RespuestaCursoDTO>> listarCursos(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<RespuestaCursoDTO> page = cursoService.listarCursos(paginacion);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Obtener un curso por ID", description = "Devuelve un curso basado en el ID proporcionado.")
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaCursoDTO> obtenerCursoPorId(@PathVariable Long id) {
        RespuestaCursoDTO response = cursoService.getCursoPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar un curso existente", description = "Actualiza la información de un curso existente.")
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaCursoDTO> actualizarCurso(@PathVariable Long id, @RequestBody @Valid ActualizarCursoDTO datos) {
        RespuestaCursoDTO response = cursoService.actualizarCurso(id, datos);
        return ResponseEntity.ok(response);
    }

}
