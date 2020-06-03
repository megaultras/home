package local.home.lib;

import local.home.lib.Data;
import local.home.lib.ActionAlert;

import com.wizarius.orm.database.DBException;
import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.mysql.driver.MysqlDriver;

public class AppContext 
{
	private static final AppContext instance = new AppContext();
	
	private Data data;
	private ActionAlert alert;
	
	private DBConnectionPool connectionPool;
	
	private AppContext()
	{
//	Initiate DB connections pool
		System.out.println("Init DB connections pool...");
		this.setConnectionPool();
		System.out.println(" - OK");
		
//	Init alert
		System.out.println("Set alert...");
		this.setAlert();
		System.out.println(" - OK");
	}
	
	public static AppContext getInstance()
	{
		return AppContext.instance;
	}
	
//	Connections pool ========================
	public DBConnectionPool getConnectionPool()
	{
		return this.connectionPool;
	}
	
	private void setConnectionPool()
	{
		try {
			this.connectionPool = new DBConnectionPool(new MysqlDriver(
    			"com.mysql.cj.jdbc.Driver",
	            "web",
	            "root",
	            "home",
	            "localhost",
	            3306,
	            null)
	    	);
	    } catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	}
	
//	Data ====================================
	public Data getData()
	{
		return this.data;
	}
	
	public void setData()
	{
		this.data = new Data();
	}
	
//	Alerts ==================================
	public ActionAlert getAlert()
	{
		return this.alert;
	}
	
	private void setAlert()
	{
		this.alert = new ActionAlert();
	}
}