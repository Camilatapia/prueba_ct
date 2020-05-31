package com.ipartek.formacion.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.ipartek.formacion.model.Curso;
import com.ipartek.formacion.model.Persona;
import com.ipartek.formacion.model.Rol;
import com.ipartek.formacion.dao.ConnectionManager;
import com.ipartek.formacion.dao.IDAO;
import com.ipartek.formacion.dao.IPersonaDAO;


public class PersonaDao implements IPersonaDAO {

	private static final Logger LOGGER = Logger.getLogger(PersonaDao.class.getCanonicalName());
	
	private static PersonaDao INSTANCE = null;
	
	private static String SQL_GET_ALL   = "SELECT \n" + 
			"	r.id as rol_id,\n" +
			"	r.nombre as rol_nombre,\n" +
			"	p.id as persona_id,\n" + 
			"	p.nombre as persona_nombre,\n" + 
			"	p.avatar as persona_avatar,\n" + 
			"	p.sexo as persona_sexo,\n" + 
			"	p.id_rol as persona_rol,\n" +
			"	c.id as curso_id,\n" + 
			"	c.titulo as curso_nombre,\n" + 
			"	c.precio as curso_precio,\n" + 
			"	c.imagen  as curso_imagen\n" + 
			" FROM rol r LEFT JOIN persona p ON r.id=p.id_rol\n" +
			" LEFT JOIN curso_comprado cc ON p.id = cc.id_persona\n" + 
			" LEFT JOIN curso c ON cc.id_curso = c.id LIMIT 500;  ";


private static String SQL_GET_BY_ID = "SELECT \n" + 
			"	p.id as persona_id,\n" + 
			"	p.nombre as persona_nombre,\n" + 
			"	p.avatar as persona_avatar,\n" + 
			"	p.sexo as persona_sexo,\n" + 
			"	p.id_rol as persona_rol,\n" +
			"	c.id as curso_id,\n" + 
			"	c.titulo as curso_nombre,\n" + 
			"	c.precio as curso_precio,\n" + 
			"	c.imagen  as curso_imagen\n" + 
			" 	FROM (persona p LEFT JOIN curso_comprado cc ON p.id = cc.id_persona)\n" + 
			"   LEFT JOIN curso c ON cc.id_curso = c.id WHERE p.id = ? ;   ";
	private static String SQL_GET_ROL ="SELECT \n" + 
			"	r.id as rol_id,\n" +
			"	r.nombre as rol_nombre,\n" +
			"	p.id as persona_id,\n" + 
			"	p.nombre as persona_nombre,\n" + 
			"	p.avatar as persona_avatar,\n" + 
			"	p.sexo as persona_sexo,\n" + 
			"	p.id_rol as persona_rol,\n" +
			"	c.id as curso_id,\n" + 
			"	c.titulo as curso_nombre,\n" + 
			"	c.precio as curso_precio,\n" + 
			"	c.imagen  as curso_imagen\n" + 
			" 	FROM rol r LEFT JOIN persona p ON r.id=p.id_rol\n" +
			" 	LEFT JOIN curso_comprado cc ON p.id = cc.id_persona\n" + 
			" 	LEFT JOIN curso c ON cc.id_curso = c.id WHERE p.id_rol = 2; ";
	private static String SQL_GET_ALUMNOS = "		SELECT \r\n" + 
			"	p.id as persona_id,\r\n" + 
			"	p.nombre as persona_nombre, \r\n" + 
			"	p.avatar as persona_avatar, \r\n" + 
			"	p.sexo as persona_sexo, \r\n" + 
			"	p.id_rol as persona_rol,\r\n" + 
			"	c.id as curso_id,\r\n" + 
			"	c.titulo as curso_nombre, \r\n" + 
			"	c.precio as curso_precio, \r\n" + 
			"	c.imagen  as curso_imagen,\r\n" + 
			"	c.id_profesor  as curso_profesor,\r\n" +
			"	r.id as rol_id,\r\n" + 
			"	r.nombre as rol_nombre,\r\n" + 
			"	pr.nombre AS profesor_nombre\r\n" + 
			"	FROM persona p LEFT JOIN curso_comprado cc ON p.id=cc.id_persona\r\n" + 
			"	LEFT JOIN curso c ON c.id = cc.id_curso\r\n" + 
			"	LEFT JOIN rol r ON p.id_rol = r.id \r\n" + 
			"	LEFT JOIN persona pr ON c.id_profesor = pr.id WHERE p.id_rol = 1; ";
	private static String SQL_DELETE    = "DELETE FROM persona WHERE id = ?; ";
	private static String SQL_INSERT    = "INSERT INTO persona (nombre, avatar, sexo, id_rol) VALUES ( ?, ?, ?, ? ); ";
	private static String SQL_UPDATE    = "UPDATE persona SET nombre = ?, avatar = ?,  sexo = ?, id_rol = ? WHERE id = ?; ";
	private static String SQL_ASIGNAR_CURSO    = "INSERT INTO curso_comprado (id_persona, id_curso) VALUES ( ?, ?); ";
	private static String SQL_ELIMINAR_CURSO    = "DELETE FROM curso_comprado WHERE id_persona = ? AND id_curso = ?;  ";
	
	
	private PersonaDao() {
		super();		
	}
	
