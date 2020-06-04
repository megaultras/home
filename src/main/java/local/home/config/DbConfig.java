package local.home.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties("jdbc")
public class DbConfig 
{
    private String drivers;
	private String url;
	private String username;
	private String password;
	private String host;
	private String database;
	private int port;

	public String getDrivers() 
	{
		return this.drivers;
	}
	
	public void setDrivers(String drivers) 
	{
		this.drivers = drivers;
	}
	
	public String getUrl() 
	{
		return this.url;
	}
	
	public void setUrl(String url) 
	{
		this.url = url;
	}
	
	public String getUsername() 
	{	
		return this.username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword() 
	{
		return this.password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getDatabase() 
	{
		return this.database;
	}
	
	public void setDatabase(String database) 
	{
		this.database = database;
	}
	
	public String getHost() 
	{
		return this.host;
	}
	
	public void setHost(String host) 
	{
		this.host = host;
	}
	
	public int getPort() 
	{
		return this.port;
	}
	
	public void setPort(int port) 
	{
		this.port = port;
	}
}