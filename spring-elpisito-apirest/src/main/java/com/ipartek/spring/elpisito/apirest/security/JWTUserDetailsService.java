package com.ipartek.spring.elpisito.apirest.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.models.dao.UsuarioDAO;

@Service
public class JWTUserDetailsService implements UserDetailsService{
	
	///Tenemos anotado como @Service un objeto de la clase (interface) UserDataServie (por polimorfismo)
	///Esto quiere decir que JWTUserDetailsService va a existir en el contexto de Spring (como Bean) y que, por lo tanto,
	///podremos inyectarlo allá donde lo necesitemos
	///Esto es lo único que necesita Spring Security para tener un USUARIO ACTIVO que se suele cargar generalmente desde una BBDD
	///ESTO ES MUY IMPORTANTE porque es asi como Spring conoce el nombre del USUARIO ACTIVO, conoce la contraseña, conoce el email...

	
	///El objeto username que recibimos como argumento es el que le llega de la request del controlador
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return usuarioDAO.findOneByNombre(username)
			.map(u -> {
				///Listado de SimpleGrantedAuthority
				///usar authorities como nombre del objeto ( es lo convencional)
				///Solo tenemos un rol 
			
				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(u.getRol()));
				
				///no confundir user con usuario, user es una clase de Spring Security
				///User implementa UserDetails (la funcion tiene que devolver UserDetails pero como User implementa UserDetails podemos devolver User)
				return new User(
                        u.getNombre(),
                        u.getPassword(),
                        authorities
                );
			}).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado en la BBDD en el proceso de logueo"));
		
	}
								
}

/*
 * EJEMPLO GUÍA PARA UTILIZAR EN LA BBDD EN EL CASO DE QUE DECIDAMOS TENER UNA TABLA DE ROLES
 * RELACIONADA CON LA TABLA DE USUARIOS (MUCHOS A MUCHOS)
 * Set<Rol> roles = u.getRoles();
 * List<GrantedAuthority> authorities = roles.stream()
 * 									.map(rol -> new SimpleGrantedAuthority(rol.getNAme()))
 * 								.toList();
 * 
 */
	

