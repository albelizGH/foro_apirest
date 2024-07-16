package com.alejobeliz.proyectos.forohub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record RegistroUsuarioDTO(
        @NotBlank String nombre,
        @NotBlank String correo,
        @NotBlank String contrasena
) {
}
