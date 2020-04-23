package com.ipartek.formacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import com.ipartek.formacion.model.Noticia;

public class NoticiaDao implements IDAO<Noticia> {

	private static final Logger LOGGER = Logger.getLogger(NoticiaDao.class.getCanonicalName());
	
	private static NoticiaDao INSTANCE = null;
	
	private static String SQL_GET_ALL   = "SELECT id,titulo,fecha,contenido FROM noticia ORDER BY id DESC LIMIT 10; ";
	/*private static String SQL_GET_BY_ID = "SELECT id_persona, id_curso FROM curso_comprado WHERE id_persona = ?, id_curso = ?; ";
	private static String SQL_DELETE    = "DELETE FROM curso_comprado WHERE id_persona = ?, id_curso = ?; ";
	private static String SQL_INSERT    = "INSERT INTO curso_comprado (id_persona, id_curso) VALUES ( ?, ? ); ";
	private static String SQL_UPDATE    = "UPDATE curso_comprado SET id_persona = ?, id_curso = ?; ";
	*/
	
	private NoticiaDao() {
		super();		
	}
	
	public synchronized static NoticiaDao getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new NoticiaDao();
        }
        return INSTANCE;
    }

	
	@Override
	public List<Noticia> getAll() {
		LOGGER.info("getAll");		
		ArrayList<Noticia> registros = new ArrayList<Noticia>();
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

	@Override
	public Noticia getById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noticia delete(int id) throws Exception, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noticia insert(Noticia pojo) throws Exception, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noticia update(Noticia pojo) throws Exception, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	private Noticia mapper( ResultSet rs ) throws SQLException {
		Noticia n = new Noticia();
		n.setId( rs.getInt("id") );
		n.setTitulo( rs.getString("titulo"));
		n.setFecha( rs.getString("fecha"));
		n.setContenido( rs.getString("contenido"));
		return n;
	}
}
