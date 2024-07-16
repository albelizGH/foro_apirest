package com.alejobeliz.proyectos.forohub.domain.usuario;

public record RespuestaUsuarioDTO(
        Long id,
        String nombre
) {
    public RespuestaUsuarioDTO(Usuario datos) {
        this( datos.getId(),datos.getNombre());
    }
}
