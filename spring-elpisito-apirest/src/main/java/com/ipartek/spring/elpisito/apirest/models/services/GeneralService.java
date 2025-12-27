package com.ipartek.spring.elpisito.apirest.models.services;

import java.util.List;



public interface GeneralService<T> {

	//Vamos a crear unos métodos BASADOS en el JpaRepository (POR ARQUITECTURA)
	//En el JpaRepository tenemos un montón de métodos pero aquí podemos elegir solo
	//los que nos interesen (no hace falta crear todos)
	//También podemos añadir métodos personalizados (es decir, que no estén en el JpaRepository
	
	List<T> findAll();
	List<T> findAllActivo();
	T save(T t);
	T findById(Long id);
	
	
	
	
}
