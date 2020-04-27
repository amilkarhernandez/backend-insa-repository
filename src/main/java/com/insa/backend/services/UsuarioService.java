package com.insa.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insa.backend.dao.IUsuarioDao;
import com.insa.backend.models.Usuarios;

@Service
public class UsuarioService implements UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Se obtiene el usuario que inicia seccion
		Usuarios usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en el Login: No existe el Usuario '"+usuario.getUsername()+"' en el sistea");
			throw new UsernameNotFoundException("Error en el Login: No existe el Usuario '"+usuario.getUsername()+"' en el sistema");
		}
		
		//Obtenemos los roles
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.collect(Collectors.toList());
		
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
