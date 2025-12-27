package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.TipoDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Tipo;

@Service
public class TipoServiceImpl implements GeneralService<Tipo>{

	@Autowired
	private TipoDAO tipoDAO;
	
	
	@Override
	public List<Tipo> findAll() {

		return tipoDAO.findAll();
	}

	@Override
	public List<Tipo> findAllActivo() {
		
		return tipoDAO.findByActivo(1);
	}

	@Override
	public Tipo save(Tipo t) {
		
		return tipoDAO.save(t);
	}

	@Override
	public Tipo findById(Long id) {
		
		return tipoDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("El tipo con id: " + id + " no existe en la BBDD"));
	}

}
