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
@Table(name="inmobiliarias")
public class Inmobiliaria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String nombre; // Artea, el pisito, chalet vizkaya
	
	@Column
	private String telefono;
	
	@Column
	private String representante;
	
	@Column(unique = true)
	private String logo; //url del logo
	
	@Column
	private Integer activo = 1;
	
	@OneToMany(mappedBy = "inmobiliaria")
	@JsonIgnore
	private List<Inmueble> inmuebles;
	
	

}
