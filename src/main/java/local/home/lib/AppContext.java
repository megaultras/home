package local.home.lib;

import local.home.lib.Data;
import local.home.lib.ActionAlert;
import local.home.config.DbConfig;

import com.wizarius.orm.database.DBException;
import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.mysql.driver.MysqlDriver;

import org.springframework.stereotype.Component;

@Component
public class AppContext 
{
	private static AppContext instance;
	
	private Data data;
	private ActionAlert alert;
	
	private DbConfig dbConfig;
	
	private DBConnectionPool connectionPool;
	
	private AppContext(DbConfig config)
	{
//	Initialize configuration
		this.dbConfig = config;
		
//	Initiate DB connections pool
		System.out.println("Init DB connections pool...");
		this.setConnectionPool();
		System.out.println(" - OK");
		
//	Init alert
		System.out.println("Set alert...");
		this.setAlert();
		System.out.println(" - OK");
	}
	
	public static void init(DbConfig config)
	{
		AppContext.instance = new AppContext(config);
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
		System.out.println(this.dbConfig.getUsername());
//		System.out.println(this.env.toString());
		
		
		
		try {
			this.connectionPool = new DBConnectionPool(new MysqlDriver(
				this.dbConfig.getDrivers(),
    			this.dbConfig.getUsername(),
    			this.dbConfig.getPassword(),
    			this.dbConfig.getDatabase(),
	            this.dbConfig.getHost(),
	            this.dbConfig.getPort(),
	            null)
	    	);
	    } catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	}
	
//	DB Config ===============================
	public DbConfig getDbConfig()
	{
		return this.dbConfig;
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