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

/**
 * Filtro de seguridad que valida el token JWT en las solicitudes HTTP.
 * Extrae el token JWT del encabezado de autorización y autentica al usuario si el token es válido.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase {@link SecurityFilter}.
     *
     * @param tokenService El servicio de tokens que maneja la generación y verificación de tokens JWT.
     * @param usuarioRepository El repositorio de usuarios que proporciona acceso a los datos del usuario.
     */
    @Autowired
    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Filtra la solicitud HTTP para validar el token JWT y autenticar al usuario.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros para continuar con el procesamiento de la solicitud.
     * @throws ServletException Si ocurre un error durante el procesamiento del filtro.
     * @throws IOException Si ocurre un error de entrada/salida durante el procesamiento del filtro.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = usuarioRepository.findByCorreoElectronico(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());//Forzamos un inicio de sesion
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera el token JWT del encabezado de autorización.
     *
     * @param request La solicitud HTTP que contiene el encabezado de autorización.
     * @return El token JWT extraído del encabezado, o {@code null} si no se encuentra.
     */
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
