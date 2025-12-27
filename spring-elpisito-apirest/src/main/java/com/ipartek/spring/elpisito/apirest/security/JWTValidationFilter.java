package com.ipartek.spring.elpisito.apirest.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTValidationFilter extends OncePerRequestFilter{
	
	//Por aqui pasan toas las peticiones
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private JWTUserDetailsService jwtUserDetailsService;
	
	public static final String AUTHORIZATION_HEADER = "Authorization"; // Usar siempre "Authorization"
	public static final String AUTHORIZATION_HEADER_BEARER = "Bearer "; // Importante dejar un espacio al final
	
	@Override
	protected void doFilterInternal(
	        HttpServletRequest request, 
	        HttpServletResponse response, 
	        FilterChain filterChain)
	        throws ServletException, IOException {

	    String requestTokenHeader = null;
	    String username = null;
	    String jwt = null;

	    try {
	        // Obtener el token del encabezado Authorization
	        requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

	        // Verificar si el token no es null y tiene el prefijo "Bearer "
	        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
	            // Si no es un token válido, simplemente pasa al siguiente filtro
	            filterChain.doFilter(request, response);
	            return;
	        }

	        // Extraer el JWT eliminando el prefijo "Bearer "
	        jwt = requestTokenHeader.substring(7);

	        // Obtener el nombre de usuario del token (puede lanzar una excepción si el token está caducado)
	        username = jwtService.getTokenUsername(jwt);

	        // Si el usuario es válido y no está autenticado, proceder con la autenticación
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            // Cargar los detalles del usuario desde la base de datos o el servicio de usuarios
	            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

	            // Verificar si el token es válido
	            if (jwtService.isTokenValid(jwt, userDetails)) {
	                // Crear un objeto de autenticación con los detalles del usuario
	                UsernamePasswordAuthenticationToken authToken = 
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	                // Establecer el token de autenticación en el contexto de seguridad
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }

	        // Continuar con la cadena de filtros
	        filterChain.doFilter(request, response);

	    } catch (JwtException e) {
	        // En caso de que el token sea inválido (expirado, mal formado, etc.), no lanzar excepción
	        // Spring Security gestionará automáticamente el error y devolverá un 401
	        filterChain.doFilter(request, response);
	        return;
	    }
	}
}
