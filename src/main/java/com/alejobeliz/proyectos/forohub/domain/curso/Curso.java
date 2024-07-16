package com.alejobeliz.proyectos.forohub.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Curso")
@Table(name = "cursos")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Curso(RegistroCursoDTO datos) {
        this.id = null;
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }

    public Curso(RespuestaCursoDTO datos) {
        this.id=datos.id();
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }



    public void actualizarCurso(ActualizarCursoDTO curso) {
        if (curso.nombre() != null) {
            this.nombre = curso.nombre();
        }
        if (curso.categoria() != null) {
            this.categoria = curso.categoria();
        }
    }
}
