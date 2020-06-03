package local.home.model.common;

import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.DBException;
import com.wizarius.orm.database.DatabaseStorage;

import local.home.lib.AppContext;

public class AbstractStorage<T> extends DatabaseStorage<T>
{
	protected AppContext context;
	
	public AbstractStorage(DBConnectionPool pool, Class<T> clazz) throws DBException 
	{
		super(pool, clazz);
		
		this.context = AppContext.getInstance();
    }
}