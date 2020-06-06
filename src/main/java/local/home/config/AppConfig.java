package local.home.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties("app")
public class AppConfig 
{
    private String auth_cookie_name;

	public String getAuth_cookie_name() 
	{
		return this.auth_cookie_name;
	}
	
	public void setAuth_cookie_name(String auth_cookie_name) 
	{
		this.auth_cookie_name = auth_cookie_name;
	}
}