package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class AutorizacionException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 2962407819399376160L;

	public AutorizacionException(String msg) {
		super(msg);
	}
	
	

}
