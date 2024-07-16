package com.alejobeliz.proyectos.forohub.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {

    public ValidacionDeIntegridad(String mensaje) {
        super(mensaje);
    }
}

