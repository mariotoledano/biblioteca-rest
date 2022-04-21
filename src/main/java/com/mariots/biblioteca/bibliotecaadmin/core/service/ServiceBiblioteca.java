package com.mariots.biblioteca.bibliotecaadmin.core.service;

import com.mariots.biblioteca.bibliotecaadmin.core.dtos.AutorDto;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.SupertemaDto;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.TemaDto;
import com.mariots.biblioteca.bibliotecaadmin.core.dtos.TextoDto;
import java.util.List;

public interface ServiceBiblioteca {
    public AutorDto guardarAutor(AutorDto autorDto);
    public TemaDto guardarTema(TemaDto temaDto);
    public SupertemaDto guardarSupertema(SupertemaDto supertemaDto);
    public TextoDto guardarTexto(TextoDto textoDto);
//    public TextoDto guardarNuevoTexto(TextoDto textoDto);

    public List<AutorDto> recuperarAutores();
    public List<TemaDto> recuperarTemas();
    public List<SupertemaDto> recuperarSupertemas();
    public List<TextoDto> recupearTextos();

    public AutorDto recuperarAutorPorId(int idAutor);
    public TemaDto recuperarTemaPorId(int idTema);
    public SupertemaDto recuperarSupertemaPorId(int idSupertema);
    public TextoDto recuperarTextoPorId(int idTexto);

    public AutorDto recuperarAutorPorNombre(String nombreAutor);
    public TemaDto recuperarTemaPorNombre(String nombreTema);
    public SupertemaDto recuperarSupertemaPorNombre(String nombreSupertema);
}