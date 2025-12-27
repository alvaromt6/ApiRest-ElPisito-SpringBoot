package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.InmobiliariaDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Inmobiliaria;

@Service
public class InmobiliariaServiceImpl implements GeneralService<Inmobiliaria> {

	@Autowired
	private InmobiliariaDAO inmobiliariaDAO;
	
	@Override
	public List<Inmobiliaria> findAll() {
		return inmobiliariaDAO.findAll();
	}

	@Override
	public List<Inmobiliaria> findAllActivo() {
		return inmobiliariaDAO.findByActivo(1);
	}

	@Override
	public Inmobiliaria save(Inmobiliaria i) {
		return inmobiliariaDAO.save(i);
	}

	@Override
	public Inmobiliaria findById(Long id) {
		return inmobiliariaDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La inmobiliaria con id: " + id + " no existe en la BBDD"));

	}
	

}
