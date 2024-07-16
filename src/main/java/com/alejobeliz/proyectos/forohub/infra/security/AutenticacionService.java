package com.alejobeliz.proyectos.forohub.infra.security;

import com.alejobeliz.proyectos.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {//Aca empieza la logica para hacer login y aplicar seguridad a mi app

    private UsuarioRepository usuarioRepository;

    @Autowired
    public AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /*Este metodo me va a pedir que le pase el usuario para que el lo valide
     * Para eso necesito el usuario que lo voy a obtener pidiendoselo a mi base de datos creando un metodo en el repository*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoElectronico(username);
    }

}
