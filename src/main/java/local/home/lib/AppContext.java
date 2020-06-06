package local.home.lib;

import local.home.lib.Data;
import local.home.lib.ActionAlert;
import local.home.lib.AuthSession;
import local.home.config.DbConfig;
import local.home.config.AppConfig;

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
	private AppConfig appConfig;
	
	private DBConnectionPool connectionPool;
	
	private AuthSession authSession;
	
	private AppContext(DbConfig dbConfig, AppConfig appConfig)
	{
//	Initialize configuration
		this.dbConfig = dbConfig;
		this.appConfig = appConfig;
		
//	Initiate DB connections pool
		System.out.println("Init DB connections pool...");
		this.setConnectionPool();
		System.out.println(" - OK");
		
//	Init authenticate session
		this.authSession = new AuthSession();
		
//	Init alert
		System.out.println("Set alert...");
		this.setAlert();
		System.out.println(" - OK");
	}
	
	public static void init(DbConfig dbConfig, AppConfig appConfig)
	{
		AppContext.instance = new AppContext(dbConfig, appConfig);
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
	
	public AppConfig getAppConfig()
	{
		return this.appConfig;
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
	
//	Auth session ============================
	public AuthSession getAuthSession()
	{
		return this.authSession;
	}
}