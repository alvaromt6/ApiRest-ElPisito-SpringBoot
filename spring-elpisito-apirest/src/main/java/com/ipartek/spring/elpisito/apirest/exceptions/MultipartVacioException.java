package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class MultipartVacioException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -5455374202017661179L;
		
	public MultipartVacioException(String msg) {
		super(msg);
	}


}
