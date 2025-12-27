package com.ipartek.spring.elpisito.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.spring.elpisito.apirest.models.entities.Poblacion;

@Repository
public interface PoblacionDAO extends JpaRepository<Poblacion, Long>{
	
	List<Poblacion> findByActivo(Integer activo);

}
