package com.insa.backend.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "integrantes")
public class Integrante implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "no puede ser vacio")
	private String nombre;
	
	@NotNull(message = "no puede ser vacio")
	private String apellido;
	
	@NotNull(message = "no puede ser vacio")
	private String email;
	
	@NotNull(message = "no puede ser vacio")
	private String genero;
	
	@NotNull(message = "no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_proyecto")
	@JsonIgnoreProperties({"integrante", "hibernateLazyInitializer", "handler" })
	private Proyecto proyecto;
	
	

	public Integrante() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integrante(@NotNull(message = "no puede ser vacio") String nombre,
			@NotNull(message = "no puede ser vacio") String apellido,
			@NotNull(message = "no puede ser vacio") String email,
			@NotNull(message = "no puede ser vacio") String genero,
			@NotNull(message = "no puede ser vacio") Proyecto proyecto) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.genero = genero;
		this.proyecto = proyecto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
