package com.ipartek.spring.elpisito.apirest.security;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTResponse {
	///Esta clase es la encargada de devolver los datos a cliente.
	///Los objetos de la clase JWTResponse son en realidad DTO

	private String jwt;
	private Map<String, Object> mensajes;
}
