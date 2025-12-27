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

import com.ipartek.spring.elpisito.apirest.models.entities.Poblacion;

import com.ipartek.spring.elpisito.apirest.models.services.PoblacionServiceImpl;

@RestController
@RequestMapping("/api")
public class PoblacionRestController {
	
	@Autowired
	private PoblacionServiceImpl poblacionService;
	
	@GetMapping("/poblaciones")
	public ResponseEntity<List<Poblacion>> findAll(){	
		return ResponseEntity.ok(poblacionService.findAll());//200
	}
	
	@GetMapping("/poblaciones-activas")
	public ResponseEntity<List<Poblacion>> findAllActivos(){
		return ResponseEntity.ok(poblacionService.findAllActivo());//200	
	}
	
	@GetMapping("/poblacion/{id}")
	public ResponseEntity<Poblacion> findById(@PathVariable Long id){
		return ResponseEntity.ok(poblacionService.findById(id));//200
	}
	
	@PostMapping("/poblacion")
	public ResponseEntity<Poblacion> create(@RequestBody Poblacion poblacion){
		return ResponseEntity.status(HttpStatus.CREATED).body(poblacionService.save(poblacion));//201
	}
	
	@PutMapping("/poblacion")
	public ResponseEntity<Poblacion> update(@RequestBody Poblacion poblacion){
		return ResponseEntity.ok(poblacionService.save(poblacion));//200
	}
}