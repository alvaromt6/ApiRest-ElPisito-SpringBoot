package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class BorradoArchivoException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 7030233147481737314L;

	public BorradoArchivoException(String msg) {
		super(msg);
	}
	
	

}
