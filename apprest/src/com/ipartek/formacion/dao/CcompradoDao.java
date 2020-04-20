package com.ipartek.formacion.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.ipartek.formacion.model.Ccomprado;

public class CcompradoDao implements IDAO<Ccomprado> {

	private static final Logger LOGGER = Logger.getLogger(CcompradoDao.class.getCanonicalName());
	
	private static CcompradoDao INSTANCE = null;
	
	private static String SQL_GET_ALL   = "SELECT id_persona, id_curso FROM curso_comprado ORDER BY id ASC LIMIT 500; ";
	private static String SQL_GET_BY_ID = "SELECT id_persona, id_curso FROM curso_comprado WHERE id_persona = ?, id_curso = ?; ";
	private static String SQL_DELETE    = "DELETE FROM curso_comprado WHERE id_persona = ?, id_curso = ?; ";
	private static String SQL_INSERT    = "INSERT INTO curso_comprado (id_persona, id_curso) VALUES ( ?, ? ); ";
	private static String SQL_UPDATE    = "UPDATE curso_comprado SET id_persona = ?, id_curso = ?; ";
	
	private CcompradoDao() {
		super();		
	}
	
	public synchronized static CcompradoDao getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new CcompradoDao();
        }
        return INSTANCE;
    }

	
	@Override
	public List<Ccomprado> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ccomprado getById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ccomprado delete(int id) throws Exception, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ccomprado insert(Ccomprado pojo) throws Exception, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ccomprado update(Ccomprado pojo) throws Exception, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
