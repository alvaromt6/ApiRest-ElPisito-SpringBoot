package com.ipartek.spring.elpisito.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.spring.elpisito.apirest.models.entities.Operacion;

@Repository
public interface OperacionDAO extends JpaRepository<Operacion, Long>{
	
	List<Operacion> findByActivo(Integer activo);

}
