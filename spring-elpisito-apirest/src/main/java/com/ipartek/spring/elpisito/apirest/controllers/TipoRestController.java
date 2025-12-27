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

import com.ipartek.spring.elpisito.apirest.models.entities.Tipo;

import com.ipartek.spring.elpisito.apirest.models.services.TipoServiceImpl;

@RestController
@RequestMapping("/api")
public class TipoRestController {
	
	@Autowired
	private TipoServiceImpl tipoService;
	
	@GetMapping("/tipos")
	public ResponseEntity<List<Tipo>> findAll(){
		return ResponseEntity.ok(tipoService.findAll());//200
	}
	
	@GetMapping("/tipos-activos")
	public ResponseEntity<List<Tipo>> findAllActivos(){
		return ResponseEntity.ok(tipoService.findAllActivo());//200
	}
	
	@GetMapping("/tipo/{id}")
	public ResponseEntity<Tipo> findById(@PathVariable Long id){
		return ResponseEntity.ok(tipoService.findById(id));//200
	}
	
	@PostMapping("/tipo")
	public ResponseEntity<Tipo> create(@RequestBody Tipo tipo){
		return ResponseEntity.status(HttpStatus.CREATED).body(tipoService.save(tipo));//201
	}
	
	@PutMapping("/tipo")
	public ResponseEntity<Tipo> update(@RequestBody Tipo tipo){
		return ResponseEntity.ok(tipoService.save(tipo));//200
	}
}
