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


import com.ipartek.spring.elpisito.apirest.models.entities.Usuario;
import com.ipartek.spring.elpisito.apirest.models.services.UsuarioServiceImpl;



@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	//Una de las características mas significativas
	//de un controlador es que sus atributos suelen ser servicios
	
	//Cuando un controlador recibe los datos del servicio (no se ha producido ningún error etc)
	//la API SIEMPRE envía automáticamente un 200 a Cliente. 
	//Pero si queremos personalizar la respuesta: 200, 201, 202...también podemos hacerlo
	//Solo los controladores devuelven códigos status 200, 201...etc(todo va bien...)
	//En el caso de problemas son las clases anotadas como @RestControllerAdvice las que devuelven los errores
	
	//No utilizar nunca en un controlador try/catch
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> findAll() {
		/*List<Usuario> resultado = usuarioService.findAll();
		return new ResponseEntity<List<Usuario>>(resultado, HttpStatus.OK);//200*/
		
		return ResponseEntity.ok(usuarioService.findAll());//200
	}

	@GetMapping("/usuarios-activos")
	public ResponseEntity<List<Usuario>> findAllActivos(){
		return ResponseEntity.ok(usuarioService.findAllActivo());//200
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id){
		return ResponseEntity.ok(usuarioService.findById(id));//200
	}
	@PostMapping("/usuario")
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));//201
	}

	@PutMapping("/usuario")
	public ResponseEntity<Usuario> update(@RequestBody Usuario usuario){
		return ResponseEntity.ok(usuarioService.save(usuario));//200
	}
}
