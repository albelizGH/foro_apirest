package com.alejobeliz.proyectos.forohub.repository;


import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Query("SELECT u FROM Usuario u WHERE u.correoElectronico = :correoElectronico")
    Optional<Usuario> findByCorreo(String correoElectronico);

    UserDetails findByCorreoElectronico(String username);
}
