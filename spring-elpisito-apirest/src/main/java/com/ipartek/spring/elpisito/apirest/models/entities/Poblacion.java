package com.ipartek.spring.elpisito.apirest.models.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="poblaciones")
public class Poblacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(unique = true)
	private String cp;//codigo postal
	
	@Column(unique = true)
	private String nombre; //MÓSTOLES, GETAFE, PLASENCIA, DOS HERMANAS, VILLASANA DE MENA...
	
	@Column
	private Integer activo = 1;
	
	//La tabla del @joinCloumn es la que va a tener la anotación @ManyToOne 
	@ManyToOne //Muchas poblaciones pueden pertenecer a una provincia
	@JoinColumn(name="provincia")
	 private Provincia provincia; //este es el "mappedBy" de @OneToMany de la clase Provincia

	@OneToMany(mappedBy = "poblacion")
	@JsonIgnore
	private List<Inmueble> inmuebles;
	
}
