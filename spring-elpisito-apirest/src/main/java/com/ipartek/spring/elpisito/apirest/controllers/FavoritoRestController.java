package com.ipartek.spring.elpisito.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.spring.elpisito.apirest.models.dto.FavoritoDTO;
import com.ipartek.spring.elpisito.apirest.models.dto.InmuebleDTO;
import com.ipartek.spring.elpisito.apirest.models.entities.Inmueble;
import com.ipartek.spring.elpisito.apirest.models.services.FavoritoService;

@RestController
@RequestMapping("/api")
public class FavoritoRestController {
	
	@Autowired
	private FavoritoService favoritoService;
	
	@PostMapping("/usuario-inmueble")
	public ResponseEntity<FavoritoDTO> addFavorito(
		@RequestParam("usuid") Long usuarioId, //El identificador "usuid" NO ES OBLIGATORIO.Si queremos que coincida el nombre de la variable
												//con el párametro no haria falta  ---> @RequestParam("")
		@RequestParam("inmid") Long inmuebleId
		) {
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(favoritoService.addFavorito(usuarioId, inmuebleId));//201
	
	}
	
	@DeleteMapping("/usuario-inmueble/{usuarioId}/{inmuebleId}")
	public ResponseEntity<String> deleteFavorito(
		@PathVariable Long usuarioId,
		@PathVariable Long inmuebleId
		){
		
		return  ResponseEntity.status(HttpStatus.OK).body(favoritoService.deleteFavorito(usuarioId, inmuebleId));//20

	}
	
	@GetMapping("usuario-inmueble-completo/{usuarioId}")
	public ResponseEntity<List<Inmueble>> listarFavoritosCompleto(
			@PathVariable Long usuarioId
			){
		return  ResponseEntity.status(HttpStatus.OK).body(favoritoService.listarFavoritosCompleto(usuarioId));//20
	}
	
	@GetMapping("usuario-inmueble/{usuarioId}")
	public ResponseEntity<List<InmuebleDTO>> listarFavoritos(
			@PathVariable Long usuarioId
			){
		return  ResponseEntity.status(HttpStatus.OK).body(favoritoService.listarFavoritos(usuarioId));//20
	}
	
	

}
