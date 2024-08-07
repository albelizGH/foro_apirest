package com.alejobeliz.proyectos.forohub.controller;


import com.alejobeliz.proyectos.forohub.domain.usuario.DatosAutenticacionUsuarioDTO;
import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import com.alejobeliz.proyectos.forohub.infra.security.DatosJWTToken;
import com.alejobeliz.proyectos.forohub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar las operaciones de autenticación de usuarios.
 * Proporciona un endpoint para iniciar sesión y generar un token JWT.
 */
@RestController
@RequestMapping("/login")
@Tag(name = "Autentificación")
public class AutenticacionController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    /**
     * Constructor de la clase {@link AutenticacionController}.
     *
     * @param authenticationManager El administrador de autenticación que maneja la autenticación de usuarios.
     * @param tokenService El servicio de tokens que maneja la generación y verificación de tokens JWT.
     */
    @Autowired
    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Autentica al usuario con las credenciales proporcionadas y genera un token JWT.
     *
     * @param datosAutenticacionUsuario Los datos de autenticación del usuario (correo y contraseña).
     * @return Un {@link ResponseEntity} que contiene el token JWT en caso de autenticación exitosa.
     */
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.correo(), datosAutenticacionUsuario.contrasena());
        authenticationManager.authenticate(authenticationToken);//Aca espera el toquen que hay en mi metodo login
        Authentication usuarioAutenticado=authenticationManager.authenticate(authenticationToken);

        String JWtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal() );
        return ResponseEntity.ok(new DatosJWTToken(JWtoken));
    }
}
