package com.ipartek.formacion.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.ipartek.formacion.dao.ConnectionManager;
import com.ipartek.formacion.dao.IDAO;
import com.ipartek.formacion.model.Curso;
import com.ipartek.formacion.model.Persona;
import com.ipartek.formacion.model.Rol;

public class CursoDao implements IDAO<Curso> {
	
	private static final Logger LOGGER = Logger.getLogger(CursoDao.class.getCanonicalName());
	
	private static CursoDao INSTANCE = null;
	
	private static String SQL_GET_ALL   = "SELECT id, titulo, imagen, precio,id_profesor FROM curso ORDER BY id DESC LIMIT 100; ";
	private static String SQL_GET_ALLPROF = "	SELECT  \r\n" + 
			"				c.id as curso_id,\r\n" + 
			"				c.titulo as curso_nombre,\r\n" + 
			"				c.precio as curso_precio,\r\n" + 
			"				c.imagen  as curso_imagen,\r\n" + 
		//	"				c.id_profesor as id_profesor,\r\n" + 
			"				p.id AS id_profesor,\r\n" + 
			"				p.nombre as persona_nombre,\r\n" + 
			"				p.sexo AS persona_sexo,\r\n" + 
			"				p.avatar AS persona_avatar,\r\n" + 
			"				p.id_rol AS rol_id\r\n" + 
			"			 FROM curso c LEFT JOIN persona p ON c.id_profesor = p.id order by c.id desc;  ";
	private static String SQL_GET_LIKE_NOMBRE   = "SELECT   \r\n" + 
			"							c.id as curso_id,\r\n" + 
			"							c.titulo as curso_nombre,\r\n" + 
			"							c.precio as curso_precio,\r\n" + 
			"							c.imagen  as curso_imagen, \r\n" + 
			"							c.id_profesor as id_profesor, \r\n" + 
			"							p.id AS id_profesor, \r\n" + 
			"							p.nombre as persona_nombre,\r\n" + 
			"							p.sexo AS persona_sexo, \r\n" + 
			"							p.avatar AS persona_avatar,\r\n" + 
			"							p.id_rol AS rol_id \r\n" + 
			"						 FROM curso c LEFT JOIN persona p ON c.id_profesor = p.id WHERE c.titulo = ? ORDER BY id DESC LIMIT 100;";
	private static String SQL_GET_BY_ID = "	SELECT  \r\n" + 
			"				c.id as curso_id,\r\n" + 
			"				c.titulo as curso_nombre,\r\n" + 
			"				c.precio as curso_precio,\r\n" + 
			"				c.imagen  as curso_imagen,\r\n" + 
			"				c.id_profesor as id_profesor,\r\n" +
			"FROM curso c WHERE c.id = ?; ";
	

	private static String SQL_DELETE    = "DELETE FROM curso WHERE id = ?; ";
	private static String SQL_INSERT    = "INSERT INTO curso (titulo, imagen, precio,id_profesor) VALUES ( ?, ?, ?,? ); ";
	private static String SQL_UPDATE    = "UPDATE curso SET id_profesor = ? WHERE id = ?; ";
	
	private CursoDao() {
		super();		
	}
	
	public synchronized static CursoDao getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new CursoDao();
        }
        return INSTANCE;
    }

	@Override
	public List<Curso> getAll() {
		LOGGER.info("getAll");		
		ArrayList<Curso> registros = new ArrayList<Curso>();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALLPROF);
				ResultSet rs = pst.executeQuery();) {

			LOGGER.info(pst.toString());			
			while( rs.next() ) {				
				registros.add( mapper(rs) );				
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		return registros;
	}
	
	/**
	 * Busca cursos por nombre que coincidan LIKE con el parametro busqueda
	 * @param busqueda String termino a buscar dentro de la columna nombre
	 * @return List<Curso> 
	 */
	public List<Curso> getAllLikeNombre( String busqueda ) {
		LOGGER.info("getAll buscar por nombre curso");		
		ArrayList<Curso> registros = new ArrayList<Curso>();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_LIKE_NOMBRE);
				) {

			
			pst.setString(1, "%" + busqueda + "%");
			
			try( ResultSet rs = pst.executeQuery() ){
				
				LOGGER.info(pst.toString());			
				while( rs.next() ) {				
					registros.add( mapper(rs) );				
				}	
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		return registros;
	}
	
	@Override
	public Curso getById(int id) throws Exception {
		Curso registro = null;
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID);) {

			pst.setInt(1, id);
			LOGGER.info(pst.toString());
			
			try( ResultSet rs = pst.executeQuery() ){
			
				if( rs.next() ) {					
					registro = mapper(rs);					
					
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
	public Curso delete(int id) throws Exception, SQLException {
		Curso registro = null;
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID);
		) {

			pst.setInt(1, id);
			LOGGER.info(pst.toString());
			
			try( ResultSet rs = pst.executeQuery() ){			
				
				if( rs.next() ) {					
					registro = mapper(rs);
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
	public Curso insert(Curso pojo) throws Exception, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Curso update(Curso pojo) throws Exception, SQLException {

		LOGGER.info("UPDATE DAO Curso: (" + pojo + ")");
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE);
		) {
			if (pojo.getProfesor() == null) {		
				pst.setObject(1, pojo.getProfesor());
				
			} else {
				
				pst.setObject(1, pojo.getProfesor().getId());
				
			}
			//pst.setInt(1, pojo.getProfesor().getId());
			
			pst.setInt(2, pojo.getId());
			
			LOGGER.info(pst.toString());

			// eliminamos la Persona
			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				
				LOGGER.warning("Curso no modificado, id= " + pojo.getId());
				throw new Exception("No se puede actualizar el registro con el id: " + pojo);
			}
			
			LOGGER.info("Modificado Curso Dao: " + pojo);
			
		} catch (SQLException e) {
			
			LOGGER.warning("Error!");
			throw new SQLException("No se puede actualizar");
		}
		
		return pojo;
	}
	
	private Curso mapper( ResultSet rs ) throws SQLException {

		
		Curso c=new Curso();
		Persona profesor;
		Rol rol = new Rol();
		//rol = new Rol(rs.getInt("rol_id"), rs.getString("rol_nombre"));	
		
		// si no existe en el hm se crea
		
		//c = new Curso();
		c.setId(rs.getInt("curso_id") );
		c.setTitulo( rs.getString("curso_nombre"));
		c.setPrecio( rs.getInt("curso_precio"));
		c.setImagen( rs.getString("curso_imagen"));
	
		profesor = new Persona(rs.getInt("id_profesor"),rs.getString("persona_nombre"), 
				rs.getString("persona_avatar"),rs.getString("persona_sexo"),rol);	
	
		c.setProfesor(profesor);
		return c;
		
		
	}
	

}
