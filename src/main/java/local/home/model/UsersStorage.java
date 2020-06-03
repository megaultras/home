package local.home.model;

import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.DBException;
import com.wizarius.orm.database.DatabaseStorage;

import local.home.model.UsersEntity;

public class UsersStorage extends DatabaseStorage<UsersEntity>
{
	public UsersStorage(DBConnectionPool pool) throws DBException 
	{
        super(pool, UsersEntity.class);
    }
}