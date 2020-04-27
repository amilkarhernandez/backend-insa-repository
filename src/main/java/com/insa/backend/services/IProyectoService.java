package com.insa.backend.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.insa.backend.models.Proyecto;


public interface IProyectoService {
	
	public List<Proyecto> finAll();

	public Proyecto findById(Long id);

	public Proyecto save(Proyecto proyecto);

	public void delete(Long id);
	
	public Long count();

	public boolean saveDataUploafile(MultipartFile file);

	Proyecto findByNombre(String cod);

}
