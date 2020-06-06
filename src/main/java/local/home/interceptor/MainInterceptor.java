package local.home.interceptor;

import javax.servlet.http.*;
import java.util.Map.Entry;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;

import local.home.lib.AppContext;
import local.home.model.UsersEntity;

public class MainInterceptor extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response, 
		Object handler
	) throws Exception {
		AppContext context = AppContext.getInstance();
		
//	Check authentication
		if (!context.getAuthSession().getUncheckedUrls().contains(request.getRequestURI())) {
			if (context.getAuthSession().getAuthorizedUser(request) == null) {
				if (context.getAuthSession().getRedirectUrl() == null) {
					context.getAuthSession().setRedirectUrl(request.getRequestURI());
				}
				
				System.out.println(" --> Not authorized");
				
				response.sendRedirect("/auth/login");
			}
		}
		
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
		AppContext context = AppContext.getInstance();
		
//	Get authorized user
		UsersEntity authorizedUser = context.getAuthSession().getAuthorizedUser(request);
		if (authorizedUser != null) {
			request.setAttribute("admin", authorizedUser);
		}
		
//  Alerts
		if (context.getAlert().isEnabled()) {
			request.setAttribute("alert", context.getAlert());
		}
	}
}