package com.ipartek.spring.elpisito.apirest.models.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name="usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //IDENTITY es un incremental para MySQL
	@Column
	private Long id;
	
	@Column(unique=true)
	private String nombre;
	
	@Column
	private String password;
	
	@Column(name = "passWord_open")
	private String passwordOpen;
	
	@Column(unique=true)
	private String email;
	
	@Column
	private String rol = "ROLE_USER";
	
	@Column
	private Integer activo = 1;
	
	//Hemos elegido Usuario como entidad dominante de la relación, es el usuario el que "toma la decisión de favoritar el inmueble y no alreves"
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "usuario_inmueble",
			joinColumns = {@JoinColumn(name = "usuario_id")},
			inverseJoinColumns =  {@JoinColumn(name = "inmueble_id")}
			)
	private Set<Inmueble> inmueblesFavoritos;
	
	

}
