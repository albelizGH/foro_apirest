package com.alejobeliz.proyectos.forohub.domain.topico;



import com.alejobeliz.proyectos.forohub.domain.curso.RegistroCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.curso.RespuestaCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.RespuestaUsuarioDTO;

import java.time.LocalDate;

public record RespuestaTopicoExtensaDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDate fechaCreacion,
        Estado estado,
        RespuestaUsuarioDTO autor,
        RespuestaCursoDTO curso
) {

    public RespuestaTopicoExtensaDTO(Topico datos){
        this(
                datos.getId(),
                datos.getTitulo(),
                datos.getMensaje(),
                datos.getFechaCreacion(),
                datos.getStatus(),
                new RespuestaUsuarioDTO(datos.getAutor()),
                new RespuestaCursoDTO(datos.getCurso())
        );

    }

}
