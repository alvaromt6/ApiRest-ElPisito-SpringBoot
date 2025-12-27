package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class RecursoYaExistenteException extends RuntimeException {
	//ERROR DE RECURSO
	//Recurso ya existente: Por ejemplo intentar crear un usuario con un email o un nombre ya utilizado (unique)
	@Serial
	private static final long serialVersionUID = 2222548612657410285L;

	public RecursoYaExistenteException(String msg) {
		super(msg);
	}
	
	
	

}
