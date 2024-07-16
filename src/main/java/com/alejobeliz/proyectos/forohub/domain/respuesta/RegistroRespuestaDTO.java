package com.alejobeliz.proyectos.forohub.domain.respuesta;

import com.alejobeliz.proyectos.forohub.domain.curso.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroRespuestaDTO(
        @NotBlank
        String mensaje,
        @NotNull
        Long topico_id,
        @NotNull
        Long autor_id

) {
}
