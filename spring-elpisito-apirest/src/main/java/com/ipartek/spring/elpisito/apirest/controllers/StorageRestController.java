package com.ipartek.spring.elpisito.apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ipartek.spring.elpisito.apirest.storage.services.StorageService;

@RestController
@RequestMapping("/media")
public class StorageRestController {
	
	@Autowired
	private StorageService storageService;
	
	//Este endpoint recibe un archivo multipart (imagen), lo almacena físicamente 
	//y crea su registro en la base de datos asociado a un inmueble específico
	//Retorna la URI completa para acceder a la imagen subida
	@PostMapping("/imagen/{idInmueble}")
	public ResponseEntity<?> uploadImagen(
			@RequestParam("imagen") MultipartFile multipartFile, //Podemos quitar el identificador del @RequestParam si coincide con el nombre de la variable
			@RequestParam("descripcion") String alt,
			@PathVariable Long idInmueble
			){
		
		//El servicio se encarga de:
		//1. Validar el tipo de archivo (solo jpg y png)
		//2. Generar un nombre único basado en timestamp
		//3. Guardar el archivo físicamente en la carpeta mediafiles
		//4. Crear el registro en la BBDD con la referencia al inmueble
		String nombreImagen = storageService.store(multipartFile, idInmueble, alt);
		
		//Retornamos un código 201 (CREATED) con la URI completa de la imagen
		//Ejemplo de URI: http://localhost:8080/media/imagen/1638475829384.jpg
		return ResponseEntity.status(HttpStatus.CREATED).body(storageService.getURICompletaFile(nombreImagen)); //201
	}
	
	//Este método nos devuelve la imagen física a partir de su nombre
	//Es el endpoint que usaremos en el src de las etiquetas <img> del frontend
	@GetMapping("/imagen/{nombreImagen}")
	public ResponseEntity<?> getImagen(@PathVariable String nombreImagen){
		//Obtenemos el recurso (la imagen física) desde el sistema de archivos
		Resource imagen = storageService.getResource(nombreImagen);
		
		//Obtenemos el tipo MIME de la imagen (image/jpeg o image/png)
		//Esto es importante para que el navegador sepa cómo interpretar el recurso
		String contentType = storageService.getContentType(imagen);
		
		//Construimos el ResponseEntity de esta manera especial 
		//porque tenemos que añadir el header CONTENT_TYPE manualmente
		//para que el navegador interprete correctamente el tipo de archivo
		return ResponseEntity
				.ok() //200
				.header(HttpHeaders.CONTENT_TYPE, contentType) //Especificamos que es una imagen
				.body(imagen); //El cuerpo de la respuesta es el archivo binario de la imagen
				
	}
	
	//Este método elimina la imagen física del servidor y su registro en la BBDD
	//IMPORTANTE: Usa el ID de la imagen (no el nombre del archivo)
	@DeleteMapping("/imagen/{idImagen}")
	public ResponseEntity<?> deleteImagenById(@PathVariable Long idImagen){
		//El servicio se encarga de:
		//1. Buscar el registro en la BBDD por ID
		//2. Eliminar el registro de la tabla imagen_inmueble
		//3. Eliminar el archivo físico de la carpeta mediafiles
		storageService.deleteFileCompleto(idImagen);
		
		return ResponseEntity.status(HttpStatus.OK).body("La imagen y su registro en la base de datos han sido eliminados con éxito"); //200
	}
	
}