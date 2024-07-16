package com.alejobeliz.proyectos.forohub.repository;

import com.alejobeliz.proyectos.forohub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico,Long> {


    @Query("SELECT t FROM Topico t WHERE t.titulo = :titulo AND t.mensaje = :mensaje")
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    Optional<Topico> findById(Long id);

}
