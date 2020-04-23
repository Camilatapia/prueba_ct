package com.ipartek.formacion.api.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.ipartek.formacion.dao.NoticiaDao;
import com.ipartek.formacion.model.Noticia;


@Path("/noticias")
@Produces("application/json")
@Consumes("application/json")
public class NoticiaController {

	private static final Logger LOGGER = Logger.getLogger(CursoController.class.getCanonicalName());
	private static NoticiaDao noticiaDAO = NoticiaDao.getInstance();

	
	@Context
	private ServletContext context;
	
	public NoticiaController() {
		super();		
	}

	@GET
	public ArrayList<Noticia> getAll() {
		LOGGER.info("getAll");		
		// return noticias;
		ArrayList<Noticia> registros = (ArrayList<Noticia>) noticiaDAO.getAll(); 
		return registros;
	}
	
}
