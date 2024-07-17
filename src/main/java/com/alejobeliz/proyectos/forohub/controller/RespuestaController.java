package com.alejobeliz.proyectos.forohub.controller;


import com.alejobeliz.proyectos.forohub.domain.respuesta.ActualizarRespuestaDTO;
import com.alejobeliz.proyectos.forohub.domain.respuesta.RegistroRespuestaDTO;
import com.alejobeliz.proyectos.forohub.domain.respuesta.RespuestaRespuestaDTO;
import com.alejobeliz.proyectos.forohub.service.RespuestaService;
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
@RequestMapping("/respuestas")
@Tag(name = "Respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    @Autowired
    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @Operation(summary = "Crear una nueva respuesta", description = "Crea una nueva respuesta en el sistema.")
    @PostMapping
    public ResponseEntity<RespuestaRespuestaDTO> crearRespuesta(@RequestBody @Valid RegistroRespuestaDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        RespuestaRespuestaDTO response = respuestaService.registrarRespuesta(datos);
        URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Listar todas las respuestas", description = "Devuelve una lista paginada de todas las respuestas disponibles.")
    @GetMapping
    public ResponseEntity<Page<RespuestaRespuestaDTO>> listarRespuestas(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<RespuestaRespuestaDTO> page = respuestaService.listarRespuestas(paginacion);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Obtener una respuesta por ID", description = "Devuelve una respuesta basada en el ID proporcionado.")
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaRespuestaDTO> obtenerRespuestaPorId(@PathVariable Long id) {
        RespuestaRespuestaDTO respuesta = respuestaService.getRespuestaPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Actualizar una respuesta existente", description = "Actualiza la información de una respuesta existente.")
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaRespuestaDTO> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid ActualizarRespuestaDTO datos) {
        RespuestaRespuestaDTO response = respuestaService.actualizarRespuesta(id, datos);
        return ResponseEntity.ok(response);
    }
}
