package com.insa.backend.services;

import java.util.List;

import com.insa.backend.models.Usuarios;

public interface IUserService {
	
	public List<Usuarios> finAll();

	public Usuarios findById(Long id);

	public Usuarios save(Usuarios usuario);

	public void delete(Long id);

}
