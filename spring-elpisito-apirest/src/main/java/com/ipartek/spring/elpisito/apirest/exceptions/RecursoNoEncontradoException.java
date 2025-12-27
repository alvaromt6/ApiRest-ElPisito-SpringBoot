package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class RecursoNoEncontradoException extends RuntimeException {
	//ERROR DE RECURSO
	//Recurso no encontrado: cuando no existe un ID o registro solicitado
	@Serial
	private static final long serialVersionUID = -6655576615122402116L;

	public RecursoNoEncontradoException(String msg) {
		super(msg);
	}

	

}
