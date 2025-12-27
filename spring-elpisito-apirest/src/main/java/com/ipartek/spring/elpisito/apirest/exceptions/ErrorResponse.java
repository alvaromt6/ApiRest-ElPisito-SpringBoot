package com.ipartek.spring.elpisito.apirest.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	
	//Esta clase es un DTO con el que pensamos pasar datos a cliente
	//A diferencia de una entidad un DTO no va a tener una tabla en la BBDD
	
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
	
	
	public ErrorResponse(int status, String error, String message, String path) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	
	

}
