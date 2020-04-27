package com.insa.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.insa.backend.models.Usuarios;

public interface IUsuarioDao extends CrudRepository<Usuarios, Long>{
	
	public Usuarios findByUsername(String username);

}
