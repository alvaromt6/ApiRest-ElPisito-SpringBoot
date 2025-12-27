package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class SubidaArchivoException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = -6943411959222650106L;

	public SubidaArchivoException(String msg) {
		super(msg);
	}
	
	

}
