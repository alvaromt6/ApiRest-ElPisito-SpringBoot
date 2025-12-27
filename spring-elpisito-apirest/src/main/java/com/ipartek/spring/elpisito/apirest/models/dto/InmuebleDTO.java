package com.ipartek.spring.elpisito.apirest.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor

public class InmuebleDTO {
	
	/*
	 Un DTO (Data Transfer Object) sirve para separar las capas de una aplicación 
	 especialmente la capa de presentación de una API Rest de la capa de datos (entidades de la BBDD)
	 Esto se logra creando clases simples con atributos que representen los datos que realmente necesitamos.
	 transferir, evitando exponer la estrucctura interna de las entidades de negocio al exterior.
	 
	 ¿Para que sirve un DTO en Spring?
	 1) SEPARACIÓN DE RESPONSABILIDADES:
	 	permite que las distintas capas de una aplicación tengan distintas responsabilidades y no dependan 
	 	indirectamente unas de otras
	 	
	 2) FLEXIBILIDAD Y CONTROL:
	 	un DTO puede contener solo los datos necesarios para una operación específica evitando exponer 
	 	información sensible o irrelevante de las entidades de la BBDD
	 	
	 3) TRANSFERENCIA DE DATOS SIMPLIFICADA:
	 	facilita la transferencia de datos entre las distintas partes de la aplicaciónen volumen
	 	haciendo este proceso más ligero.
	 
	 4) MEJORA EL RENDIMIENTO:
	 	al enviar menos dato sen una sola respuesta se puede reducir el tráfico en la red y mejorar 
	 	el rendimiento de la aplicación.
	 
	 5)FACILITA LA REUTILIZACIÓN DE CÓDIGO:
	 	los DTOs pueden ser reutilizados en diferentes partes de la aplicación que requieran los mismos datos.
	 	
	 6)PROTECCIÓN DE ENTIDADES:
	 	los DTOs ayudan a proteger las entidades de la BBDD de cambios no deseados o de exposición a capas externas
	 	
	 */
	
	private Long id;

}
