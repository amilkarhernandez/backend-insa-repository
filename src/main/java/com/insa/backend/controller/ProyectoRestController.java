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
import com.insa.backend.models.Proyecto;
import com.insa.backend.services.IIntegrantesService;
import com.insa.backend.services.IProyectoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProyectoRestController {
	
	@Autowired
	private IProyectoService proyectoService;
	
	@Autowired
	private IIntegrantesService integranteService;
	
		// Listado de Proyectos
		@Secured({ "ROLE_ADMIN", "ROLE_USER" })
		@GetMapping("/proyectos")
		public List<Proyecto> index() {
			return proyectoService.finAll();
		}
		
		@Secured({ "ROLE_ADMIN" , "ROLE_USER"})
		@GetMapping("/proyectos/totalproyectos")
		public Long total() {
			return proyectoService.count();
		}

		// buscar un Proyecto por ID
		@Secured({ "ROLE_ADMIN", "ROLE_USER" })
		@GetMapping("/proyectos/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {

			Proyecto proyecto = null;
			Map<String, Object> response = new HashMap<>();

			try {
				proyecto = proyectoService.findById(id);
			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Realizar la Consulta en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (proyecto == null) {
				response.put("mensaje",
						"El Proyecto con el Id:".concat(id.toString()).concat(" no existe en la base de datos"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
		}
		
		//Metodo para Guardar un Proyecto
		@Secured({"ROLE_ADMIN", "ROLE_USER"})
		@PostMapping("/proyectos")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<?> create(@Valid @RequestBody Proyecto proyecto, BindingResult result) {
			Proyecto proyectoNew = null;
			Proyecto pro = proyecto;
			List<Integrante> integ = pro.getIntegrante();
			Proyecto prod = null;
			
			pro.setIntegrante(null);
			
			Map<String, Object> response = new HashMap<>();

			if (result.hasErrors()) {

				List<String> errors = result.getFieldErrors().stream()
						.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());

				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			try {

				proyectoNew = proyectoService.save(pro);
				prod = proyectoService.findById(proyectoNew.getId());
				//Guardar Integrante
				for(Integrante i : integ) {
					i.setProyecto(prod);
					integranteService.save(i);
				}
				
			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Realizar el Insert en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.put("mensaje", "El Proyecto ha sido creado con Exito");
			response.put("proyecto", proyectoNew);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		
		
		//Editar un Proyecto
		@Secured({"ROLE_ADMIN"})
		@PutMapping("/proyectos/{id}")
		public ResponseEntity<?> update(@Valid @RequestBody Proyecto proyecto, BindingResult result,
				@PathVariable Long id) {

			Proyecto proyectoActual = proyectoService.findById(id);
			Proyecto proyectoUpdated = null;

			Map<String, Object> response = new HashMap<>();

			if (result.hasErrors()) {

				List<String> errors = result.getFieldErrors().stream()
						.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());

				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			if (proyectoActual == null) {
				response.put("mensaje", "Error: no se puede Editar, El Proyecto con el Id:".concat(id.toString())
						.concat(" no existe en la base de datos"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			try {
				proyectoActual.setCodigoProyecto(proyecto.getCodigoProyecto());
				proyectoActual.setProjNombre(proyecto.getProjNombre());
				proyectoActual.setProjDescripcion(proyecto.getProjDescripcion());
				proyectoActual.setTotalHoras(proyecto.getTotalHoras());
				proyectoActual.setCostoTotal(proyecto.getCostoTotal());

				proyectoUpdated = proyectoService.save(proyectoActual);

			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Actualizar en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.put("mensaje", "El Proyecto ha sido Actualizado con Exito");
			response.put("proyecto", proyectoUpdated);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

		// ELIMINAR UN Proyecto
		@Secured({"ROLE_ADMIN"})
		@DeleteMapping("/proyectos/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			Map<String, Object> response = new HashMap<>();

			try {
				proyectoService.delete(id);
			} catch (DataAccessException e) {
				// TODO: handle exception
				response.put("mensaje", "Error al Eliminar en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El Proyecto ha sido Eliminado con Exito");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}
		
		//Subir un Excel o u csv
		@Secured({"ROLE_ADMIN"})
		@PostMapping("/proyectos/subir")
		public ResponseEntity<?> archivo(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
			Map<String, Object> response = new HashMap<>();
			boolean idFlag = proyectoService.saveDataUploafile(file);
			
			if(idFlag) {
				redirectAttributes.addFlashAttribute("success", "Archivo Subido");
				response.put("mensaje", "Se Cargaron los Datos Correctamente");
			}else {
				redirectAttributes.addFlashAttribute("error", "Ocurrio un Error");
				response.put("mensaje", "Ocurrio un Error, Intente nuevamente");
			}
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		}
		
		

}
