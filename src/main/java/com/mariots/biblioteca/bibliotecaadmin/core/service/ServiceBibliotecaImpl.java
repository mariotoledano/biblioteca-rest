package com.mariots.biblioteca.bibliotecaadmin.core.service;

import com.mariots.biblioteca.bibliotecaadmin.api.exceptions.RecursoNoEncontradoException;
import com.mariots.biblioteca.bibliotecaadmin.api.exceptions.RecursoYaVinculadoException;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.AutorDto;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.SupertemaDto;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.TemaDto;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.TextoDto;
import com.mariots.biblioteca.bibliotecaadmin.api.mapper.Mapper;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.objetosvinculados.TemaSupertema;
import com.mariots.biblioteca.bibliotecaadmin.persistence.entities.SupertemaEntity;
import com.mariots.biblioteca.bibliotecaadmin.persistence.entities.TemaEntity;
import com.mariots.biblioteca.bibliotecaadmin.persistence.repository.RepositoryBiblioteca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServiceBibliotecaImpl implements ServiceBiblioteca {
    @Autowired
    RepositoryBiblioteca repository;
   @Autowired
    Mapper mapper;

    //MÉTODOS GUARDAR
    @Override
    public AutorDto guardarAutor(AutorDto autorDto) {
        return mapper.toDto(repository.guardarAutor(mapper.toEntity(autorDto)));
    }

    @Override
    public TemaDto guardarTema(TemaDto temaDto) {
        return mapper.toDto(repository.guardarTema(mapper.toEntity(temaDto)));
    }

    @Override
    public SupertemaDto guardarSupertema(SupertemaDto supertemaDto) {
        return mapper.toDto(repository.guardarSupertema(mapper.toEntity(supertemaDto)));
    }

    @Override
    public TextoDto guardarTexto(TextoDto textoDto) {
        return mapper.toDto(repository.guardarTexto(mapper.toEntity(textoDto)));
    }

    //MÉTODOS RECUPERAR TODOS
    @Override
    public List<AutorDto> recuperarAutores() {
        return repository.recuperarAutores().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TemaDto> recuperarTemas() {
        return repository.recuperarTemas().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SupertemaDto> recuperarSupertemas() {
        return repository.recuperarSupertemas().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TextoDto> recupearTextos() {
        return repository.recupearTextos().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    //MÉTODOS RECUPERAR POR ID
    @Override
    public AutorDto recuperarAutorPorId(int idAutor) {
        return repository.recuperarAutorPorId(idAutor).map(mapper::toDto).orElseThrow(()->new RecursoNoEncontradoException("Id de Autor no encontrado"));
    }

    @Override
    public TemaDto recuperarTemaPorId(int idTema) {
        return repository.recuperarTemaPorId(idTema).map(mapper::toDto).orElseThrow(()->new RecursoNoEncontradoException("Id de Tema no encontrado"));
    }

    @Override
    public SupertemaDto recuperarSupertemaPorId(int idSupertema) {
        return repository.recuperarSupertemaPorId(idSupertema).map(mapper::toDto).orElseThrow(()->new RecursoNoEncontradoException("Id de Supertema no encontrado"));
    }

    @Override
    public TextoDto recuperarTextoPorId(int idTexto) {
        return repository.recuperarTextoPorId(idTexto).map(mapper::toDto).orElseThrow(()->new RecursoNoEncontradoException("Id de Texto no encontrado"));
    }

    @Override
    public AutorDto recuperarAutorPorNombre(String nombreAutor) {
        return repository.recuperarAutorPorNombre(nombreAutor).map(mapper::toDto).orElseThrow(()->new RecursoNoEncontradoException("Nombre de Autor no encontrado"));
    }

    @Override
    public TemaDto recuperarTemaPorNombre(String nombreTema) {
        return repository.recuperarTemaPorNombre(nombreTema).map(mapper::toDto).orElseThrow(()->new RecursoNoEncontradoException("Nombre de Tema no encontrado"));    }

    @Override
    public SupertemaDto recuperarSupertemaPorNombre(String nombreSupertema) {
        return repository.recuperarSupertemaPorNombre(nombreSupertema).map(mapper::toDto).orElseThrow(()->new RecursoNoEncontradoException("Nombre de Supertema no encontrado"));
    }

    @Override
    public TemaSupertema vincularTemaSupertema(int idTema, int idSupertema) {
        TemaDto temaDto = recuperarTemaPorId(idTema);
        SupertemaDto supertemaDto = recuperarSupertemaPorId(idSupertema);
//        Si no hay temas previamente vinculados añadimos el nuevo IdTema al SupertemaDto
        if(Objects.isNull(supertemaDto.getIdTemas())){
            List<Integer> listaIdsTemas= new ArrayList<>();
            listaIdsTemas.add(temaDto.getIdTema());
            supertemaDto.setIdTemas(listaIdsTemas);
            guardarSupertema(supertemaDto);
        } else{
            //Comprobamos si el Tema a vincular ya existe previamente en Superemas
            if(repository.recuperarSupertemaPorId(idSupertema).get()
                    .getTemas()
                    .stream()
                    .anyMatch((tema)->tema==repository.recuperarTemaPorId(idTema).get())){
                throw new RecursoYaVinculadoException();
            } else{
                List<Integer> listaIdsTemas= supertemaDto.getIdTemas();
                listaIdsTemas.add(temaDto.getIdTema());
                supertemaDto.setIdTemas(listaIdsTemas);
                guardarSupertema(supertemaDto);
            }
        }
        TemaDto temaDespues = recuperarTemaPorId(idTema);
        SupertemaDto supertemaDespues =recuperarSupertemaPorId(idSupertema);
        return new TemaSupertema(temaDespues,supertemaDespues);
    }
}