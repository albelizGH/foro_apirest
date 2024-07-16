package com.alejobeliz.proyectos.forohub.service;

import com.alejobeliz.proyectos.forohub.domain.curso.Curso;
import com.alejobeliz.proyectos.forohub.domain.topico.*;
import com.alejobeliz.proyectos.forohub.domain.topico.validaciones.Validaciones;
import com.alejobeliz.proyectos.forohub.domain.usuario.Usuario;
import com.alejobeliz.proyectos.forohub.infra.errores.ValidacionDeIntegridad;
import com.alejobeliz.proyectos.forohub.repository.CursoRepository;
import com.alejobeliz.proyectos.forohub.repository.TopicoRepository;
import com.alejobeliz.proyectos.forohub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    private TopicoRepository topicoRepository;
    private UsuarioRepository usuarioRepository;
    private CursoRepository cursoRepository;
    private List<Validaciones> validaciones;

    @Autowired
    public TopicoService(List<Validaciones> validaciones, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository curso) {
        this.validaciones = validaciones;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = curso;
    }

    @Transactional
    public RespuestTopicoDTO registrarTopico(RegistroTopicoDTO datos) {
        Optional<Usuario> usuario = usuarioRepository.findById(datos.autor_id());
        Optional<Curso> curso = cursoRepository.findById(datos.curso_id());

        if (!usuario.isPresent()) {
            throw new ValidationException("Usuario inexistente");
        }

        if (!curso.isPresent()) {
            throw new ValidationException("Curso inexistente");
        }

        var usuarioEncontrado = usuario.get();
        var cursoEncontrado = curso.get();

        validaciones.forEach(validacion -> validacion.validar(datos));
        Topico topico = new Topico(datos, usuarioEncontrado, cursoEncontrado);
        topicoRepository.save(topico);
        RespuestTopicoDTO response = new RespuestTopicoDTO(topico);
        return response;
    }

    public Page<RespuestaTopicoExtensaDTO> listaTopicos(Pageable topicos) {
        Page page = topicoRepository.findAll(topicos).map(RespuestaTopicoExtensaDTO::new);
        return page;
    }


    public RespuestaTopicoExtensaDTO getTopicoPorId(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (!topico.isPresent()) {
            throw new ValidacionDeIntegridad("No hay tópico que correspondan a ese Id");
        }
        return new RespuestaTopicoExtensaDTO (topico.get());
    }

    @Transactional
    public RespuestaTopicoExtensaDTO actualizarTopico(Long id, ActualizacionTopicoDTO datos) {
        Optional<Topico> topicoBuscado = topicoRepository.findById(id);
        if (!topicoBuscado.isPresent()) {
            throw new ValidacionDeIntegridad("No hay topicos que correspondan a ese Id");
        }
        Topico topico = topicoBuscado.get();
        topico.actualizarDatos(datos);
        topicoRepository.save(topico);
        return new RespuestaTopicoExtensaDTO(topico);
    }

    @Transactional
    public void eliminarTopico(Long id) {
        Optional<Topico> topicoBuscado = topicoRepository.findById(id);
        if (!topicoBuscado.isPresent()) {
            throw new ValidacionDeIntegridad("No hay topicos que correspondan a ese Id");
        }
        Topico topico = topicoBuscado.get();
        topicoRepository.delete(topico);
    }
}
