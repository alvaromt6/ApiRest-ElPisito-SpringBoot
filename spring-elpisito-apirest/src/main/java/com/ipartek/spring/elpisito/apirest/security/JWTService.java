package com.ipartek.spring.elpisito.apirest.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.models.dao.UsuarioDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	/*
	 * En esta clase vamos a definir (parametrizar) varios datos
	 * como, por ejemplo: el tiempo de validez del TOKEN, el nombre secreto, crear un token, verificar si es válido
	 */
	
	//en variables finale se usa las mayúsculas separadas por _
	public static final long JWT_TOKEN_VALIDITY = 1200; //segundos (20 minutos) "Periodo de validez del token
	public static final String JWT_SECRET = "SLAPEICJDUQYNVÑPAKTÑSJEIDMGJSATQHJKÑÑEOFP"; //IMPORTANTE QUE SEA RARA Y LARGA 40 LETRAS
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	/////////////////////////////////////////
	///MÉTODOS PARA COMPROBAR Y SACAR INFORMACIÓN DEL TOKEN
	/////////////////////////////////////////
	
	///Este método devolverá los claims del token 
	///Recibiremos una cosa parecida a esta (que es un token):
	///eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
	/// header.payload.signature
	
	//CLaims Es un objeto que contiene informacion que no ah llegado del token suele ser informacion que tu has metido dentro del payload del token
	private Claims getAllClaimsFromToken(String token) {
		//Lo primero que vamos a hacer es con JWT_SECRET conseguir una "key"
		Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
		
		//Vamos a realizar el proceso inverso a la creación de un token
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token) //parseClaimsJws!!!NOO parseClaimsJwt
			.getBody();
	}


	///Este método recibe los claims de un objeto Claims y devuelve objetos del tipo("String", "Double"...)
	/// <T> puede ser cualquier tipo 
	///La función claimsResolver, recibe claims y devuelve T " Function<Claims, T> claimsResolver){"
	public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver){
		
		Claims claims = getAllClaimsFromToken(token);
		
		return claimsResolver.apply(claims);
		
	}
	
	///Este método devuelve la fecha de expiración de token
	/// Este método devuelve la fecha de expiración del token
	private Date getTokenExpirationDate(String token) {

		///return getClaimsFromToken(token, c-> c.getExpiration
	    return getClaimsFromToken(token, Claims::getExpiration);
	}

	
	//Este método comprueba si en token está expirado (Devolvemos true or false
	private boolean isTokenExpirated(String token) {
		Date expirationDate = getTokenExpirationDate(token);
		//Si la fecha de expiración es anterior es true, es decir esta exìrado.
		return expirationDate.before(new Date());
	}
	
	//Este método consigue el nombre de usuario del claim del token
	//(Se supone que en el proceso de creación del token lo rellenamos)
	
	public String getTokenUsername(String token) {
		///return getClaimsFromToken(token, c-> c.getSubject
		return getClaimsFromToken(token, Claims::getSubject);
	}
	
	//Este método comprueba si un token es válido: pueden haber tokens no caducado pero no es válido
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String userNameFromUserDetail = userDetails.getUsername(); //Este es el nombre de usuario de la BBDD
		String userNameFromJWT = getTokenUsername(token); //Este es el nombre de usuario del token
		
		return userNameFromUserDetail.equals(userNameFromJWT) && !isTokenExpirated(token);
	}
	
	
	///	/////////////////////////////////////////
	///MÉTODOS PARA CREAR UN TOKEN
	/////////////////////////////////////////
	///
	
	//Este método genera, construye "internamente" el token...
	//El String que devuelve ES EL TOKEN!!!!
	
	private String generateToken(Map<String, Object> claims, String subject) {
		Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
		
		return Jwts
		.builder() //el builder es el creador del token
		.setClaims(claims) //claims es un Map con todo tipo de datos que seteamos dentro del token ( no enviar info confidencial/sensible)
		.setSubject(subject) //nombre del usuario
		.setIssuedAt(new Date(System.currentTimeMillis())) // la fecha actual en milisegundos (AHORA) 
		.setExpiration(new Date(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000) ))//Momento en el que token expira, 20 minutos despues de inicio sesión
		.signWith(key) // Firmamos el token con nuestra clave
		.compact(); //convierte finalmente el objeto Jwts.builder en String
				
	}

	///Este método "pasa" el token generado allá donde nos haga falta...
	///Recordamos que el objeto UserDetails contiene la info del usuario (nombre, password, rol...)
	public String getToken(UserDetails userDetails) {
		Usuario usuario = usuarioDAO.findOneByNombre(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
		
		//Es aquí donde podríamos incluir dentro del token datos en el objeto Claims (realmente los datos que necesitemos pasar)
		//SIEMPRE QUE NO SEAN DATOS CONFIDENCIALES/SENSIBLES
		Map<String, Object> claims = new HashMap<>();//LinkedHashMap para que los datos esten en orden
		//userDetails.getAuthorities() es un List de String con todos los roles
		claims.put("ROL", userDetails.getAuthorities().toString()); //"["ROLE_USER"]"
		claims.put("USUARIO", usuario.getNombre());
		//claims.put("Felicitación Navidad", "Feliz Navidad!!!");
		claims.put("ID", usuario.getId());
		
		//Retornamos el token
		return generateToken(claims, usuario.getNombre());
		
	}
}

























