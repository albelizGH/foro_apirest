package com.alejobeliz.proyectos.forohub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroCursoDTO(
        @NotBlank
        String nombre,
        @NotNull
        Categoria categoria
) {
}
