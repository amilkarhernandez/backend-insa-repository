package com.insa.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insa.backend.dao.IUsuarioDao;
import com.insa.backend.models.Usuarios;

@Service
public class UserServiceimp implements 	IUserService{
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	public List<Usuarios> finAll() {
		return (List<Usuarios>) usuarioDao.findAll();
	}

	@Override
	public Usuarios findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuarios save(Usuarios usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}
	
	

}
