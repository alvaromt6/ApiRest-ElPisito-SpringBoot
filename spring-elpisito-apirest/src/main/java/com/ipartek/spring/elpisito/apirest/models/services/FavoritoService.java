package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.models.dao.InmuebleDAO;
import com.ipartek.spring.elpisito.apirest.models.dao.UsuarioDAO;
import com.ipartek.spring.elpisito.apirest.models.dto.FavoritoDTO;
import com.ipartek.spring.elpisito.apirest.models.dto.InmuebleDTO;
import com.ipartek.spring.elpisito.apirest.models.entities.Inmueble;
import com.ipartek.spring.elpisito.apirest.models.entities.Usuario;

@Service
public class FavoritoService {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	public FavoritoDTO addFavorito(Long usuarioId, Long inmuebleId) {
		
		Usuario usuario = usuarioDAO.findById(usuarioId).orElseThrow(()-> new RecursoNoEncontradoException("El usuario con id: " + usuarioId + " que estás intentado utilizar para añadir a favoritos no existe en la BBDD"));

		Inmueble inmueble = inmuebleDAO.findById(inmuebleId).orElseThrow(()-> new RecursoNoEncontradoException("El inmueble con id: " + inmuebleId + " que estás intentado utilizar para añadir a favoritos no existe en la BBDD"));

		//Añadimos el inmueble a la lista de favoritos de usuario
		usuario.getInmueblesFavoritos().add(inmueble);	
		
		//Guardamos para que hibernate realice el registro en la BBDD
		usuarioDAO.save(usuario);
		
		String nombreTipo = inmueble.getTipo().getNombre();
		String nombrePoblacion = inmueble.getPoblacion().getNombre();
		String nombreProvincia = inmueble.getPoblacion().getProvincia().getNombre();
		String nombreOperacion = inmueble.getOperacion().getNombre();
		Double precio = inmueble.getPrecio();
		String nombreInmobiliaria = inmueble.getInmobiliaria().getNombre();
		
		return new FavoritoDTO(inmuebleId, nombreTipo, nombrePoblacion, nombreProvincia, nombreOperacion, precio, nombreInmobiliaria);
		
	}
	
	public String deleteFavorito(Long usuarioId, Long inmuebleId) {
		
		Usuario usuario = usuarioDAO.findById(usuarioId).orElseThrow(()-> new RecursoNoEncontradoException("El usuario con id: " + usuarioId + " que estás intentado utilizar para eliminar un inmueble de favoritos no existe en la BBDD"));

		Inmueble inmueble = inmuebleDAO.findById(inmuebleId).orElseThrow(()-> new RecursoNoEncontradoException("El inmueble con id: " + inmuebleId + " que estás intentado eliminar de favoritos no existe en la BBDD"));

		//Eliminamos el inmueble a la lista de favoritos de usuario
		usuario.getInmueblesFavoritos().remove(inmueble);	
		
		//Guardamos para que hibernate realice el registro en la BBDD
		usuarioDAO.save(usuario);
		
		return "El inmueble ha sido eliminado se du lista de deseos ";
		
	}
	
	//A partir de un id de Usuario devuelve ls inmuebles favoritos con todos sus datos
	public List<Inmueble> listarFavoritosCompleto(Long usuarioId){
		Usuario usuario = usuarioDAO.findById(usuarioId).orElseThrow(()-> new RecursoNoEncontradoException("El usuario con id: " + usuarioId + " no existe en la BBDD"));

		return new ArrayList<>(usuario.getInmueblesFavoritos());
	} 

	//A partir de un id de Usuario devuelve los id de inmuebles favoritos
	
	public List<InmuebleDTO> listarFavoritos(Long usuarioId){
		Usuario usuario = usuarioDAO.findById(usuarioId).orElseThrow(()-> new RecursoNoEncontradoException("El usuario con id: " + usuarioId + " no existe en la BBDD"));

		return usuario.getInmueblesFavoritos().stream()
				.map(i -> new InmuebleDTO(i.getId()))
				.toList();
				
	} 


}































