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
			" FROM (persona p LEFT JOIN curso_comprado cc ON p.id = cc.id_persona)\n" + 
			"     LEFT JOIN curso c ON cc.id_curso = c.id WHERE p.id = ? ;   ";
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
			" FROM rol r LEFT JOIN persona p ON r.id=p.id_rol\n" +
			" LEFT JOIN curso_comprado cc ON p.id = cc.id_persona\n" + 
			" LEFT JOIN curso c ON cc.id_curso = c.id WHERE p.id_rol = 2; ";

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
			//CUIDADO los simobolos % % no se pueden porner en la SQL, siempre en el PST
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
			pst.setInt(4, pojo.getRol().getId());
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
			pst.setInt(4, pojo.getRol().getId());
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
		rol = new Rol(rs.getInt("persona_rol"), rs.getString("rol_nombre"));			
		
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
			Persona profesor = new Persona(rs.getInt("persona_id"),rs.getString("persona_nombre"), 
					rs.getString("persona_avatar"),rs.getString("persona_sexo"),rol);
			Curso c = new Curso();
			c.setId(idCurso);
			c.setTitulo(rs.getString("curso_nombre"));
			c.setPrecio( rs.getInt("curso_precio"));
			c.setImagen(rs.getString("curso_imagen"));	
			c.setProfesor(profesor);
			p.getCursos().add(c);
			
					
		}	
		
		//actualizar hashmap
		hm.put(key, p);
		return p;
		
		
	}

		
	}

