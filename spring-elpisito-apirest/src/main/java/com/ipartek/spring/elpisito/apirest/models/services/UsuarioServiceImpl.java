package com.ipartek.spring.elpisito.apirest.models.services;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.UsuarioDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Usuario;

@Service
public class UsuarioServiceImpl implements GeneralService<Usuario> {
	
	//Una de las principales caractrísticas de un @Service
	//es que sus atributos suelen ser (no siempre) DAOs (Repositorios)
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	//usuarioDAO automáticamente tiene todos los métodos IMPLEMENTADOS POR HIBERNATE de JpaRepository
	//¿Dónde los ha implementado?: la implementación la ha hecho hibernate en el CONSTEXTO DE SPRING.
	//Se ha creado en el contexto de Spring una clase implementadora de UsuarioDAO y en esa implementación
	//se ha puesto toda la lógica SQL sin que hayamos tocado una sola linea de código SQL
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Usuario> findAll() {
	
		return usuarioDAO.findAll();
	}
	
	

	@Override
	public List<Usuario> findAllActivo() {

		return usuarioDAO.findByActivo(1);
	}
	
	

	@Override
	public Usuario save(Usuario u) {
		//Este método recibe un Usuario.
		//Si el objeto que recibimos tiene id
		//hibernate considera que es un update
		//Si el Usuario que recibe el método no tiene id
		//hibernate considera que es un craate
		//Podemos concluir que el CrudRepository solo
		//tiene un método "save" para ambas taréas
		
		//¿Sería interesante añadir aquí el password hasheaado
		//antes de mandarlo a la BBDD?: SI	
		
		u.setPasswordOpen(u.getPassword()); //password abierta
		u.setPassword(passwordEncoder.encode(u.getPassword())); // password hashed
		
		//DataIntegrityViolationException (unique)	
		return usuarioDAO.save(u);
		
		
	}

	@Override
	public Usuario findById(Long id)  {
			
		//El método findById no devuelve nunca un error. Si no existe coincidencia devuelve null dentro de
		//un Optional
		return usuarioDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("El usuario con id: " + id + " no existe en la BBDD"));

	}
	
	

	/*@Override
	public void deleteById(Long id) {
		
		Optional<Usuario> resultadOptional = usuarioDAO.findById(id);
		
		if(resultadOptional.isPresent()) {
			usuarioDAO.deleteById(id);
		}else {
			throw new RuntimeException("usuario no encontrado!!!");
		}
	
		
		//usuarioDAO.deleteById(id); //Debería lanzar una DataAccessException
		
	}*/
	




}
