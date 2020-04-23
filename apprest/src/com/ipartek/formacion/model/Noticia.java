package com.ipartek.formacion.model;

public class Ccomprado {

	private int id_persona;
	private int id_curso;
	
	public Ccomprado() {
		super();
		this.id_persona = 0;
		this.id_curso = 0;	
	}
		

	public Ccomprado(int id_persona, int id_curso) {		
		this();
		this.id_persona = id_persona;
		this.id_curso = id_curso;
	
	}


	public int getId_persona() {
		return id_persona;
	}


	public void setId_persona(int id_persona) {
		this.id_persona = id_persona;
	}


	public int getId_curso() {
		return id_curso;
	}


	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}


	@Override
	public String toString() {
		return "Ccomprado [id_persona=" + id_persona + ", id_curso=" + id_curso + "]";
	}

	
}
