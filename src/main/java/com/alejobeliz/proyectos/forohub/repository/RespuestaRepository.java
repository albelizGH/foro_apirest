package com.alejobeliz.proyectos.forohub.repository;

import com.alejobeliz.proyectos.forohub.domain.respuesta.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {

    Optional<Respuesta> findById(Long id);

}
