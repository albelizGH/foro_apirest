package com.alejobeliz.proyectos.forohub.domain.topico;

import java.time.LocalDate;

public record RespuestTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDate fechaCreacion
) {

    public RespuestTopicoDTO(Topico datos){
        this(
                datos.getId(),
                datos.getTitulo(),
                datos.getMensaje(),
                datos.getFechaCreacion()
        );

    }

}
