package local.home.model;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.DBException;
import com.wizarius.orm.database.DatabaseStorage;

import local.home.model.AccountsEntity;

public class AccountsStorage extends DatabaseStorage<AccountsEntity>
{
	public AccountsStorage(DBConnectionPool pool) throws DBException 
	{
        super(pool, AccountsEntity.class);
    }
}