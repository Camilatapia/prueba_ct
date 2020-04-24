package com.ipartek.formacion.model;

import java.util.ArrayList;

public class Rol {

	private int id;
	private String nombre;
	
	public Rol() {
		this.id = 0;
		this.nombre = "";		
		
	}
	
	public Rol(int id, String nombre) {		
		this();
		this.id = id;
		this.nombre = nombre;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
	
}
