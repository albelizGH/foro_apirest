package com.alejobeliz.proyectos.forohub.repository;


import com.alejobeliz.proyectos.forohub.domain.curso.Categoria;
import com.alejobeliz.proyectos.forohub.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {

    Optional<Curso> findByNombre(String nombre);

    Optional<Curso> findByNombreAndCategoria(String nombre, Categoria categoria);
}
