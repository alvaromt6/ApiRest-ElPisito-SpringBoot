package com.ipartek.spring.elpisito.apirest.controllers;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.spring.elpisito.apirest.models.entities.Inmueble;
import com.ipartek.spring.elpisito.apirest.models.services.InmuebleServiceImpl;


@RestController
@RequestMapping("/api")
public class InmuebleRestController {
	
	@Autowired
	private InmuebleServiceImpl inmuebleService;
	
	@GetMapping("/inmuebles")
	public ResponseEntity<List<Inmueble>> findAll(){
		return ResponseEntity.ok(inmuebleService.findAll());//200
	}
	
	@GetMapping("/inmuebles-activos")
	public ResponseEntity<List<Inmueble>> findAllActivos(){
		return ResponseEntity.ok(inmuebleService.findAllActivo());//200
	}
	
	@GetMapping("/inmueble/{id}")
	public ResponseEntity<Inmueble> findById(
			@PathVariable Long id
			){
		return ResponseEntity.ok(inmuebleService.findById(id));//200
	}
	
	@PostMapping("/inmueble")
	public ResponseEntity<Inmueble> create(@RequestBody Inmueble inmueble){
		return ResponseEntity.status(HttpStatus.CREATED).body(inmuebleService.save(inmueble));//201
	}
	
	@PutMapping("/inmueble")
	public ResponseEntity<Inmueble> update(@RequestBody Inmueble inmueble){
		return ResponseEntity.ok(inmuebleService.save(inmueble));//200
	}
	
	@GetMapping("/inmuebles-portada")
	public ResponseEntity<List<Inmueble>> findAllPortada(){
		return ResponseEntity.ok(inmuebleService.findAllPortada()); //200
		
	}
	
	@GetMapping("/inmuebles/{idTipo}/{idPoblacion}/{idOperacion}")
	public ResponseEntity<List<Inmueble>> finder(
			@PathVariable Long idTipo,
			@PathVariable Long idPoblacion,
			@PathVariable Long idOperacion
			){
		return ResponseEntity.ok(inmuebleService.finder(idTipo,idPoblacion,idOperacion)); //200
		
	}
	
	@GetMapping("/inmuebles-inmobiliaria/{idInmobiliaria}")
	public ResponseEntity<List<Inmueble>> findAllInmueblesInmobiliaria(
			@PathVariable Long idInmobiliaria
			){
		return ResponseEntity.ok(inmuebleService.findAllInmueblesInmobiliaria(idInmobiliaria));
	
		
	}
	
}
