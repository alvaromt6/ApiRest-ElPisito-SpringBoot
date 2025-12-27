package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.OperacionDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Operacion;

@Service
public class OperacionServiceImpl implements GeneralService<Operacion>{

	
	@Autowired
	private OperacionDAO operacionDAO;
	
	@Override
	public List<Operacion> findAll() {
		return operacionDAO.findAll();
	}

	@Override
	public List<Operacion> findAllActivo() {
		return operacionDAO.findByActivo(1);
	}

	@Override
	public Operacion save(Operacion o) {
		return operacionDAO.save(o);
	}

	@Override
	public Operacion findById(Long id) {
		return operacionDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La operación con id: " + id + " no existe en la BBDD"));
	}



}
