package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.InmobiliariaDAO;
import com.ipartek.spring.elpisito.apirest.models.dao.InmuebleDAO;
import com.ipartek.spring.elpisito.apirest.models.dao.OperacionDAO;
import com.ipartek.spring.elpisito.apirest.models.dao.PoblacionDAO;
import com.ipartek.spring.elpisito.apirest.models.dao.TipoDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.Inmobiliaria;
import com.ipartek.spring.elpisito.apirest.models.entities.Inmueble;
import com.ipartek.spring.elpisito.apirest.models.entities.Operacion;
import com.ipartek.spring.elpisito.apirest.models.entities.Poblacion;
import com.ipartek.spring.elpisito.apirest.models.entities.Tipo;

@Service
public class InmuebleServiceImpl implements GeneralService<Inmueble> {
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	@Autowired
	private TipoDAO tipoDAO;
	
	@Autowired
	private PoblacionDAO poblacionDAO;
	
	@Autowired
	private OperacionDAO operacionDAO;
	
	@Autowired
	private InmobiliariaDAO inmobiliariaDAO;

	@Override
	public List<Inmueble> findAll() {
		return inmuebleDAO.findAll();
	}

	@Override
	public List<Inmueble> findAllActivo() {
		return inmuebleDAO.findByActivo(1);
	}

	@Override
	public Inmueble save(Inmueble i) {
		return inmuebleDAO.save(i);
	}

	@Override
	public Inmueble findById(Long id) {
		return inmuebleDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("El inmueble con id: " + id + " no existe en la BBDD"));
	}

	public List<Inmueble>findAllPortada(){
		return inmuebleDAO.findByActivoAndPortada(1, 1);
	}

	public List<Inmueble> finder(Long idTipo, Long idPoblacion, Long idOperacion){
		Tipo tipo = tipoDAO.findById(idTipo).orElseThrow(()-> new RecursoNoEncontradoException("El tipo con id: " + idTipo + " que estás intentado utilizar en el finder no existe en la BBDD"));
		Poblacion poblacion = poblacionDAO.findById(idPoblacion).orElseThrow(()-> new RecursoNoEncontradoException("La población con id: " + idPoblacion + " que estás intentado utilizar en el finder no existe en la BBDD"));
		Operacion  operacion = operacionDAO.findById(idOperacion).orElseThrow(()-> new RecursoNoEncontradoException("La operación con id: " + idOperacion + " que estás intentado utilizar en el finder no existe en la BBDD"));
				
		return inmuebleDAO.findByTipoAndPoblacionAndOperacionAndActivo(tipo, poblacion, operacion, 1);
	}
	
	public List<Inmueble> findAllInmueblesInmobiliaria(Long idInmobiliaria) {
		
		Inmobiliaria inmobiliaria = inmobiliariaDAO.findById(idInmobiliaria).orElseThrow(()-> new RecursoNoEncontradoException("El inmobiliairia con id: " + idInmobiliaria + " que estás intentado utilizar en el finder no existe en la BBDD"));

		
		return inmuebleDAO.findByInmobiliariaAndActivo(inmobiliaria,1);
	}
	
	
	
}
