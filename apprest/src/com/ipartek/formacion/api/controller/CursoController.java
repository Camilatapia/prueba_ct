package com.ipartek.formacion.api.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ipartek.formacion.dao.CursoDao;
import com.ipartek.formacion.model.Curso;
import com.ipartek.formacion.model.Persona;

@Path("/cursos")
@Produces("application/json")
@Consumes("application/json")
public class CursoController {

	private static final Logger LOGGER = Logger.getLogger(CursoController.class.getCanonicalName());
	private static CursoDao cursoDAO = CursoDao.getInstance();

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	
	@Context
	private ServletContext context;
	
	public CursoController() {
		super();		
	}

	@GET
	public Response getAll( @QueryParam("filtro") String filtro ) {
		
		LOGGER.info("getAll " + filtro);		
		ArrayList<Curso> registros = new ArrayList<Curso>(); 
		
		
		if ( filtro != null && !"".equals(filtro.trim())) {
			registros = (ArrayList<Curso>) cursoDAO.getAllLikeNombre(filtro);
			
		}else {
			registros = (ArrayList<Curso>) cursoDAO.getAll();			
			
		}
		
		Response response = Response.status(Status.OK).entity(registros).build();
		
		return response;
	}
	
	
	@DELETE
	@Path("/{id: \\d+}")
	public Response eliminar(@PathParam("id") int id) {
		LOGGER.info("eliminar(" + id + ")");

		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();
		Curso curso = null;
		
		try {
			curso = cursoDAO.delete(id);
			response = Response.status(Status.OK).entity(curso).build();
			
		}catch (SQLException e) {
			response = Response.status(Status.CONFLICT).entity(curso).build();
			
		}catch (Exception e) {
			response = Response.status(Status.NOT_FOUND).entity(curso).build();
		}
		return response;
	}
}
