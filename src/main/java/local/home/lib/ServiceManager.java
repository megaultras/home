package local.home.lib;

import local.home.lib.Data;
import local.home.model.CounterStorage;
import local.home.model.AccountsStorage;

import com.wizarius.orm.database.connection.DBConnectionPool;
//import com.wizarius.orm.database.mysql.MysqlConnection;

public class ServiceManager 
{
	private static Data data;
	
	private static DBConnectionPool connectionPool;
	
//	Connections pool
	public static DBConnectionPool getConnectionPool()
	{
		return ServiceManager.connectionPool;
	}
	
	public static void setConnectionPool(DBConnectionPool connectionPool)
	{
		ServiceManager.connectionPool = connectionPool;
	}
	
//	Data ===========================================================================================
	public static Data getData()
	{
		return ServiceManager.data;
	}
	
	public static void setData()
	{
		ServiceManager.data = new Data();
	}
}