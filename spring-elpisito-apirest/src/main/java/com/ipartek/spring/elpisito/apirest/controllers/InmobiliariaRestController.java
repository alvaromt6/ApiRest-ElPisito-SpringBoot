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

import com.ipartek.spring.elpisito.apirest.models.entities.Inmobiliaria;
import com.ipartek.spring.elpisito.apirest.models.services.InmobiliariaServiceImpl;

@RestController
@RequestMapping("/api")
public class InmobiliariaRestController {
	
	@Autowired
	private InmobiliariaServiceImpl inmobiliariaService;
	
	@GetMapping("/inmobiliarias")
	public ResponseEntity<List<Inmobiliaria>> findAll(){
		return ResponseEntity.ok(inmobiliariaService.findAll());//200
	}
	
	@GetMapping("/inmobiliarias-activos")
	public ResponseEntity<List<Inmobiliaria>> findAllActivos(){
		return ResponseEntity.ok(inmobiliariaService.findAllActivo());//200
	}
	
	@GetMapping("/inmobiliaria/{id}")
	public ResponseEntity<Inmobiliaria> findById(@PathVariable Long id){
		return ResponseEntity.ok(inmobiliariaService.findById(id));//200
	}
	
	@PostMapping("/inmobiliaria")
	public ResponseEntity<Inmobiliaria> create(@RequestBody Inmobiliaria inmobiliaria){
		return ResponseEntity.status(HttpStatus.CREATED).body(inmobiliariaService.save(inmobiliaria));//201
	}
	
	@PutMapping("/inmobiliaria")
	public ResponseEntity<Inmobiliaria> update(@RequestBody Inmobiliaria inmobiliaria){
		return ResponseEntity.ok(inmobiliariaService.save(inmobiliaria));//200
	}
}
