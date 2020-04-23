package com.ipartek.formacion.model;



public class Noticia {

	private int id;
	private String titulo;
	private String fecha;
	private String contenido;
	
	public Noticia() {
		super();
		this.id = 0;
		this.titulo = "";		
		this.fecha = "";
		this.contenido = "";
		
	}
	
	public Noticia(int id, String titulo, String fecha, String contenido) {
		this();
		this.id = id;
		this.titulo = titulo;
		this.fecha = fecha;
		this.contenido = contenido;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Override
	public String toString() {
		return "Noticia [id=" + id + ", titulo=" + titulo + ", fecha=" + fecha + ", contenido=" + contenido + "]";
	}
	
	
	
	}
