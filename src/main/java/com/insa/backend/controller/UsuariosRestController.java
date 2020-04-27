package com.insa.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insa.backend.models.Usuarios;
import com.insa.backend.services.IUserService;


@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuariosRestController {
	
	@Autowired
	private IUserService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// LISTADO DE USUARIOS
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/usuarios")
	public List<Usuarios> index() {
		return usuarioService.finAll();
	}
	
	// BUSCAR REGISTRO POR ID
		@Secured({ "ROLE_ADMIN" })
		@GetMapping("/usuarios/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {

			Usuarios usuario = null;
			Map<String, Object> response = new HashMap<>();

			try {
				usuario = usuarioService.findById(id);
			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Realizar la Consulta en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (usuario == null) {
				response.put("mensaje",
						"El Usuario con el Id:".concat(id.toString()).concat(" no existe en la base de datos"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Usuarios>(usuario, HttpStatus.OK);
		}

		// CREAR UN NUEVO REGISTRO
		@Secured({ "ROLE_ADMIN" })
		@PostMapping("/usuarios")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<?> create(@Valid @RequestBody Usuarios usuario, BindingResult result) {
			Usuarios usuarioNew = null;
			Map<String, Object> response = new HashMap<>();

			if (result.hasErrors()) {

				List<String> errors = result.getFieldErrors().stream()
						.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());

				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			try {
				String passwordBcrypt = passwordEncoder.encode(usuario.getPassword());
				// Volvemos asignar el password encriptado, antes de guardar:
				usuario.setPassword(passwordBcrypt);
				usuario.setEnabled(true);
				usuarioNew = usuarioService.save(usuario);
			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Realizar el Insert en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.put("mensaje", "El Usuario ha sido creado con Exito");
			response.put("usuario", usuarioNew);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

		// ACTUALIZAR UN REGISTRO
		@Secured({ "ROLE_ADMIN" })
		@PutMapping("/usuarios/{id}")
		public ResponseEntity<?> update(@Valid @RequestBody Usuarios usuario, BindingResult result, @PathVariable Long id) {

			Usuarios usuarioActual = usuarioService.findById(id);
			Usuarios usuarioUpdated = null;

			Map<String, Object> response = new HashMap<>();

			if (result.hasErrors()) {
				/*
				 * List<String> errors = new ArrayList<>(); for(FieldError err :
				 * result.getFieldErrors()) { errors.add("El campo '" + err.getField() + "' "
				 * +err.getDefaultMessage()); }
				 */
				List<String> errors = result.getFieldErrors().stream()
						.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());

				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			if (usuarioActual == null) {
				response.put("mensaje", "Error: no se puede Editar, El Usuario con el Id:".concat(id.toString())
						.concat(" no existe en la base de datos"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			try {
				usuarioActual.setCedula(usuario.getCedula());
				usuarioActual.setNombres(usuario.getNombres());
				usuarioActual.setApellidos(usuario.getApellidos());
				usuarioActual.setTelefono(usuario.getTelefono());
				usuarioActual.setEnabled(usuario.getEnabled());
				usuarioActual.setUsername(usuario.getUsername());
				String passwordBcrypt = passwordEncoder.encode(usuario.getPassword());
				// Volvemos asignar el password encriptado, antes de guardar:
				usuarioActual.setPassword(passwordBcrypt);
				usuarioActual.setRoles(usuario.getRoles());

				usuarioUpdated = usuarioService.save(usuarioActual);

			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Actualizar en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.put("mensaje", "El usuario ha sido Actualizado con Exito");
			response.put("acudiente", usuarioUpdated);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

		// ELIMINAR UN REGISTRO
		@Secured({ "ROLE_ADMIN" })
		@DeleteMapping("/usuarios/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			Map<String, Object> response = new HashMap<>();

			try {
				usuarioService.delete(id);
			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Eliminar en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El Usuario ha sido Eliminado con Exito");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}

}
