package com.insa.backend.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "proyectos")
public class Proyecto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cod_proyecto")
	@NotEmpty(message = "No puede Estar vacio")
	private String codigoProyecto;

	@Column(name = "proj_nombre")
	@NotEmpty(message = "No puede Estar vacio")
	private String projNombre;

	@Column(name = "proj_desc", length = 2000)
	@NotEmpty(message = "No puede Estar vacio")
	private String projDescripcion;

	@Column(name = "horas_tot")
	private Long totalHoras;

	@Column(name = "costo_tot")
	@NotNull(message = "Please provide a price")
    @DecimalMin("1.00")
	private Double costoTotal;
	
	@JsonIgnoreProperties({"proyecto","hibernateLazyInitializer", "handler"})
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
	private List<Integrante> integrante;
	
	public Proyecto() {
		integrante = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoProyecto() {
		return codigoProyecto;
	}

	public void setCodigoProyecto(String codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	public String getProjNombre() {
		return projNombre;
	}

	public void setProjNombre(String projNombre) {
		this.projNombre = projNombre;
	}

	public String getProjDescripcion() {
		return projDescripcion;
	}

	public void setProjDescripcion(String projDescripcion) {
		this.projDescripcion = projDescripcion;
	}

	public Long getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(Long totalHoras) {
		this.totalHoras = totalHoras;
	}

	public Double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}
	

	
	public List<Integrante> getIntegrante() {
		return integrante;
	}

	public void setIntegrante(List<Integrante> integrante) {
		this.integrante = integrante;
	}
	
	



	public Proyecto(String codigoProyecto, String projNombre, String projDescripcion, Long totalHoras,
			Double costoTotal) {
		super();
		this.codigoProyecto = codigoProyecto;
		this.projNombre = projNombre;
		this.projDescripcion = projDescripcion;
		this.totalHoras = totalHoras;
		this.costoTotal = costoTotal;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
