package com.ipartek.spring.elpisito.apirest.security;

import lombok.Data;

//Para SpringSecurity es mejor usar @Data porque puede usar los métodos (hashCode, equals, toString...) para algo interno
@Data //Crea los Getters, Setters, hashCode, toString...
public class JWTRequest {
	
	
	//Es un DTo en el que se van a mapear las peticiones
	//hechas desde cliente
	private String username;
	private String password;

}
