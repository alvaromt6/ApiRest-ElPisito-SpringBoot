package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.ProvinciaDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Provincia;

@Service
public class ProvinciaServiceImpl implements GeneralService<Provincia>{

	@Autowired
	private ProvinciaDAO provinciaDAO;
	
	
	@Override
	public List<Provincia> findAll() {
		
		return provinciaDAO.findAll();
	}

	@Override
	public List<Provincia> findAllActivo() {
		
		return provinciaDAO.findByActivo(1);
	}

	@Override
	public Provincia save(Provincia p) {

		return provinciaDAO.save(p);
	}

	@Override
	public Provincia findById(Long id) {
		return provinciaDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La provincia con id: " + id + " no existe en la BBDD"));
	}

}
