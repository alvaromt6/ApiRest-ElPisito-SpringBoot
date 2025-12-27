package com.ipartek.spring.elpisito.apirest.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint{
	/*
	 * Este servicio (anotado como @Component es muy importante
	 * ya que vamos a decir a SpringSecurity que vamos a tener
	 * un EndPoint de Autenticación. Vamos a obtener el TOKEN a través de una petición POST
	 * 
	 * El AuthenticationEntryPoint es un middleware de primer término
	 * que recibe el request (la petición de cliente) y "colapsa", "intercepta"
	 * todos los pasos posteriores en el caso de que se produzzca un IOException o un 
	 * ServletException
	 * 
	 * se usa para mejorar las solicutudes no autenticadas que intenten acceder a recursos protegidos
	 * 
	 * Spring Security lo invoca cuando:
	 * 1) Un usuario no autenticado intenta acceder a una URI que está protegida
	 * 2) Cuando no se ha proporcionado un token válido (En aplicaciones JWT)
	 * 
	 * 
	 * Su proposito principal es:
	 * Devolver una repuesta adecuada para indicar que el recurso necesita autenticación
	 * Por ejemplo: En aplicaciones web tradicionales redirigir al usuario a la página de login
	 * (no es nuestro caso porque nuestra aplicación es una API Rest y de ello se va a encargar Angular )
	 * En el caso de una API REST va a devolver un 401 junto con un mensaje que explique que se requiere autenticación...
	 * 
	 */
	
	

    /*
     * Este servicio (anotado con @Component) es muy importante porque le indica a
     * Spring Security cuál es el punto de entrada para manejar intentos de acceso
     * no autenticados.
     *
     * El AuthenticationEntryPoint actúa como un middleware de primer nivel que se 
     * ejecuta cuando un usuario NO autenticado intenta acceder a un recurso protegido.
     *
     * Spring Security lo invoca cuando:
     * 1) Un usuario no autenticado intenta acceder a una ruta protegida.
     * 2) No se proporciona un token válido (en aplicaciones basadas en JWT).
     *
     * Su propósito es devolver una respuesta adecuada indicando que el recurso
     * requiere autenticación.
     *
     * En aplicaciones web tradicionales podría redirigir al formulario de login.
     * En una API REST, lo correcto es devolver un código 401 junto con un mensaje.
     *
     * En nuestro caso, Angular se encargará de gestionar esta respuesta en el cliente.
     * 
     * 401: no se quién eres
     * Request -> JWTFilter -> Autenticado NO -> JWTAuthenticationEntryPoint -> 401 Unauthorized
     */

	@Override
	public void commence(
			HttpServletRequest request, 
			HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED,
				"Petición no autorizada. El usuario necesita autenticación. Es posible que hayas introducido tus credenciales incorréctamente. Inténtalo de nuevo"
				);
		//401 Unautorized
	}
	/*
	 * sin token 401
	 * token expirado 401
	 * 
	 * 
	 */

}
