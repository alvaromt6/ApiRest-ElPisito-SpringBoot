package com.ipartek.spring.elpisito.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.spring.elpisito.apirest.models.entities.Inmobiliaria;
import com.ipartek.spring.elpisito.apirest.models.entities.Inmueble;
import com.ipartek.spring.elpisito.apirest.models.entities.Tipo;
import com.ipartek.spring.elpisito.apirest.models.entities.Operacion;
import com.ipartek.spring.elpisito.apirest.models.entities.Poblacion;



@Repository
public interface InmuebleDAO extends JpaRepository<Inmueble, Long> {
	
	List<Inmueble> findByActivo(Integer activo);
	List<Inmueble> findByActivoAndPortada(Integer activo, Integer portada);
	List<Inmueble> findByTipoAndPoblacionAndOperacionAndActivo(Tipo tipo, Poblacion poblacion, Operacion operacion, Integer activo);
	List<Inmueble> findByInmobiliariaAndActivo(Inmobiliaria inmobiliaria, Integer activo);

}
