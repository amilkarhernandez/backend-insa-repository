package com.insa.backend.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.insa.backend.models.Integrante;


public interface IIntegrantesService {
	
	public List<Integrante> finAll();

	public Integrante findById(Long id);

	public Integrante save(Integrante integrante);

	public void delete(Long id);
	
	public Long count();

	boolean saveDataUploafile(MultipartFile file);

}
