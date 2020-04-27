package com.insa.backend.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.insa.backend.models.Proyecto;

public interface IProyectoDao extends CrudRepository<Proyecto, Long>{

	@Query("select p from Proyecto p where p.codigoProyecto = ?1")
	public Proyecto findByNombre(String cod);
}
