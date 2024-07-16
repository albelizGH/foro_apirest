package com.alejobeliz.proyectos.forohub.domain.topico.validaciones;


import com.alejobeliz.proyectos.forohub.domain.topico.RegistroTopicoDTO;
import com.alejobeliz.proyectos.forohub.domain.topico.Topico;
import com.alejobeliz.proyectos.forohub.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TopicosDuplicados implements Validaciones{

    private TopicoRepository repository;

    @Autowired
    public TopicosDuplicados(TopicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(RegistroTopicoDTO datos) {
        Optional<Topico> topico =repository.findByTituloAndMensaje(datos.titulo(),datos.mensaje());
        if(topico.isPresent()){
            throw new ValidationException("Ya existe un mismo tópico con ese título y mensaje");
        }
    }
}
