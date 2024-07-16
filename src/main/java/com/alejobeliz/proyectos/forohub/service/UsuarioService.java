package com.alejobeliz.proyectos.forohub.service;


import com.alejobeliz.proyectos.forohub.domain.usuario.ActualizarUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.RegistroUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.RespuestaUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import com.alejobeliz.proyectos.forohub.infra.errores.ValidacionDeIntegridad;
import com.alejobeliz.proyectos.forohub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RespuestaUsuarioDTO registrarUsuario(RegistroUsuarioDTO datos) {
        //Verificamos que no este ya creado
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByCorreo(datos.correo());

        if (usuarioBuscado.isPresent()) {
            throw new ValidacionDeIntegridad("El correo " + datos.correo() + " ya esta registrado");
        }

        String nombreUsuario = datos.nombre();
        String correoElectronico = datos.correo();
        String contrasena = datos.contrasena();

        //Ahora encriptamos la contraseña
        contrasena = passwordEncoder.encode(contrasena);

        Usuario nuevoUsuario = new Usuario(null, nombreUsuario, correoElectronico, contrasena);

        usuarioRepository.save(nuevoUsuario);

        return new RespuestaUsuarioDTO(nuevoUsuario);
    }

    public Page<RespuestaUsuarioDTO> listarUsuarios(Pageable usuarios) {
        Page page = usuarioRepository.findAll(usuarios).map(RespuestaUsuarioDTO::new);
        return page;
    }

    @Transactional
    public RespuestaUsuarioDTO actualizarUsuario(Long id, ActualizarUsuarioDTO datos) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
        if (!usuarioBuscado.isPresent()) {
            throw new ValidacionDeIntegridad("No hay usuarios que correspondan a ese Id");
        }
        Usuario usuario = usuarioBuscado.get();
        usuario.actualizarDatos(datos);
        usuarioRepository.save(usuario);
        return new RespuestaUsuarioDTO(usuario);
    }

    public RespuestaUsuarioDTO getUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (!usuario.isPresent()) {
            throw new ValidacionDeIntegridad("No hay usuarios que correspondan a ese Id");
        }
        return new RespuestaUsuarioDTO(usuario.get());
    }
}

