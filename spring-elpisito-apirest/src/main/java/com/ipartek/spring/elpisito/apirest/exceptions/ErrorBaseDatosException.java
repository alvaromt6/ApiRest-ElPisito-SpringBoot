package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class ErrorBaseDatosException extends RuntimeException {

	//ERROR DE SERVIDOR/BBDD
	//Fallo en la BBDD: Error al realizar una operación de lectura-escritura en la BBDD
	@Serial
	private static final long serialVersionUID = 1168386980860906858L;

	public ErrorBaseDatosException(String msg) {
		super(msg);
	}

	
}
