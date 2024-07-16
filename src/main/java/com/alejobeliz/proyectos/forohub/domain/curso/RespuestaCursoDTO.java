package com.alejobeliz.proyectos.forohub.domain.curso;

public record RespuestaCursoDTO(
        Long id,
        String nombre,
        Categoria categoria
) {
    public RespuestaCursoDTO(Curso curso) {
        this(curso.getId(),curso.getNombre(),curso.getCategoria());
    }
}
