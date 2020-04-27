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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.insa.backend.models.Integrante;
import com.insa.backend.services.IIntegrantesService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class IntegrantesController {

	@Autowired
	private IIntegrantesService integranteService;

	// Listado de Integrantes
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/integrantes")
	public List<Integrante> index() {
		return integranteService.finAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/integrantes/totalintegrantes")
	public Long totalintegrante() {
		return integranteService.count();
	}

	// buscar un integrante por ID
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/integrantes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Integrante integrante = null;
		Map<String, Object> response = new HashMap<>();

		try {
			integrante = integranteService.findById(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al Realizar la Consulta en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (integrante == null) {
			response.put("mensaje",
					"El Integrante con el Id:".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Integrante>(integrante, HttpStatus.OK);
	}

	// Metodo para Guardar un Integrante
	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/integrantes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Integrante integrante, BindingResult result) {
		Integrante integranteNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			integranteNew = integranteService.save(integrante);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al Realizar el Insert en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El Integrante ha sido creado con Exito");
		response.put("integrante", integranteNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/integrantes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Integrante integrante, BindingResult result,
			@PathVariable Long id) {

		Integrante integranteActual = integranteService.findById(id);
		Integrante integranteUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (integranteActual == null) {
			response.put("mensaje", "Error: no se puede Editar, El Integrante con el Id:".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			integranteActual.setNombre(integrante.getNombre());
			integranteActual.setApellido(integrante.getApellido());
			integranteActual.setEmail(integrante.getEmail());
			integranteActual.setGenero(integrante.getGenero());
			integranteActual.setProyecto(integrante.getProyecto());

			integranteUpdated = integranteService.save(integranteActual);

		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al Actualizar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El Integrante ha sido Actualizado con Exito");
		response.put("integrante", integranteUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// ELIMINAR UN REGISTRO
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/integrantes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			integranteService.delete(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al Eliminar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Integrante ha sido Eliminado con Exito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	// Subir un Excel o u csv
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/integrantes/subir")
	public ResponseEntity<?> archivo(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		Map<String, Object> response = new HashMap<>();
		boolean idFlag = integranteService.saveDataUploafile(file);

		if (idFlag) {
			redirectAttributes.addFlashAttribute("success", "Archivo Subido");
			response.put("mensaje", "Se Cargaron los Datos Correctamente");
		} else {
			redirectAttributes.addFlashAttribute("error", "Ocurrio un Error");
			response.put("mensaje", "Ocurrio un Error, Intente nuevamente");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
