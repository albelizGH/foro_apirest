CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    topico_id BIGINT,
    fecha_creacion DATE NOT NULL,
    autor_id BIGINT,
    solucion VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (topico_id) REFERENCES topicos (id),
    FOREIGN KEY (autor_id) REFERENCES usuarios (id)
);
