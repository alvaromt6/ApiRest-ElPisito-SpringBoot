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

import com.ipartek.spring.elpisito.apirest.models.entities.Provincia;

import com.ipartek.spring.elpisito.apirest.models.services.ProvinciaServiceImpl;

@RestController
@RequestMapping("/api")
public class ProvinciaRestController {
	
	@Autowired
	private ProvinciaServiceImpl provinciaService;
	
	@GetMapping("/provincias")
	public ResponseEntity<List<Provincia>> findAll(){
		return ResponseEntity.ok(provinciaService.findAll());//200
	}
	
	@GetMapping("/provincias-activas")
	public ResponseEntity<List<Provincia>> findAllActivos(){
		return ResponseEntity.ok(provinciaService.findAllActivo());//200
	}

	@GetMapping("/provincia/{id}")
	public ResponseEntity<Provincia> findById(@PathVariable Long id){
		return ResponseEntity.ok(provinciaService.findById(id));//200
	}
	
	@PostMapping("/provincia")
	public ResponseEntity<Provincia> create(@RequestBody Provincia provincia){	
		return ResponseEntity.status(HttpStatus.CREATED).body(provinciaService.save(provincia));//201
	}

	@PutMapping("/provincia")
	public ResponseEntity<Provincia> update(@RequestBody Provincia provincia){
		return ResponseEntity.ok(provinciaService.save(provincia));//200
	}
}