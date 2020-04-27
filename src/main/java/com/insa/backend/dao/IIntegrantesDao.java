package com.insa.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.insa.backend.models.Integrante;

public interface IIntegrantesDao  extends CrudRepository<Integrante, Long>{
	
	
}
