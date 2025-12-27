package com.ipartek.spring.elpisito.apirest.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

	/*
	 * Su propósito principal es:
	 * Devolver una respuesta adecuada para indicar que, aunque estés autenticado, el recurso no es accesible
	 * (para tu ROL
	 * 403 Forbidden
	 * 403: se quien eres pero no puedes
	 * 
	 * Resquest -> JwtFilter -> Authentication OK -> Authorization -> Permisos insuficientes  -> AccessDeniedHandler -> 403 Forbidden*/
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Petición no autorizada. No tienes permisos para utilizar el recurso");
		//403 Forbidden
		
		
		
	}

}
