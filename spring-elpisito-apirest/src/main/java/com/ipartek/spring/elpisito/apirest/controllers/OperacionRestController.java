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

import com.ipartek.spring.elpisito.apirest.models.entities.Operacion;

import com.ipartek.spring.elpisito.apirest.models.services.OperacionServiceImpl;

@RestController
@RequestMapping("/api")
public class OperacionRestController {
	
	@Autowired
	private OperacionServiceImpl operacionService;
	
	@GetMapping("/operaciones")
	public ResponseEntity<List<Operacion>> findAll(){	
		return ResponseEntity.ok(operacionService.findAll());//200
	}
	
	@GetMapping("/operaciones-activas")
	public ResponseEntity<List<Operacion>> findAllActivos(){
		return ResponseEntity.ok(operacionService.findAllActivo());//200	
	}
	
	@GetMapping("/operacion/{id}")
	public ResponseEntity<Operacion> findById(@PathVariable Long id){
		return ResponseEntity.ok(operacionService.findById(id));//200
	}
	
	@PostMapping("/operacion")
	public ResponseEntity<Operacion> create(@RequestBody Operacion operacion){
		return ResponseEntity.status(HttpStatus.CREATED).body(operacionService.save(operacion));//201
	}
	
	@PutMapping("/operacion")
	public ResponseEntity<Operacion> update(@RequestBody Operacion operacion){
		return ResponseEntity.ok(operacionService.save(operacion));//200
	}
}