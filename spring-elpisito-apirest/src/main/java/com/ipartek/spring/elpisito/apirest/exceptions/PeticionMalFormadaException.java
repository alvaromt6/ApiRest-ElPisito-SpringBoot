package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class PeticionMalFormadaException extends RuntimeException{

	//ERROR DE VALIDACIÓN
	//Campos incorrectos o faltantes, cadenas mal formadas una URI
	@Serial
	private static final long serialVersionUID = 1669302263507389499L;

	public PeticionMalFormadaException(String msg) {
		super(msg);
	}
	
	

}
