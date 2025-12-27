package com.ipartek.spring.elpisito.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.spring.elpisito.apirest.models.entities.Provincia;

@Repository
public interface ProvinciaDAO extends JpaRepository<Provincia, Long> {
	
	List<Provincia> findByActivo(Integer activo);

}
