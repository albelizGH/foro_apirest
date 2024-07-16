package com.alejobeliz.proyectos.forohub.domain.respuesta;


import com.alejobeliz.proyectos.forohub.domain.topico.Topico;
import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name="Respuesta")
@Table(name = "respuestas")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    public void actualizarRespuesta(ActualizarRespuestaDTO datos) {
        if(datos.mensaje()!=null){
            this.mensaje=datos.mensaje();
        }
    }

}
