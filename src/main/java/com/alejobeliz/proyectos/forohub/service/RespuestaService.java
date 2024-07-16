package com.alejobeliz.proyectos.forohub.service;


import com.alejobeliz.proyectos.forohub.domain.respuesta.ActualizarRespuestaDTO;
import com.alejobeliz.proyectos.forohub.domain.respuesta.RegistroRespuestaDTO;
import com.alejobeliz.proyectos.forohub.domain.respuesta.Respuesta;
import com.alejobeliz.proyectos.forohub.domain.respuesta.RespuestaRespuestaDTO;
import com.alejobeliz.proyectos.forohub.domain.topico.Topico;
import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import com.alejobeliz.proyectos.forohub.infra.errores.ValidacionDeIntegridad;
import com.alejobeliz.proyectos.forohub.repository.RespuestaRepository;
import com.alejobeliz.proyectos.forohub.repository.TopicoRepository;
import com.alejobeliz.proyectos.forohub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RespuestaService {

    private TopicoRepository topicoRepository;
    private UsuarioRepository usuarioRepository;
    private RespuestaRepository respuestaRepository;

    @Autowired
    public RespuestaService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, RespuestaRepository respuestaRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @Transactional
    public RespuestaRespuestaDTO registrarRespuesta(RegistroRespuestaDTO datos) {
        //Primero busco el autor y el topico para ver si existen
        Topico topico = topicoRepository.findById(datos.topico_id()).orElseThrow(() -> new ValidacionDeIntegridad("No hay ningun tópico con ese id"));
        Usuario autor = usuarioRepository.findById(datos.autor_id()).orElseThrow(() -> new ValidacionDeIntegridad("No hay ningun usuario con ese id"));

        Respuesta respuesta = new Respuesta(null, datos.mensaje(), topico, LocalDateTime.now(), autor);

        //Tengo que agregar la respuesta a la lista del topico
        topico.agregarRespuesta(respuesta);

        respuestaRepository.save(respuesta);

        return new RespuestaRespuestaDTO(respuesta);

    }

    public Page<RespuestaRespuestaDTO> listarRespuestas(Pageable respuestas) {
        Page page = respuestaRepository.findAll(respuestas).map(RespuestaRespuestaDTO::new);
        return page;
    }

    public RespuestaRespuestaDTO getRespuestaPorId(Long id) {
        Optional<Respuesta> respuesta = respuestaRepository.findById(id);
        if (!respuesta.isPresent()) {
            throw new ValidacionDeIntegridad("No hay respuestas que correspondan a ese Id");
        }
        return new RespuestaRespuestaDTO(respuesta.get());
    }

    @Transactional
    public RespuestaRespuestaDTO actualizarRespuesta(Long id, ActualizarRespuestaDTO datos) {
        Optional<Respuesta> respuestaBuscada = respuestaRepository.findById(id);
        if (!respuestaBuscada.isPresent()) {
            throw new ValidacionDeIntegridad("No hay respuestas que correspondan a ese Id");
        }
        Respuesta respuesta = respuestaBuscada.get();
        respuesta.actualizarRespuesta(datos);
        respuestaRepository.save(respuesta);
        return new RespuestaRespuestaDTO(respuesta);
    }


}
