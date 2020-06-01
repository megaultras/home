package local.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.wizarius.orm.database.mysql.driver.MysqlDriver;
import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.DBException;

import local.home.lib.Data;
import local.home.lib.ServiceManager;

@SpringBootApplication
public class HomeApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(HomeApplication.class, args);
	}
	
	public HomeApplication()
	{
		
	}
	
	/**
	 * After Spring Boot initialization action
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void init() 
	{
		System.out.println("===========================================");
	    System.out.println("Init");
	    System.out.println("-------------------------------------------");
	    
//	Initiate DB connections pool
	    System.out.println("Initiate DB connections pool...");
	    
	    try {
	    	DBConnectionPool connectionPool = new DBConnectionPool(new MysqlDriver(
    			"com.mysql.cj.jdbc.Driver",
	            "web",
	            "root",
	            "home",
	            "localhost",
	            3306,
	            null)
	    	);
		    ServiceManager.setConnectionPool(connectionPool);
	    } catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	    
	    System.out.println(" - OK");
	    
//	Init data
	    ServiceManager.setData();
	    
//	Load records
	    System.out.println("Load records...");
	    for (String type : Data.types) {
	    	ServiceManager.getData().loadRecords(type);
	    }
	    System.out.println(" - OK");
	    
//	Load accounts
    	System.out.println("Load accounts...");
	    ServiceManager.getData().loadAccounts();
	    System.out.println(" - OK");
	    
//	Load min values
	    System.out.println("Load min values...");
	    for (String type : Data.types) {
	    	ServiceManager.getData().loadMin(type);
	    }
	    System.out.println(" - OK");
	    
//	Load max values
	    System.out.println("Load max values...");
	    for (String type : Data.types) {
	    	ServiceManager.getData().loadMax(type);
	    }
	    System.out.println(" - OK");
	    
//	Load avg values
	    System.out.println("Load avg values...");
	    for (String type : Data.types) {
		    try {
		    	ServiceManager.getData().loadAvg(type);
		    } catch (DBException ex) {
		    	System.out.println("Error: " + ex.getMessage());
		    	System.exit(0);
		    }
	    }
	    System.out.println(" - OK");
	    
//	Load months
	    System.out.println("Load months...");
	    try {
	    	ServiceManager.getData().loadMonths();
	    } catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	    System.out.println(" - OK");
	    
	    System.out.println("===========================================");
	}
}