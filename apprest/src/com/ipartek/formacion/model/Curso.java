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
	
	}

	public Curso(int id, String titulo, String imagen, int precio, Persona profesor) {		
		super();
		this.id = 0;
		this.titulo = "";
		this.imagen = "";
		this.precio = 0;
		this.profesor = new Persona();
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
		result = prime * result + precio;
		result = prime * result + ((profesor == null) ? 0 : profesor.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (id != other.id)
			return false;
		if (imagen == null) {
			if (other.imagen != null)
				return false;
		} else if (!imagen.equals(other.imagen))
			return false;
		if (precio != other.precio)
			return false;
		if (profesor == null) {
			if (other.profesor != null)
				return false;
		} else if (!profesor.equals(other.profesor))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	

	

}
