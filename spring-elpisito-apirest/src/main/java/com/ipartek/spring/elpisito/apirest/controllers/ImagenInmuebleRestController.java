package com.ipartek.spring.elpisito.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.spring.elpisito.apirest.models.entities.ImagenInmueble;
import com.ipartek.spring.elpisito.apirest.models.services.ImagenInmuebleServiceImpl;

@RestController
@RequestMapping("/api")
public class ImagenInmuebleRestController {
	
	@Autowired
	private ImagenInmuebleServiceImpl imagenInmuebleService;
	
	@GetMapping("/imagenes-inmueble")
	public ResponseEntity<List<ImagenInmueble>> findAll(){
		return ResponseEntity.ok(imagenInmuebleService.findAll());//200
	}
	
	@GetMapping("/imagenes-inmueble-activas")
	public ResponseEntity<List<ImagenInmueble>> findAllActivos(){
		return ResponseEntity.ok(imagenInmuebleService.findAllActivo());//200
	}
	
	@GetMapping("/imagen-inmueble/{id}")
	public ResponseEntity<ImagenInmueble> findById(@PathVariable Long id){
		return ResponseEntity.ok(imagenInmuebleService.findById(id));//200
	}
	
	@PostMapping("/imagen-inmueble")
	public ResponseEntity<ImagenInmueble> create(@RequestBody ImagenInmueble imagenInmueble){
		return ResponseEntity.status(HttpStatus.CREATED).body(imagenInmuebleService.save(imagenInmueble));//201
	}
	
	@PutMapping("/imagen-inmueble")
	public ResponseEntity<ImagenInmueble> update(@RequestBody ImagenInmueble imagenInmueble){
		return ResponseEntity.ok(imagenInmuebleService.save(imagenInmueble));//200
	}
	
	@DeleteMapping("/imagen-inmueble/{id}")
	public void deleteById(@PathVariable Long id){
		imagenInmuebleService.deleteById(id);
	}
}