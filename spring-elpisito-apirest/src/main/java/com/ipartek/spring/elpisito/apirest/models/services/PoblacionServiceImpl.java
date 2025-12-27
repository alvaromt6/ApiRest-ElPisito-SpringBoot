package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.PoblacionDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Poblacion;

@Service
public class PoblacionServiceImpl implements GeneralService<Poblacion>{

	@Autowired
	private PoblacionDAO poblacionDAO;
	
	@Override
	public List<Poblacion> findAll() {
		return poblacionDAO.findAll();
	}

	@Override
	public List<Poblacion> findAllActivo() {
		return poblacionDAO.findByActivo(1);
	}


	@Override
	public Poblacion save(Poblacion p) {
		return poblacionDAO.save(p);
	}

	@Override
	public Poblacion findById(Long id) {
		return poblacionDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La población con id: " + id + " no existe en la BBDD"));

	}

}
