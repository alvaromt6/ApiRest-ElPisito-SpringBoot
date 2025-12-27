package com.ipartek.spring.elpisito.apirest.models.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="provincias")
public class Provincia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(unique = true)
	private String nombre; //BIZKAIA ,BARCELONA, SEVILLA, MADRID...
	
	@Column
	private Integer activo = 1;
	
	@JsonIgnore //Evita que en un JSON de una provincia el listado de poblaciones no se propague 
	@OneToMany (mappedBy = "provincia") //Una provincia puede tener muchas poblaciones
	private List<Poblacion> poblaciones;

}
