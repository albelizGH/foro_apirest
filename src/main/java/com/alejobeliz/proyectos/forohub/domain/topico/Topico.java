package com.alejobeliz.proyectos.forohub.domain.topico;


import com.alejobeliz.proyectos.forohub.domain.curso.Curso;
import com.alejobeliz.proyectos.forohub.domain.respuesta.Respuesta;
import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_creacion") // Aquí ajusta el nombre de la columna según la base de datos
    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Estado status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    public Topico(RegistroTopicoDTO datos , Usuario datosUsuario, Curso curso){
        this.id=null;
        this.titulo=datos.titulo();
        this.mensaje=datos.mensaje();
        this.fechaCreacion=LocalDate.now();
        this.status=Estado.ACTIVO;
        this.autor=datosUsuario;
        this.curso=curso;
    }

    public void actualizarDatos(ActualizacionTopicoDTO datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
    }

    public void agregarRespuesta(Respuesta respuesta){
        respuestas.add(respuesta);
    }


}
//}
