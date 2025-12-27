package com.ipartek.spring.elpisito.apirest.storage.utilities;

import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import com.ipartek.spring.elpisito.apirest.exceptions.FormatoNoSoportadoException;
import com.ipartek.spring.elpisito.apirest.exceptions.MultipartTratamientoException;
import com.ipartek.spring.elpisito.apirest.exceptions.TratamientoTipoMimeException;

public class UtilidadesStorage {
	
	//Este método comprueba si el tipo MiME del archivo MultipartFile recibido comoargumento 
	//es permitido 8coincide con alguno de los tipos pasadas en el ArrayList también recibido como argumento 
	//en el caso de que exista concordancia (el tipo sea permitido) devuelve el tipo REAL ("image/jpeg"...etc...)
	public static String compruebaConcordanciaTipoMIME(MultipartFile file, List<String> tiposPermitidos) {
		
		//Extraemos el nombre del archivo con su extensión del MultipartFile
		//esta operación es suceptible de lanzar un NullPointerException
		String nombreDelArchivo = null;
		
		try {
			nombreDelArchivo = file.getOriginalFilename().toLowerCase(); //"cocina.jpg" ...
		} catch (NullPointerException e) {
			throw new MultipartTratamientoException("Error al intentar conseguir el nombre del archivo del MultipartFile");
		}
		
		String tipoDetectado = null;
		
		try {
			tipoDetectado = new Tika().detect(file.getInputStream()); //"imagen/jpeg"
		} catch (Exception e) {
			throw new TratamientoTipoMimeException("Error al detectar el tipo MIME mediante el archivo MultipartFile");
		}
		
		//Hay diversos tipos que TIKa detecta y que requieren una "normalización" porque no son "interpretables" direc
		
		String tipoNormalizado = normalizaMIMEParaTIKA(tipoDetectado, nombreDelArchivo);
		
		if(!tiposPermitidos.contains(tipoNormalizado)) {
			throw new FormatoNoSoportadoException("Error al subir el archivo. El tipo de archivo que estas intentando subir es invalido");
		}
		
		return tipoNormalizado;
		
	}
	
	public static String DevuelveExtensionTipoMIME(String tipo) {
		//Pasar el tipo MIME ("imagen/jpeg") a ".jpg"
		
		MimeTypes allMimeTypes = MimeTypes.getDefaultMimeTypes();
		MimeType tipoMime = null;
		
		try {
			tipoMime =  allMimeTypes.forName(tipo);
		} catch (MimeTypeException e) {
			throw new TratamientoTipoMimeException("Error al intentar generar la extensión de un archivo a partir de su tipo MIME");
		}
				
		return tipoMime.getExtension();
		
	}
	
	//Hay veces que con algunos tipos TIKA no devuelve los tipos de manera "interpretable" por lo que 
	//lo sometemos a un proceso de normalización
	public static String normalizaMIMEParaTIKA(String tipoDetectado, String filename) {
		
		String tipoNormalizado = tipoDetectado; //Si no entramos en los If el método devuelve lo que recibe
		
		//Limpiamos el tipo detectado de posibles apuntes que haya hecho Tika...
		if(tipoDetectado.contains(";")) {
			tipoDetectado = tipoDetectado.split(";")[0].trim();
		}
		
		// Normalizar para docx (word),xlsx(excel),pptx(power point)
		if (tipoDetectado.equals("application/x-tika-ooxml") && filename.endsWith(".docx")) {
			tipoNormalizado = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else if (tipoDetectado.equals("application/x-tika-ooxml") && filename.endsWith(".xlsx")) {
			tipoNormalizado = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			} else if (tipoDetectado.equals("application/x-tika-ooxml") && filename.endsWith(".pptx")) {
				tipoNormalizado = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
				} else if( (tipoDetectado.equals("application/x-rar-compressed")||tipoDetectado.equals("application/vnd.rar")||tipoDetectado.equals("application/octet-stream")) && filename.endsWith(".rar")) {
					tipoNormalizado = "application/x-rar-compressed";
		}
		
		
		return tipoNormalizado;
		
		
	}

}
