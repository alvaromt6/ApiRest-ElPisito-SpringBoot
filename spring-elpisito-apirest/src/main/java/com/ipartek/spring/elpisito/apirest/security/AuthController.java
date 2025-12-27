package com.ipartek.spring.elpisito.apirest.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {
	
	private final JWTUserDetailsService jwtUserDetailsService;
	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> autenticacion(@RequestBody JWTRequest credenciales) {

	    	Map<String, Object> response = new HashMap<>();
	    	
	        // 1️ Autenticamos al usuario usando username y password
	        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                new UsernamePasswordAuthenticationToken(credenciales.getUsername(), credenciales.getPassword());
	        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

	        // 2️ Cargamos los detalles del usuario
	       UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(credenciales.getUsername());

	        // 3️ Generamos el token 
	         String token = jwtService.getToken(userDetails);
	         
	         response.put("mensaje","Login realizado con exito");
	         response.put("status", "200");

	        // 4️ Devolvemos el token en la respuesta
	        return ResponseEntity
	        		.ok()
	        		.header("Authorization", "Bearer " + token) //Esta línea tiene que ser exacta
	        		.body(new JWTResponse(token, response));
	}


}
