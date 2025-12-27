package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class FormatoNoSoportadoException extends RuntimeException{

	//ERROR DE FORMATO
	//Formato de contenido no soportado: por ejemplo enviar a local storage un pdf cuando solo este permitido jpg
	@Serial
	private static final long serialVersionUID = 2947862331403197786L;

	public FormatoNoSoportadoException(String msg) {
		super(msg);
	}
	
	

}
