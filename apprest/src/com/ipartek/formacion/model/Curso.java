package com.ipartek.formacion.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Curso {
	
	private int id;
	
	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 2, max = 50, message = "El nombre debe tener minimo 2 caracteres y maximo 50")
	private String titulo;
	
	@NotEmpty
	private String imagen;
	
	private int precio;
	
	@NotNull
	@Valid // fuerza la validacion de Persona
	private Persona profesor;
	
	public Curso() {
		super();
		this.id = 0;
		this.titulo = "";		
		this.imagen = "imagen1.png";
		this.precio = 0;
		this.profesor = new Persona();
	}

	public Curso(int id, String titulo, String imagen, int precio, Persona profesor) {		
		this();
		this.id = id;
		this.titulo = titulo;
		this.imagen = imagen;
		this.precio = precio;
		this.profesor = profesor;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Persona getProfesor() {
		return profesor;
	}

	public void setProfesor(Persona profesor) {
		this.profesor = profesor;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", titulo=" + titulo + ", imagen=" + imagen + ", precio=" + precio + ", profesor="
				+ profesor + "]";
	}

	

	

}
