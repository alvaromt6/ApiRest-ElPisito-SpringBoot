package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class TratamientoTipoMimeException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 4962926699265676638L;

	public TratamientoTipoMimeException(String msg) {
		super(msg);
	}
	
	

}
