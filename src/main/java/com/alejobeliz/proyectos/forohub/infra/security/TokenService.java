package com.alejobeliz.proyectos.forohub.infra.security;

import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Servicio para generar y verificar tokens JWT.
 * Maneja la creación de tokens JWT y la validación de tokens existentes.
 */
@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String apiSecret;

    @Value("${jwt.expiration}")
    private int expiracionEnHoras;

    /**
     * Genera un token JWT para el usuario especificado.
     *
     * @param usuario El usuario para quien se genera el token.
     * @return El token JWT generado.
     * @throws RuntimeException Si ocurre un error al crear el token.
     */
    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Foro.Hub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar token jwt", exception);
        }

    }

    /**
     * Obtiene el sujeto del token JWT.
     *
     * @param tokenJWT El token JWT del cual se extrae el sujeto.
     * @return El sujeto del token JWT.
     * @throws RuntimeException Si el token es inválido o ha expirado.
     */
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(apiSecret);
            return JWT.require(algoritmo)
                    .withIssuer("Foro.Hub")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido o expirado!");
        }
    }

    /**
     * Genera la fecha de expiración del token JWT.
     *
     * @return La fecha de expiración del token JWT.
     */
    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(expiracionEnHoras).toInstant(ZoneOffset.of("-03:00"));
    }
}
