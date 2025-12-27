package com.ipartek.spring.elpisito.apirest.storage.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ipartek.spring.elpisito.apirest.exceptions.BorradoArchivoException;
import com.ipartek.spring.elpisito.apirest.exceptions.ErrorInternoServidorException;
import com.ipartek.spring.elpisito.apirest.exceptions.PeticionMalFormadaException;
import com.ipartek.spring.elpisito.apirest.exceptions.RecursoNoEncontradoException;
import com.ipartek.spring.elpisito.apirest.exceptions.SubidaArchivoException;
import com.ipartek.spring.elpisito.apirest.models.dao.ImagenInmuebleDAO;
import com.ipartek.spring.elpisito.apirest.models.dao.InmuebleDAO;
import com.ipartek.spring.elpisito.apirest.models.entities.ImagenInmueble;
import com.ipartek.spring.elpisito.apirest.models.entities.Inmueble;
import com.ipartek.spring.elpisito.apirest.storage.utilities.UtilidadesStorage;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class StorageService {
	
	@Autowired
	private ImagenInmuebleDAO imagenInmuebleDAO;
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	@Autowired
	private HttpServletRequest request;
	
	// Con esta propiedad estamos indicando que la propiedad definida
	// en el "application-properties" media.location se refiere al nombre de la
	// carpeta en la que queremos albergar los archivos físicos que subamos al servidor
	// (en nuestro caso "mediafiles")
	// @Value sirve para dar un valor determinado a un atributo
	@Value("${media.location}")
	private String mediaLocation;
	
	// Este objeto de tipo Path representa la ruta RAIZ del almacenamiento del protocolo FILE
	private Path rootLocation;
	
	// Cuando un método está anotado con @PostConstruct es llamado automáticamente
	// en el momento en que la clase se instancia
	@PostConstruct 
	public void init() throws IOException {
		// Iniciamos la ruta de almacenamiento
		rootLocation = Paths.get(mediaLocation); // rootLocation (Path) apunta a la carpeta de destino (mediafiles)
		
		try {
			// Esta línea puede producir una IOException (tipo checked de obligado tratamiento)
			// La excepción no se produce si el directorio ya existe
			Files.createDirectories(rootLocation); // Verifica la existencia y solo la crea si no existe
		} catch (IOException e) {
			throw new ErrorInternoServidorException(
				"Error producido al intentar crear un directorio de almacenamiento multimedia en " + rootLocation.toString());
			
		}
	}
	
	public String store(MultipartFile file, Long idInmueble, String descripcion) {
		// En este método vamos a bifurcar la tarea en dos fases:
		// FASE 0: Validaciones previas (antes de crear nada)
		// FASE 1: Crear un nombre para el posterior almacenamiento del archivo físico
		//         a un directorio del servidor (mediafiles)
		// FASE 2: Registro en la BBDD
		
		///////////////////////////////////////////////////////////////
		/// FASE 0: VALIDACIONES PREVIAS
		///////////////////////////////////////////////////////////////
		
		/// Paso 0: Verificar si el multipart está vacío
		if (file.isEmpty()) {
			throw new MultipartException("Error al intentar almacenar el archivo dado que el archivo esta vacio");
			// Cuando lanzamos una excepción con un throw las líneas de código siguientes no se ejecutan jamás
			// Es decir salimos del método automáticamente
		}
		
		/// Paso 1: Si llegamos aquí estamos seguros que el Multipart no está vacío (sabemos que hay un archivo)
		/// pero no sabemos de qué tipo de archivo se trata. Para comprobar el tipo de archivo recibido debemos
		/// comprobar su tipo MIME
		/// https://developer.mozilla.org/es/docs/Web/HTTP/Guides/MIME_types/Common_types
		/// En nuestro caso solo vamos a dejar subir archivos (.jpg y .png)
		/// Los tipos MIME permitidos los vamos a matizar en un ArrayList para poderlos manejar con mayor facilidad
		List<String> tiposPermitidos = List.of("image/jpeg", "image/png");
		
		String tipoNormalizado = UtilidadesStorage.compruebaConcordanciaTipoMIME(file, tiposPermitidos);
		
		// Si el método compruebaConcordanciaTipoMIME arroja alguna excepción el código se corta aquí
		// En consecuencia si llegamos aquí es porque no ha ocurrido ninguna excepción
		
		String tipo = UtilidadesStorage.DevuelveExtensionTipoMIME(tipoNormalizado);
		
		// Si no ha habido ninguna excepción en este punto del código
		// HEMOS CONSEGUIDO LA EXTENSIÓN REAL DEL ARCHIVO QUE SE REGISTRARÁ EN LA BASE DE DATOS.
		
		/// Paso 2: CRÍTICO - Validar que el inmueble existe ANTES de subir el archivo físico
		/// Esto evita crear archivos huérfanos si el inmueble no existe en la BBDD
		/// Si el inmueble no existe, se lanza una excepción y NO se continúa con la subida
		Inmueble inmueble = inmuebleDAO.findById(idInmueble)
			.orElseThrow(() -> new RecursoNoEncontradoException(
				"El inmueble con ID " + idInmueble + " no se ha podido encontrar en la BBDD"
			));
		
		// Si llegamos aquí: archivo válido + tipo correcto + inmueble existe
		
		///////////////////////////////////////////////////////////////
		/// FASE 1: SUBIDA FÍSICA DEL ARCHIVO
		///////////////////////////////////////////////////////////////
		
		/// Paso 3: En este paso VAMOS A CREAR el nombre del archivo sustituyendo el nombre original
		/// ("cocina", "salon principal") por un timestamp único
		String nombreImagen = String.valueOf(Calendar.getInstance().getTimeInMillis()); // 5565615
		
		nombreImagen = nombreImagen.concat(tipo); // "5565615.jpg"
		
		/// Paso 4: Vamos a añadir a la ruta prefijada de almacenamiento
		/// (que está incompleta porque le falta el nombre de la imagen)
		/// el nombre de la imagen (variable nombreImagen)
		/// rootLocation maneja la ruta del protocolo "file" (no la del protocolo http)
		Path rutaCompletaImagen = rootLocation.resolve(Paths.get(nombreImagen));
		// Ya tenemos el path completo en el que se va a guardar físicamente la imagen
		
		/// Paso 5: Vamos a crear (AÑADIR FÍSICAMENTE) el archivo a su destino de almacenamiento (mediafiles)
		/// Esta operación la tenemos que meter en un try con recursos
		/// Recordemos que en un try con recursos solo son admitidos
		/// objetos de clases que implementen la interface Autocloseable
		try (
			// Utilizamos un try con recursos para que sea Java el encargado
			// de cerrar el objeto inputStream. Este proceso si no se hace utilizando
			// un try catch con recursos, acarrearía utilizar un try dentro de otro try...
			// No olvidemos que un objeto InputStream consume una cantidad grande de memoria
			// Tampoco olvidemos que la clase InputStream implementa la interface Autocloseable
			// característica imprescindible para poder utilizar dentro del try con recursos
			
			InputStream inputStream = file.getInputStream();
			
		) {
			// Aquí es donde finalmente se realiza FÍSICAMENTE la subida del archivo
			// De existir un archivo con el mismo nombre, el método en su tercer argumento
			// nos proporciona la posibilidad de elegir qué hacer. En nuestro caso elegimos
			// eliminar el anterior y remplazarlo por el nuevo (aunque hay más opciones...)
			
			Files.copy(inputStream, rutaCompletaImagen, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (Exception e) {
			throw new SubidaArchivoException(
				"Error en el proceso de almacenamiento físico del archivo MultipartFile: " + e.getMessage()
			);
		}
		
		///////////////////////////////////////////////////////////////
		/// FASE 2: REGISTRO EN LA BASE DE DATOS
		///////////////////////////////////////////////////////////////
		
		/// Paso 6: Una vez tenemos el archivo físico, vamos a crear la notación en la BBDD
		/// para que sea compatible con el archivo físico que acabamos de subir
		/// Si la subida física (Paso 5) hubiera arrojado algún problema (no llegaríamos a este punto)
		/// el resultado hubiera sido que no existe ninguna notación en la BBDD
		/// 
		/// IMPORTANTE: La razón por la que validamos el inmueble en el Paso 2 (ANTES de subir)
		/// es para evitar crear archivos huérfanos. Si validáramos aquí, ya habríamos creado
		/// un archivo físico que no tiene sentido (porque el inmueble no existe)
		
		/// Instanciamos una imagenInmueble (imagen) que este será el objeto que guardemos en la BBDD
		ImagenInmueble imagen = new ImagenInmueble();
		
		/// Seteamos el nombre, inmueble (al que hace referencia) y el Alt de la imagen
		imagen.setNombre(nombreImagen);    // Generado en Paso 3
		imagen.setInmueble(inmueble);      // Validado en Paso 2
		imagen.setAltImagen(descripcion);  // Recibido como parámetro
		
		try {
			// Intentamos guardar el registro en la base de datos
			imagenInmuebleDAO.save(imagen);
			
		} catch (Exception e) {
			// Si falla el guardado en BBDD, eliminamos el archivo físico
			// para mantener la consistencia entre BBDD y sistema de archivos
			// De lo contrario, quedaría un archivo huérfano en mediafiles
			try {
				deleteFileFisico(nombreImagen);
			} catch (Exception deleteException) {
				// Log del error secundario pero lanzar el error principal
				System.err.println("ERROR CRÍTICO: No se pudo eliminar el archivo huérfano: " 
					+ nombreImagen + " - " + deleteException.getMessage());
			}
			
			throw new SubidaArchivoException(
				"Error al registrar la imagen en la Base de datos: " + e.getMessage()
			);
		}
		
		// Si llegamos aquí: Todo exitoso ✅
		// - Archivo físico creado en mediafiles
		// - Registro guardado en la BBDD
		// - Sistema consistente
		return nombreImagen;
	}
	
	public String getContentType(Resource resource) {
		try {
			String contentType = Files.probeContentType(resource.getFile().toPath()); // "imagen/jpeg"
			
			// Si no se puede determinar, usar un tipo por defecto
			if (contentType == null) {
				contentType = "application/octet-stream";
			}
			
			return contentType;
			
		} catch (IOException e) {
			throw new PeticionMalFormadaException("Error al intentar conseguir el content type de un Resource");
		}
	}
	
	/// import org.springframework.core.io.Resource;
	/// Resource es de core.io!!!!! OJO!!!
	/// Este método nos viene genial si el recurso no es una imagen (pdf, excel...)
	public Resource getResource(String filename) {
		Path rutaCompleta = rootLocation.resolve(Paths.get(filename));
		
		/// Podemos conseguir el objeto Resource del objeto Path
		/// que contiene la ruta completa del archivo físico
		try {
			Resource resource = new UrlResource(rutaCompleta.toUri()); // Puede provocar un MalformedURLException
			
			// Vamos a comprobar si existe esa ruta al archivo
			if (resource.exists() && resource.isReadable()) {
				return resource;
			} else {
				throw new PeticionMalFormadaException(
					"No se encuentra el archivo en la ruta " + rutaCompleta.toUri() + " porque probablemente no existe"
				);
			}
		} catch (MalformedURLException e) {
			throw new PeticionMalFormadaException(
				"Error al encontrar el archivo, la ruta: " + rutaCompleta.toUri() + " está mal formada"
			);
		}
	}
	
	/// Este método viene genial para visualizar imágenes a través de su URI
	public String getURICompletaFile(String filename) {
		/// Obtenemos la URI del nombre filename (protocolo Http)
		/// No confundir con el objeto Path que obtenemos con el rootLocation.resolve que maneja el protocolo file
		///
		/// Obtener el host en el que nos encontramos
		String host = request
				.getRequestURL()
				.toString()
				.replace(request.getRequestURI(), ""); // http://localhost:8080 ...en desarrollo claro!!!
		
		return ServletUriComponentsBuilder
				.fromUriString(host) // Añadimos la primera parte: http://localhost:8080
				.path("/media/imagen/") // Añadimos la ruta donde se encuentra el recurso http://localhost:8080/media/imagen/
				.path(filename) // http://localhost:8080/media/imagen/51945.jpg
				.toUriString();
	}
	
	/// Este método borra la imagen física de mediafiles.
	public void deleteFileFisico(String filename) {
		try {
			// Conseguimos el objeto Path completo añadiéndole la filename
			Path filePath = rootLocation.resolve(filename).normalize(); // normalize limpia la ruta eliminando redundancias
			
			/// Dejamos en un archivo de tipo File el objeto perteneciente a la ruta filePath
			/// No olvidemos que la ruta no es el objeto físico de la imagen
			File file = filePath.toFile();
			
			// Verificar si la eliminación fue exitosa
			if (file.exists()) {
				boolean deleted = file.delete();
				if (!deleted) {
					throw new BorradoArchivoException("No se pudo eliminar el archivo físico: " + filename);
				}
			}
			
		} catch (Exception e) {
			throw new BorradoArchivoException("Error al intentar el borrado físico de la imagen: " + filename);
		}
	}
	
	/// Este método borra el registro y la imagen física de mediafiles.
	public void deleteFileCompleto(Long idImagen) {
		// Obtenemos el objeto ImagenInmueble
		ImagenInmueble imagen = imagenInmuebleDAO.findById(idImagen)
			.orElseThrow(() -> new RecursoNoEncontradoException(
				"La imagen con id: " + idImagen + " no existe en la BBDD"
			));
		
		// Si llegamos aquí es que la imagen existe
		try {
			// 1) Eliminamos el registro de la BBDD
			imagenInmuebleDAO.deleteById(idImagen);
			
		} catch (Exception e) {
			throw new BorradoArchivoException(
				"Error al intentar el borrado en la Base de datos el registro de la imagen " + imagen.getNombre()
			);
		}
		
		// 2) Eliminamos la imagen físicamente
		deleteFileFisico(imagen.getNombre());
	}
}