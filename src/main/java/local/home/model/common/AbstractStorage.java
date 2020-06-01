package local.home.model.common;

import com.wizarius.orm.database.DBConnectionPool;
import com.wizarius.orm.database.DatabaseStorage;
import com.wizarius.orm.database.exceptions.DBException;
import com.wizarius.orm.database.interfaces.DBEntity;

@SuppressWarnings("unchecked")
public class AbstractStorage<T extends AbstractEntity> extends DatabaseStorage<T>
{
	public AbstractStorage(DBConnectionPool pool, Class<? extends DBEntity> clazz) throws DBException {
        super(pool, clazz);
    }
}