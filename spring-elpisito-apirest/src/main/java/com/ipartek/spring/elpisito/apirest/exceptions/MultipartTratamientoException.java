package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class MultipartTratamientoException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = -931330830538980548L;

	public MultipartTratamientoException(String msg) {
		super(msg);
	}
	
	

}
