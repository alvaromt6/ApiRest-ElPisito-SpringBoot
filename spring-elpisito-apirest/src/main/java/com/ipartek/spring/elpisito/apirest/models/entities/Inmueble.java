package com.ipartek.spring.elpisito.apirest.models.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name="inmuebles")
public class Inmueble {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String via; //CALLE, PLAZA, CARRETERA, AVENIDA...
	
	@Column(name="nombre_via")
	private String nombreVia;
	
	@Column
	private String claim; //Maravilloso piso!!!, Fantasticas vistas!!!
	
	@Column
	private String numero; //4, 4B...
	
	@Column
	private String planta;//SIN PLANTA, PRIMERA, SEGUNDA...
	
	@Column
	private String puerta; //A,C, 4B....
	
	@Column
	private String apertura; //INTERIOR, EXTERIOR, SEMI-EXTERIOR...
	
	@Column
	private String orientacion; //NORTE, SUR...
	
	
	@Column(name="superficie_util")
	private Double superficieUtil;
	
	
	@Column(name="superficie_construida")
	private Double superficieConstruida;
	
	
	@Column
	private Double precio;
	
	@Column
	private String habitaciones; 
	
	@Column
	private String banhos;
	
	
	@Column(length=3500)
	private String descripcion; //Una descripción amplia del inmueble
	
	
	@Column
	private String calefaccion; //CENTRAL, ELÉCTRICA, SIN CALEFACCIÓN
	
	@Column
	private Integer amueblado; //1 o 0
	
	@Column
	private String balcones;
	
	@Column
	private String garajes;
	
	
	@Column
	private Integer piscina; //1 o 0
	
	@Column
	private Integer trastero; //1 o 0
	
	@Column
	private Integer ascensor; //1 o 0
	
	@Column
	private Integer jardin; //1 o 0
	
	@Column
	private Integer tendedero; //1 o 0
	
	@Column
	private Integer portada = 0; //1 o 0
	
	@Column
	private Integer oportunidad = 0; //1 o 0
	
	
	@Column
	private Integer gadget = 0; //Tamaño en el que se manifiesta en cliente el gadget oportunidad
		
	
	@Column
	private Integer activo = 1;
	
	
	@ManyToOne
	@JoinColumn(name="tipo")
	private Tipo tipo; //Este el el mappedBy del @OneToMany de la clase Tipo
	
	
	
	@ManyToOne
	@JoinColumn(name="poblacion")
	private Poblacion poblacion;//Este el el mappedBy del @OneToMany de la clase Poblacion
	
	
	@ManyToOne
	@JoinColumn(name="operacion")
	private Operacion operacion;//Este el el mappedBy del @OneToMany de la clase Operacion
	
	
	
	@OneToMany(mappedBy="inmueble")
	private List<ImagenInmueble> imagenes;
	
	
	@ManyToOne
	@JoinColumn(name="inmobiliaria")
	private Inmobiliaria inmobiliaria;//Este el el mappedBy del @OneToMany de la clase Inmobiliaria
	
	//Inversa de la relación (hemos tomado la entidad Usuario como dominante en la relación Usuario-Inmueble
	@ManyToMany(mappedBy = "inmueblesFavoritos")
	private Set<Usuario> usuariosQueLoFavorean;
	

}
