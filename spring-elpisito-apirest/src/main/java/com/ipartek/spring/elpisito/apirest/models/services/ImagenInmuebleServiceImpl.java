package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.ImagenInmuebleDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.ImagenInmueble;

@Service
public class ImagenInmuebleServiceImpl implements GeneralService<ImagenInmueble> {

	@Autowired
	private ImagenInmuebleDAO imagenInmuebleDAO;
	
	@Override
	public List<ImagenInmueble> findAll() {
		return imagenInmuebleDAO.findAll();
	}

	@Override
	public List<ImagenInmueble> findAllActivo() {
		return imagenInmuebleDAO.findByActivo(1);
	}

	@Override
	public ImagenInmueble save(ImagenInmueble t) {
		return imagenInmuebleDAO.save(t);
	}

	@Override
	public ImagenInmueble findById(Long id) {
		return imagenInmuebleDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La imagen con id: " + id + " no existe en la BBDD"));

	}
	
	public void deleteById(Long id) {
		imagenInmuebleDAO.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La imagen con id: " + id + " no existe en la BBDD no es posible eliminarla"));

		imagenInmuebleDAO.deleteById(id);
		
		
	}
}
