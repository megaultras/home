package local.home.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import local.home.interceptor.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer 
{
	public void addViewControllers(ViewControllerRegistry registry) 
	{
		registry.addViewController("/login").setViewName("auth/login");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) 
	{
		ArrayList<String> excludedPaths = new ArrayList<String>();
		excludedPaths.add("/css/*");
		excludedPaths.add("/css/bootstrap/*");
		excludedPaths.add("/js/*");
		excludedPaths.add("/images/*");
		excludedPaths.add("/favicon.ico");
		
	    registry.addInterceptor(new MainInterceptor()).excludePathPatterns(excludedPaths);
	}
}