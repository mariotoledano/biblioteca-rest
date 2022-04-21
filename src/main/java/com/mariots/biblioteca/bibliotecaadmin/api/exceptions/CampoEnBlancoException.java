package com.mariots.biblioteca.bibliotecaadmin.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CampoEnBlancoException extends RuntimeException{
    public CampoEnBlancoException(){
        super("Campo en blanco no valido, introduzca un valor");
    }
}