package com.alejobeliz.proyectos.forohub.domain.respuesta;

import com.alejobeliz.proyectos.forohub.domain.curso.Categoria;
import com.alejobeliz.proyectos.forohub.domain.curso.Curso;
import com.alejobeliz.proyectos.forohub.domain.curso.RespuestaCursoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RespuestaRespuestaDTO(
        Long id,
        String mensaje,
        Long topico_id,
        LocalDateTime fechaCreacion,
        Long autor_id
) {
    public RespuestaRespuestaDTO(Respuesta respuesta) {
        this(respuesta.getId(),respuesta.getMensaje(),respuesta.getTopico().getId(), LocalDateTime.now(),respuesta.getAutor().getId());
    }
}
