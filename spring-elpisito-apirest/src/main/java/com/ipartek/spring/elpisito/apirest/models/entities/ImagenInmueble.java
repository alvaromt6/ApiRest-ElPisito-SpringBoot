package com.ipartek.spring.elpisito.apirest.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="imagenes_inmueble")
public class ImagenInmueble {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(unique = true)
	private String nombre; //64951285612.jpg... (unique)
	
	@Column(name="alt_imagen")
	private String altImagen;
	
	@Column
	private Integer activo = 1;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "Inmueble")
	private Inmueble inmueble; //Este es el MappedBy del @OneToMany de la clase Inmueble
}
