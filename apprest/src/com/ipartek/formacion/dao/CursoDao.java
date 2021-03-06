package com.ipartek.formacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.ipartek.formacion.model.Curso;

public class CursoDao implements IDAO<Curso> {
	
	private static final Logger LOGGER = Logger.getLogger(CursoDao.class.getCanonicalName());
	
	private static CursoDao INSTANCE = null;
	
	private static String SQL_GET_ALL   = "SELECT id, titulo, imagen, precio FROM curso ORDER BY id DESC LIMIT 100; ";
	private static String SQL_GET_LIKE_NOMBRE   = "SELECT id, titulo, imagen, precio FROM curso WHERE titulo LIKE ? ORDER BY id DESC LIMIT 100; ";
	
	private static String SQL_GET_BY_ID = "SELECT id, titulo, imagen, precio FROM curso WHERE id = ?; ";
	private static String SQL_DELETE    = "DELETE FROM curso WHERE id = ?; ";
	private static String SQL_INSERT    = "INSERT INTO curso (titulo, imagen, precio) VALUES ( ?, ?, ? ); ";
	private static String SQL_UPDATE    = "UPDATE curso SET titulo = ?, imagen = ?,  precio = ? WHERE id = ?; ";
	
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
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
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
		LOGGER.info("getAll");		
		ArrayList<Curso> registros = new ArrayList<Curso>();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_LIKE_NOMBRE);
				) {

			//CUIDADO los simobolos % % no se pueden porner en la SQL, siempre en el PST
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
		// TODO Auto-generated method stub
		return null;
	}
	
	private Curso mapper( ResultSet rs ) throws SQLException {
		Curso c = new Curso();
		c.setId( rs.getInt("id") );
		c.setTitulo( rs.getString("titulo"));
		c.setImagen( rs.getString("imagen"));
		c.setPrecio( rs.getInt("precio"));
		return c;
	}

	@Override
	public Curso getByNombre(String nombre) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

}
