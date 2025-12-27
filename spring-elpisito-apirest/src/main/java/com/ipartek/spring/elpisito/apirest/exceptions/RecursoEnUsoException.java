package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class RecursoEnUsoException extends RuntimeException {
	//ERROR DE RECURSO
	//Recurso en uso: No podemos eliminar un recurso de la BBDD porque tiene relaciones activas 
	//( no romper integridad referencial de la BBDD)
	@Serial
	private static final long serialVersionUID = -3697620440480710310L;

	public RecursoEnUsoException(String msg) {
		super(msg);
	}

	
	
}
