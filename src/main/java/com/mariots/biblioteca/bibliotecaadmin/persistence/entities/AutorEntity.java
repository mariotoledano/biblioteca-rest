package com.mariots.biblioteca.bibliotecaadmin.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name="autores")
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idAutor;
    @NotNull
    @Column(name="autor")
    private String nombreAutor;
    @Column(name="fecha_autor")
    private String fechaAutor;
    @Column(name="descripcion_breve")
    private String descripcionBreve;
    @Column(name="descripcion_larga")
    private String descripcionLarga;

    //JOIN TABLE
    @OneToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH}, mappedBy = "autor")
    private List<TextoEntity> textos;

    }