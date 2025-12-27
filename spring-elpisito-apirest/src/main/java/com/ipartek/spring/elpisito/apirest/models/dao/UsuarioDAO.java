package com.ipartek.spring.elpisito.apirest.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ipartek.spring.elpisito.apirest.models.entities.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario,Long> {
	
	//https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
	//Cuando necesitemos que hibernate implemente métodos que no están en el CrudRepository o en JpaRepository
	//podemos utilizar DERIVED QUERY METHODS
	//El secreto está del NOMBRE DEL MÉTODO
	List<Usuario> findByActivo(Integer activo);
	
	Optional<Usuario> findOneByNombre(String username);

}