	public synchronized static PersonaDao getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new PersonaDao();
        }
        return INSTANCE;
    }
	

	
	@Override
	public List<Persona> getAll() {
		LOGGER.info("getAll");
		
		ArrayList<Persona> registros = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();

		) {

			LOGGER.info(pst.toString());
			
			while( rs.next() ) {				
				 mapper(rs, hmPersonas );			
			}
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

				// convert hashmap to array
				registros = new ArrayList<Persona> ( hmPersonas.values() );
				return registros;
	}
	
	
	@Override
	public List<Persona> getAllProfesores() throws Exception{
		
		LOGGER.info("getAllProfesores(): PersonaDAO");
		
		ArrayList<Persona> profesores = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmProfesor = new HashMap<Integer, Persona>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement s = con.prepareCall(SQL_GET_ROL);
				
				ResultSet rs = s.executeQuery();

			){
			LOGGER.info(s.toString());

			while (rs.next()) {
				profesorMapper(rs, hmProfesor);
				
					}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			LOGGER.info("getAllProfesores() ");
			throw new SQLException("Error al obtener la lista de los Profesores");
			
		}
		
		profesores = new ArrayList<Persona>(hmProfesor.values());
		return profesores;
	}
	//Listado profesores
	
	@Override
	public List<Persona> getAllAlumnos() {
		
		LOGGER.info("getAllAlumnos()");
		ArrayList<Persona> alumnos = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmAlumno = new HashMap<Integer, Persona>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement s = con.prepareCall(SQL_GET_ALUMNOS);
				
				ResultSet rs = s.executeQuery();

			){
			LOGGER.info(s.toString());

			while (rs.next()) {
			
				alumnosMapper(rs, hmAlumno);

			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			LOGGER.info("Error al cargar lista de alumnos getAllAlumnos()");
			
		}
		
		alumnos = new ArrayList<Persona>(hmAlumno.values());
		return alumnos;
	}//listado alumnos
	
	@Override
	public Persona getById(int id) throws Exception {
		Persona registro = null;
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID);
		) {

			pst.setInt(1, id);
			LOGGER.info(pst.toString());
			
			try( ResultSet rs = pst.executeQuery() ){
			
				HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();
				if( rs.next() ) {					
					registro = mapper(rs, hmPersonas);
				}else {
					throw new Exception("Registro no encontrado para id = " + id);
				}
			}
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return registro;
		}
	
	@Override
	public List<Persona> getAllByRol(Rol rol) throws Exception {
		LOGGER.info("getByRol");		
		ArrayList<Persona> registros = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ROL);
				) {
			LOGGER.info(pst.toString());
			
			pst.setInt(1, rol.getId());
			
			try( ResultSet rs = pst.executeQuery(); ){
				
				LOGGER.info(pst.toString());			
				while( rs.next() ) {
					registros.add(mapper(rs, hmPersonas));
									
				}	
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		registros = new ArrayList<Persona> ( hmPersonas.values() );
		return registros;
	}
	

	@Override
	public Persona delete(int id) throws Exception, SQLException {
		Persona registro = null;
		
		//recuperar persona antes de eliminar
		registro = getById(id);
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE);) {

			pst.setInt(1, id);
			LOGGER.info(pst.toString());
			
			//eliminamos la persona
			int affetedRows = pst.executeUpdate();	
			if (affetedRows != 1) {
				throw new Exception("No se puede eliminar registro " + id);
			}
			
		} catch (SQLException e) {

			throw new SQLException("No se puede eliminar registro " + e.getMessage() );
		}

		return registro;
	}

	@Override
	public Persona insert(Persona pojo) throws Exception, SQLException {
		
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);) {

			pst.setString(1, pojo.getNombre() );
			pst.setString(2, pojo.getAvatar() );
			pst.setString(3, pojo.getSexo() );
			pst.setObject(4, pojo.getRol().getId());
			//pst.setInt(4,pojo.getRol());
			LOGGER.info(pst.toString());
			
			//eliminamos la persona
			int affetedRows = pst.executeUpdate();	
			if (affetedRows == 1) {
				//recuperar ID generado automaticamente
				ResultSet rs = pst.getGeneratedKeys();
				if( rs.next()) {
					pojo.setId( rs.getInt(1) );
				}	
				
			}else {
				throw new Exception("No se puede crear registro " + pojo);
			}
			
		} catch (SQLException e) {

			throw new Exception("No se puede crear registro " + e.getMessage() );
		}

		return pojo;
	}

	@Override
	public Persona update(Persona pojo) throws Exception, SQLException {
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE);) {

			pst.setString(1, pojo.getNombre() );
			pst.setString(2, pojo.getAvatar() );
			pst.setString(3, pojo.getSexo() );
			pst.setObject(4, pojo.getRol().getId());
			//pst.setInt(4,pojo.getRol());
			pst.setInt(5, pojo.getId() );
			LOGGER.info(pst.toString());
			
			//eliminamos la persona
			int affetedRows = pst.executeUpdate();	
			if (affetedRows != 1) {				
				throw new Exception("No se puede modificar registro " + pojo);
			}
			
		} catch (SQLException e) {

			throw new Exception("No se puede modificar registro " + e.getMessage() );
		}

		return pojo;
	}
	
	public boolean asignarCurso( int idPersona, int idCurso ) throws Exception, SQLException {
		boolean resul = false;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_ASIGNAR_CURSO);
		) {

			pst.setInt(1, idPersona);
			pst.setInt(2, idCurso);
			LOGGER.info(pst.toString());
			
			//eliminamos la persona
			int affetedRows = pst.executeUpdate();	
			if (affetedRows == 1) {
				resul = true;
			}else {
				resul = false;	
				throw new SQLException("Error!");
			}
		}
		
		return resul;
	}
	
	public boolean eliminarCurso( int idPersona, int idCurso ) throws Exception, SQLException {
		boolean resul = false;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_ELIMINAR_CURSO);
		) {

			pst.setInt(1, idPersona);
			pst.setInt(2, idCurso);
			LOGGER.info(pst.toString());
			
			//eliminamos la persona
			int affetedRows = pst.executeUpdate();	
			if (affetedRows == 1) {
				resul = true;
			}else {
				throw new Exception("No se encontrado registro id_persona =" + idPersona + " id_curso=" + idCurso );		
			}
		}
		
		return resul;
	}


	private Persona mapper( ResultSet rs, HashMap<Integer, Persona> hm ) throws SQLException {
		
		
		int key = rs.getInt("persona_id"); 
		
		Persona p = hm.get(key);
		Rol rol;
		rol = new Rol(rs.getInt("rol_id"), rs.getString("rol_nombre"));			
		
		// si no existe en el hm se crea
		if ( p == null ) {
		p = new Persona();
		p.setId( key  );
		p.setNombre( rs.getString("persona_nombre"));
		p.setAvatar( rs.getString("persona_avatar"));
		p.setSexo( rs.getString("persona_sexo"));
		p.setRol(rol);
		
		
		//p.setRol(rs.getInt("persona_rol"));
		}
			
		// se añade el curso
		int idCurso = rs.getInt("curso_id");
		if ( idCurso != 0) {
			//TODO meter el profesor del curso. REVISAR
			/*Persona profesor = new Persona(rs.getInt("persona_id"),rs.getString("persona_nombre"), 
					rs.getString("persona_avatar"),rs.getString("persona_sexo"),rol);*/
			Curso c = new Curso();
			c.setId(idCurso);
			c.setTitulo(rs.getString("curso_nombre"));
			c.setPrecio( rs.getInt("curso_precio"));
			c.setImagen(rs.getString("curso_imagen"));	
			//c.setProfesor(profesor);
			p.getCursos().add(c);
			
					
		}	
		
		//actualizar hashmap
		hm.put(key, p);
		return p;
		
		
	}
	
