package com.alejobeliz.proyectos.forohub.infra.security;

import com.alejobeliz.proyectos.forohub.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1-Obtengo el token del header
        var tokenJWT = recuperarToken(request);
        /*if(token=="" || token == null){
            throw new RuntimeException("El token enviado no es valido");//Hacemos esto porque si la peticion no viene con token romperia el codigo
        }*/
        //invierto el orden de como estaba antes para que no me genere problemas porque en el login siempre el token esta vacio o nulo
        if (tokenJWT != null) {
            //2-Tengo que validar para ver si ese token que me envian es correcto o son solo letras
            //Solo voy a activar el filtro si viene algun token en el header
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = usuarioRepository.findByCorreoElectronico(subject);
            //El token ya estaria valido si subject != null
            //entonces fuerzo un inicio de sesion en mi sistema
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());//Forzamos un inicio de sesion
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);//Le mandamos el request y el response al siguiente filtro.
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
