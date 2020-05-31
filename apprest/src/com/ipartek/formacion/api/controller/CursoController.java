package com.ipartek.formacion.api.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ipartek.formacion.dao.impl.CursoDao;
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
	@GET
	@Path("/{id: \\d+}")
	public Response getById(@PathParam("id") int id) throws Exception {
		
		LOGGER.info("Obtener el curso por id");
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();

		try {
			
			Curso curso = cursoDAO.getById(id);

			if (curso == null) {
				
				response = Response.status(Status.NOT_FOUND).build();
				LOGGER.warning("Error: Curso no encontrado, con id " + id);
				throw new Exception("Error: Curso no encontrado");

			} else {
				response = Response.status(Status.OK).entity(curso).build();
				LOGGER.info("Encontrado curso por id: " + curso);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.warning("No se ha podido obtener el curso con id " + id);
		}
		return response;
	}
	
	@PUT
	@Path("/{id: \\d+}")
	public Response update(@PathParam("id") int id, Curso curso) {
		
		LOGGER.info("Update PathParam id (" + id + ", " + curso + ")");
		
		Response response = Response.status(Status.NOT_FOUND).entity(curso).build();
		
		Set<ConstraintViolation<Curso>> violations = validator.validate(curso);
		ArrayList<String> errores = new ArrayList<String>();

		if (violations.isEmpty()) {

			try {
				
				cursoDAO.update(curso);
				response = Response.status(Status.OK).entity(curso).build();
				LOGGER.info("Curso modificado");
				
			} catch (Exception e) {
				
				errores.add("Error de nombre");
				LOGGER.warning("Error de nombre del curso");
				
				response = Response.status(Status.CONFLICT).entity(errores).build();
				
			}
		} else {
			
			for (ConstraintViolation<Curso> violation : violations) {
				errores.add(violation.getPropertyPath() + ": " + violation.getMessage());
			}
			
			LOGGER.warning("No se cumplen las validaciones para modificar el curso: " + errores);
			response = Response.status(Status.BAD_REQUEST).entity(errores).build();
		}

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