private Persona alumnosMapper(ResultSet rs, HashMap<Integer,Persona> hmAlumno) throws SQLException {
		
		int key = rs.getInt("persona_id");
		Persona alumno = hmAlumno.get(key);
		
		//Se añade el Rol
		Rol rol = new Rol();
		rol.setId(rs.getInt("rol_id"));
		rol.setNombre(rs.getString("rol_nombre"));
		
		//Si no existe en el HashMap se crea
		if( alumno == null) {
			
			alumno = new Persona();
			alumno.setId(key);
			alumno.setNombre( rs.getString("persona_nombre"));
			alumno.setAvatar( rs.getString("persona_avatar"));
			alumno.setSexo( rs.getString("persona_sexo"));
			alumno.setRol(rol);
			
		}
		
		//Se añade el Curso
		int idCurso = rs.getInt("curso_id");
		
		if ( idCurso != 0) {
			Curso c = new Curso();
			c.setId(idCurso);
			c.setTitulo(rs.getString("curso_nombre"));
			c.setPrecio( rs.getInt("curso_precio"));
			c.setImagen(rs.getString("curso_imagen"));	
			
			//Se añade el Profesor
			Persona profesor = new Persona();
			//profesor.setId(rs.getInt("persona_id"));
			profesor.setNombre(rs.getString("profesor_nombre"));
			
			c.setProfesor(profesor);
			alumno.getCursos().add(c);
		}	
		
		//Actualizar HashMap
		hmAlumno.put(key, alumno);
		return alumno;
	}

	private Persona profesorMapper(ResultSet rs, HashMap<Integer, Persona> hmProfesor) throws SQLException {
		
		int key = rs.getInt("persona_id");
		Persona profesor = hmProfesor.get(key);
		
		//Se añade el Rol
		Rol rol = new Rol();
		rol.setId(rs.getInt("rol_id"));
		rol.setNombre(rs.getString("rol_nombre"));
		
		if(profesor == null) {
			
			profesor = new Persona();
			
			profesor.setId(key);
			profesor.setNombre(rs.getString("persona_nombre"));
			profesor.setAvatar(rs.getString("persona_avatar"));
			profesor.setSexo(rs.getString("persona_sexo"));
			profesor.setRol(rol);
			
		}
		
		//Se añade el Curso
		int idCurso = rs.getInt("curso_id");
				
		if ( idCurso != 0) {
			
			Curso c = new Curso();
			
			c.setId(idCurso);
			c.setTitulo(rs.getString("curso_nombre"));
			c.setImagen(rs.getString("curso_imagen"));
			c.setPrecio( rs.getInt("curso_precio"));
			
			
			//Se añade el Profesor
			Persona p = new Persona();
			
			p.setId(rs.getInt("persona_id"));
			p.setNombre(rs.getString("persona_nombre"));
			p.setAvatar(rs.getString("persona_avatar"));
			p.setSexo(rs.getString("persona_sexo"));
			p.setRol(rol);
			
			c.setProfesor(p);
			profesor.getCursos().add(c);
			
			}
		
		//Actualizar HashMap
		hmProfesor.put(key, profesor);
		
		return profesor;
	}
		
	}

