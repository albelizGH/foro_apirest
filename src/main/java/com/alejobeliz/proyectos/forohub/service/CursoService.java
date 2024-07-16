package com.alejobeliz.proyectos.forohub.service;

import com.alejobeliz.proyectos.forohub.domain.curso.ActualizarCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.curso.Curso;
import com.alejobeliz.proyectos.forohub.domain.curso.RegistroCursoDTO;
import com.alejobeliz.proyectos.forohub.domain.curso.RespuestaCursoDTO;
import com.alejobeliz.proyectos.forohub.infra.errores.ValidacionDeIntegridad;
import com.alejobeliz.proyectos.forohub.repository.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public RespuestaCursoDTO registrarCurso(RegistroCursoDTO datos) {
        Optional<Curso> cursoBuscado=cursoRepository.findByNombreAndCategoria(datos.nombre(),datos.categoria());
        if(cursoBuscado.isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un curso con ese nombre en esa categoria");
        }
        Curso nuevoCurso = new Curso(datos);
        cursoRepository.save(nuevoCurso);
        RespuestaCursoDTO response = new RespuestaCursoDTO(nuevoCurso);
        return response;
    }

    @Transactional
    public Page<RespuestaCursoDTO> listarCursos(Pageable paginacion) {
        return cursoRepository.findAll(paginacion).map(RespuestaCursoDTO::new);
    }

    public RespuestaCursoDTO getCursoPorId(Long id) {
        Optional<Curso> curso  = cursoRepository.findById(id);
        if(!curso.isPresent()){
            throw new RuntimeException("Curso no encontrado");
        }
        return new RespuestaCursoDTO(curso.get());
    }

    @Transactional
    public RespuestaCursoDTO actualizarCurso(Long id, ActualizarCursoDTO datos) {
        Optional<Curso> cursoBuscado = cursoRepository.findById(id);
        if (!cursoBuscado.isPresent()) {
            throw new ValidacionDeIntegridad("No hay cursos que correspondan a ese Id");
        }
        Curso curso = cursoBuscado.get();
        curso.actualizarCurso(datos);
        cursoRepository.save(curso);
        return new RespuestaCursoDTO(curso);
    }
}
