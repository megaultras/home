package local.home.interceptor;

import javax.servlet.http.*;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import local.home.lib.AppContext;

public class MainInterceptor extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response, 
		Object handler) throws Exception {
	    
//		System.out.println(request.getMethod() + " | " + request.getRequestURI());
		
	    return true;
	}
	
	@Override
	public void postHandle(
		HttpServletRequest request, 
		HttpServletResponse response, 
		Object handler, 
		ModelAndView model
	) throws Exception 
	{
//	Get authorized user
		try {
			User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (authUser != null) {
				request.setAttribute("admin", authUser.getUsername());
			}
		} catch (Exception ex) {
			
		}
		
//  Alerts
		AppContext context = AppContext.getInstance();
		if (context.getAlert().isEnabled()) {
			System.out.println("++++++++++++++++++");
			request.setAttribute("alert", context.getAlert());
		}
	}
}