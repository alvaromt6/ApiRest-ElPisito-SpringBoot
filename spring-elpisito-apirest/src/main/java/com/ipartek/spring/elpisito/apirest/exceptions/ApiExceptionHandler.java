package com.ipartek.spring.elpisito.apirest.exceptions;

import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

//¿Una aplicación puede tener varios @RestControllerAdvice?: la respuesta es si.
//No solo puede tener varios sino que además es recomendado. Es buena práctica tener 
//un @RestControllerAdvice general y después tener otros mas especializados: por ejemplo, un 
//@RestControllerAdvice especializado en recoger y tratar errores de seguridad
//Si tenemos varios ¿cual se llama primero, en qué orden?: para ello existe una notación
//complementaria llamada @Orde en la que establecemos el orden de llamada. Ejemplo @Order(1)
//Otra pregunta: ¿podríarmos tener una excepción "duplicada" tratada en varios @RestControllerAdvice?:
//en teroría si, pero existe una norma de buena práctica que nos dice que no debemos duplicar handlers
//para tratera un mismo error si está debidamente justificado

//Spring Security no envia excepciones al @RestControllerAdvice (errores internos)
//Pero las excepciones que se producen en nuestro código interno(por ejemplo un controller)
//si son enviadas 
@RestControllerAdvice
public class ApiExceptionHandler {
	
	//ESTE ES EL CONTROLADOR GLOBAL DE ERRORES. SUS MÉTODOS SON INVOCADOS AUTOMÁTICAMENTE POR SPRING
	//CUNADO SE PRODUCE UN ERROR SALTANDOSE LA CADENA "STACK TRACE"
	
	//Si se produce una excepción el flujo es el siguiente:
	//cliente ----> Controlador --> Servicio ----> Se produce una excepción ----->@RestControllerAdvice ------> cliente
	
	
	//Si todo va bien el flujo es el siguiente;
	//cliente ----> Controlador --> Servicio ---->Repositorio ----->Servicio -------> controlador  ------> cliente
	
	
	
	//=============================================================================================================
	//Este método se encarga de contruir el objeto ErrorResponse con los datos que le para Spring
	private ResponseEntity<ErrorResponse> build(HttpStatus status, Exception ex, HttpServletRequest req ){
		ErrorResponse error = new ErrorResponse(
				status.value(),
				status.getReasonPhrase(),
				ex.getMessage(),
				req.getRequestURI()
				
				);
		//return new ResponseEntity<ErrorResponse>( error , status );
		return ResponseEntity.status(status).body(error);
	}
	
	//=============================================================================================================
	//EXCEPCIONES API
	//=============================================================================================================
	
	//Intentamos insertar un registro con un campo inexistente relacionado con otra tabla
	//Ejemplo (Violación de una clave foránea FK): Tenemos una imagen y queremos subirla al servidor y relacionarla con un inmueble cuyo id no exste
	
