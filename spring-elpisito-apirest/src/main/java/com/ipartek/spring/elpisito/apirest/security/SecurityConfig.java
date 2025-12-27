package com.ipartek.spring.elpisito.apirest.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	
	@Autowired 
	private JWTAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired 
	private JWTAccessDeniedHandler accessDeniedHandler;

	//Esta clase va a centralizar la configuración de SpringSecurity
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, JWTValidationFilter jwtValidationFilter)throws Exception {
		
		//En una API REST no existen las sesiones!!!
		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http
		.authorizeHttpRequests(auth -> {
			
			//Podemos hacer infinitas combinaciones utilizando el SELECTOR y el AUTORIZADOR
			//Podemos jugar con el SELECTOR : anyRequest(), requestMatchers()...
			//Podemos jugar con el AUTORIZADOR: permitAll(), authenticated(), hasRole(), hasAnyRole()
			//Haciendo combinaciones entre un SELECTOR y un AUTORIZADOR crearemos autorizaciones
			//a la carta
			//Es obligatorio para Spring Security que en la BBDD esté anotado como "ROLE_LOQUESEA" y aquí "LOQUESEA"
			
			/*auth.requestMatchers("/api/provincias").hasRole("USER");
			auth.requestMatchers("/api/tipos").hasAnyRole("ADMIN_PLUS","ADMIN");
			auth.requestMatchers("/api/**").authenticated();*/   
			
			/*
			* = 1 segmento
					** = todos los segmentos siguientes → debe ir al final.
			*/
			
			auth.requestMatchers(HttpMethod.POST,"/api/inmueble")
			.hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.PUT,"/api/inmueble").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/inmuebles").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/inmuebles-inmobiliaria/**").hasAnyRole("SUPER_ADMIN","ADMIN");
			//auth.requestMatchers("/api/inmuebles-portada").authenticated();
			
			auth.requestMatchers(HttpMethod.POST,"/api/poblacion").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.PUT,"/api/poblacion").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/poblaciones").hasAnyRole("SUPER_ADMIN","ADMIN");
			//auth.requestMatchers(HttpMethod.GET,"/api/poblaciones-activas").hasAnyRole("SUPER_ADMIN","ADMIN","USER");
	
			
			auth.requestMatchers(HttpMethod.POST,"/api/provincia").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.PUT,"/api/provincia").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/provincias").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/provincias-activas").hasAnyRole("SUPER_ADMIN","ADMIN");

			
			auth.requestMatchers(HttpMethod.POST,"/api/tipo").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.PUT,"/api/tipo").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/tipos").hasAnyRole("SUPER_ADMIN","ADMIN");
			//auth.requestMatchers(HttpMethod.GET,"/api/tipos-activos").hasAnyRole("SUPER_ADMIN","ADMIN","USER");

			
			auth.requestMatchers(HttpMethod.POST,"/api/operacion").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.PUT,"/api/operacion").hasAnyRole("SUPER_ADMIN","ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/operaciones").hasAnyRole("SUPER_ADMIN","ADMIN");
			//auth.requestMatchers(HttpMethod.GET,"/api/operaciones-activas").hasAnyRole("SUPER_ADMIN","ADMIN","USER");
			
			
			//el POST no existe porque no podemos restringir que una persona se de de alta...
			auth.requestMatchers(HttpMethod.PUT,"/api/usuario").hasRole("SUPER_ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/usuario/**").hasRole("SUPER_ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/usuarios").hasRole("SUPER_ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/api/usuarios-activos").hasRole("SUPER_ADMIN");
			
			//Favoritos
			auth.requestMatchers(HttpMethod.POST,"/api/usuario-inmueble").hasAnyRole("SUPER_ADMIN","ADMIN","USER");
			auth.requestMatchers(HttpMethod.DELETE,"/api/usuario-inmueble/**").hasAnyRole("SUPER_ADMIN","ADMIN","USER");
	
		
			
			auth.anyRequest().permitAll(); // Todo lo no restringido se permite
			//Esta linea de código se suele poner siempre al final porque 
			//significa que todo lo que no hayamos restringido "arriba" está permitido
			//Por lo tanto el orden en el que anotemos las "restricciones de acceso" es
			//importantísimo
			
		});
		
		//Si no lo expresamos aquí SecurityConfig no gestiona los errores por defecto
		http.exceptionHandling(ex -> ex
				.authenticationEntryPoint(authenticationEntryPoint)//401
				.accessDeniedHandler(accessDeniedHandler) //403
				
				);
		
		
		http.addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter.class);
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		//El CRSF del inglés (Cross Site Request Forgery) o (Falsificación en peticiones)
		//de sitios cruzados es un tipo de EXPLOIT malicioso de un sitio web, en el que comandos
		//no autorizados son transmitidos por un usuario (sin que él lo sepa) en el cual el sitio web confía
		//Desactivamos el csrf porque nuestra API usa tokens JWT
		http.csrf( csrf -> csrf.disable());
		
		
		return http.build();
	}
	
	///Una aplicación de Spring Security SOLO PUEDE TENER UN PASSWORD ENCODE!!!
	///y además es OBLIGATORIO que lo tenga.
	///Este password encoder debe estar anotado como @Bean está permanentemente activo en el contexto de Spring
	///y el método puede ser utilizado sin necesidad de hacer una instancia de su clase
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
		
		return configuration.getAuthenticationManager();
	}
	
	@Bean CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOrigins(List.of("*")); //Permitimos todos los orígenes
		config.setAllowedMethods(List.of("*")); //Permitimos todos los métodos de http: POST, GET, PUT, DELETE...
		config.setAllowedHeaders(List.of("*")); //Permitimos todos los headers (es muy importante porque en los headers llegan los tokens)
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
	/*
	@Bean CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOrigins(List.of("*")); //Permitimos todos los orígenes
		config.setAllowedMethods(List.of("GET","POST")); //Permitimos los métodos de http: POST, GET
		config.setAllowedHeaders(List.of("Authoritation","Cache-control","Content-Type")); //Permitimos los headers (es muy importante porque en los headers llegan los tokens)
		config.setAllowCredentials(true);  //Permitimos credenciales
		config.setExposedHeaders(List.of("Authoritation")); //Headers que no queremos que sean visibles //De esta manera podemos acceder a info del token , por ejemplo, desde un interceptor de Angular
		 
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	*/
}












