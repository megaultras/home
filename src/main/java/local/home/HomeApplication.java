package local.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.event.EventListener;
//import org.springframework.boot.context.event.ApplicationReadyEvent;

import javax.annotation.PostConstruct;

import com.wizarius.orm.database.DBException;

import local.home.lib.Data;
import local.home.lib.AppContext;
import local.home.config.DbConfig;
import local.home.config.AppConfig;

@SpringBootApplication
public class HomeApplication 
{
	@Autowired
	private DbConfig dbConfig;
	
	@Autowired
	private AppConfig appConfig;
	
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
//	@EventListener(ApplicationReadyEvent.class)
	@PostConstruct
	public void init() 
	{
		System.out.println("===========================================");
	    System.out.println("Init");
	    System.out.println("-------------------------------------------");
	    
//	Init context
	    AppContext.init(this.dbConfig, this.appConfig);
	    AppContext context = AppContext.getInstance();
	    
//	Init data
	    System.out.println("Init data...");
	    context.setData();
	    System.out.println(" - OK");
	    
//	Load records
	    System.out.println("Load records...");
	    for (String type : Data.types) {
	    	context.getData().loadRecords(type);
	    }
	    System.out.println(" - OK");
	    
//	Load accounts
    	System.out.println("Load accounts...");
    	context.getData().loadAccounts();
	    System.out.println(" - OK");
	    
//	Load min values
	    System.out.println("Load min values...");
	    for (String type : Data.types) {
	    	context.getData().loadMin(type);
	    }
	    System.out.println(" - OK");
	    
//	Load max values
	    System.out.println("Load max values...");
	    for (String type : Data.types) {
	    	context.getData().loadMax(type);
	    }
	    System.out.println(" - OK");
	    
//	Load avg values
	    System.out.println("Load avg values...");
	    for (String type : Data.types) {
		    try {
		    	context.getData().loadAvg(type);
		    } catch (DBException ex) {
		    	System.out.println("Error: " + ex.getMessage());
		    	System.exit(0);
		    }
	    }
	    System.out.println(" - OK");
	    
//	Load months
	    System.out.println("Load months...");
	    try {
	    	context.getData().loadMonths();
	    } catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	    System.out.println(" - OK");
	    
	    System.out.println("===========================================");
	}
}