	//Intentamos crear o actulizar con datos corruptos o invalidos
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> dataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest req){
		
		return build(HttpStatus.CONFLICT, ex, req); //409
	}
	
	//Intentamos insertar un registo cuyo password esté duplicado
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ErrorResponse> duplicateKey(DuplicateKeyException ex, HttpServletRequest req){
		
		return build(HttpStatus.CONFLICT, ex, req); //409
	}
	
	//Intentamos actualizar un id inexistente
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<ErrorResponse> emptyResultDataAccess(EmptyResultDataAccessException ex, HttpServletRequest req){
		
		return build(HttpStatus.NOT_FOUND, ex, req); //404
	}
	
	//BBDD caida (no confundir con la API. Si la API está caída no puede devolver nada!!!)
	@ExceptionHandler(DataAccessResourceFailureException.class)
	public ResponseEntity<ErrorResponse> dataAccessResourceFailure(DataAccessResourceFailureException ex, HttpServletRequest req){
		
		return build(HttpStatus.SERVICE_UNAVAILABLE, ex, req); //503 
	}
	
	//Pasamos un formato incorrecto: 2023 - 31 -12
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> constraintViolation(ConstraintViolationException ex, HttpServletRequest req){
		
		return build(HttpStatus.BAD_REQUEST, ex, req); //400
	}
	
	//Intentamos pasar datos a la BBDD fuera de rango, longitud etc
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException ex, HttpServletRequest req){
		
		return build(HttpStatus.BAD_REQUEST, ex, req); //400
	}
	
	//Ocurre si existe un archivo "normal" en el logar donde se intenta crear un directorio
	//Por ejemplo intentamos crear el directorio "mediafiles" y en ese lugar existe un archivo llamado mediafiles.jpg (por ejemplo)
	@ExceptionHandler(FileAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> fileAlreadyExists(FileAlreadyExistsException ex, HttpServletRequest req) {

		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); // 500

	}
	
	//Cuando el proceso no tiene permisos para crear el directorio
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> accessDenied (AccessDeniedException ex, HttpServletRequest req) {
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); // 500
		
	}
	
	//Puede ocurrir si algún componente intermedio de la ruta (uri) no existe y el sistema no permite crear todo el árbol
	@ExceptionHandler(NoSuchFileException.class)
	public ResponseEntity<ErrorResponse> noSuchFile (NoSuchFileException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); // 500
		
	}
	
	//Error genérico del sistema de archivos (Disco lleno, ruta invalida, archivo corrupto etc.)
	@ExceptionHandler(FileSystemException.class)
	public ResponseEntity<ErrorResponse> fileSystem (FileSystemException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); // 500
		
	}
	
	
	/*
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorResponse> io (IOException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); // 500
		
	}
	*/
	
	
	
	//=============================================================================================================
	//EXCEPCIONES PERSONALIZADAS
	//=============================================================================================================
	
	@ExceptionHandler(ErrorInternoServidorException.class)
	public ResponseEntity<ErrorResponse> errorInternoServidor(ErrorInternoServidorException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); //500
	}
	
	@ExceptionHandler(DatosInvalidosException.class)
	public ResponseEntity<ErrorResponse> datosInvalidos(DatosInvalidosException ex, HttpServletRequest req){
		
		return build(HttpStatus.BAD_REQUEST, ex, req); //400
	}
	
	@ExceptionHandler(ErrorBaseDatosException.class)
	public ResponseEntity<ErrorResponse> errorBaseDatos(ErrorBaseDatosException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); //500
	}
	
	@ExceptionHandler(FormatoNoSoportadoException.class)
	public ResponseEntity<ErrorResponse> formatoNoSoportado(FormatoNoSoportadoException ex, HttpServletRequest req){
		
		return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex, req); //415
	}
	
	@ExceptionHandler(PeticionMalFormadaException.class)
	public ResponseEntity<ErrorResponse> peticionMalFormada(PeticionMalFormadaException ex, HttpServletRequest req){
		
		return build(HttpStatus.BAD_REQUEST, ex, req); //400
	}

	@ExceptionHandler(RecursoEnUsoException.class)
	public ResponseEntity<ErrorResponse> recursoEnUso(RecursoEnUsoException ex, HttpServletRequest req){
		
		return build(HttpStatus.CONFLICT, ex, req); //409
	}
	
	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<ErrorResponse> recursoNoEncontrado(RecursoNoEncontradoException ex, HttpServletRequest req){
		
		return build(HttpStatus.NOT_FOUND, ex, req); //404
	}
	
	@ExceptionHandler(RecursoYaExistenteException.class)
	public ResponseEntity<ErrorResponse> recursoYaExistente(RecursoYaExistenteException ex, HttpServletRequest req){
		
		return build(HttpStatus.CONFLICT, ex, req); //409
	}
	
	@ExceptionHandler(MultipartVacioException.class)
	public ResponseEntity<ErrorResponse> multipartVacio(MultipartVacioException ex, HttpServletRequest req){
		
		return build(HttpStatus.BAD_REQUEST, ex, req); //400
	}
	
	@ExceptionHandler(TratamientoTipoMimeException.class)
	public ResponseEntity<ErrorResponse> tratamientoTipoMime(TratamientoTipoMimeException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); //500
	}
	
	@ExceptionHandler(MultipartTratamientoException.class)
	public ResponseEntity<ErrorResponse> multipartTratamiento(MultipartTratamientoException ex, HttpServletRequest req){
		
		return build(HttpStatus.CONFLICT, ex, req); //409
	}
	
	@ExceptionHandler(SubidaArchivoException.class)
	public ResponseEntity<ErrorResponse> subidaArchivo(SubidaArchivoException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); //500
	}
	
	@ExceptionHandler(BorradoArchivoException.class)
	public ResponseEntity<ErrorResponse> borradoArchivo(BorradoArchivoException ex, HttpServletRequest req){
		
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req); //500
	}
	
	
	//=============================================================================================================
	//EXCEPCIONES PERSONALIZADAS DE SEGURITY
	//=============================================================================================================
	/*Es mejor que security las gestione
	@ExceptionHandler(AutenticacionException.class)
	public ResponseEntity<ErrorResponse> autenticacion(AutenticacionException ex, HttpServletRequest req){
		
		return build(HttpStatus.UNAUTHORIZED, ex, req); //401
	}
	
	@ExceptionHandler(AutorizacionException.class)
	public ResponseEntity<ErrorResponse> autorizacion(AutorizacionException ex, HttpServletRequest req){
		
		return build(HttpStatus.FORBIDDEN, ex, req); //403
	}
	*/
	
}
