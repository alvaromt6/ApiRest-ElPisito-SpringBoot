package com.ipartek.spring.elpisito.apirest.exceptions;

import java.io.Serial;

public class AutenticacionException  extends RuntimeException{

	@Serial
	private static final long serialVersionUID = -6618213959357055284L;

	public AutenticacionException(String msg) {
		super(msg);
	}
	
	
	

}
