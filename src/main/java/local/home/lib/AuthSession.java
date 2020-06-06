package local.home.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.http.*;
import java.util.Map.Entry;

import local.home.model.UsersEntity;

public class AuthSession 
{
	private HashMap<String, UsersEntity> session = new HashMap();
	private HashMap<Integer, UsersEntity> sessionById = new HashMap();
	
	ArrayList<String> uncheckedUrls = new ArrayList() {{
		add("/");
		add("/error");
		add("/charts/load");
		add("/auth/login");
	}};
	
	private String redirectUrl;
	
	public HashMap<String, UsersEntity> getSession() 
	{
		return this.session;
	}

	public void setSession(HashMap<String, UsersEntity> session) 
	{
		this.session = session;
	}
	
	public HashMap<Integer, UsersEntity> getSessionById() 
	{
		return this.sessionById;
	}
	
	public void setSessionById(HashMap<Integer, UsersEntity> sessionById) 
	{
		this.sessionById = sessionById;
	}
	
	public ArrayList<String> getUncheckedUrls() 
	{
		return this.uncheckedUrls;
	}
	
	
	public String getRedirectUrl() 
	{
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) 
	{
		this.redirectUrl = redirectUrl;
	}

//	Session management =================================
	public boolean addUser(UsersEntity user, HttpServletResponse response) 
	{
		boolean result = false;
		
		if (this.sessionById.containsKey(user.getId())) {
			return result;
		}
		
		String cookieUuid = this.generateSessionUuid();
		
		this.session.put(cookieUuid, user);
		this.sessionById.put((Integer) user.getId(), user);
		
		AppContext context = AppContext.getInstance();
		
		Cookie cookie = new Cookie(context.getAppConfig().getAuth_cookie_name(), cookieUuid);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		result = true;
		
		return result;
	}
	
	public boolean removeUser(String sessionUuid) 
	{
		boolean result = false;
		
		if (!this.session.containsKey(sessionUuid)) {
			return result;
		}
		
		this.sessionById.remove(this.session.get(sessionUuid).getId());
		this.session.remove(sessionUuid);
		
		result = true;
		
		return result;
	}
	
	public UsersEntity getUser(String sessionUuid) 
	{
		return this.session.get(sessionUuid);
	}
	
	public UsersEntity getUser(Integer userId) 
	{
		return this.sessionById.get(userId);
	}
	
	public String generateSessionUuid() 
	{	
		return UUID.randomUUID().toString();
	}
	
	public Cookie getAuthCookie(HttpServletRequest request) 
	{
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null) {
			AppContext context = AppContext.getInstance();
			
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(context.getAppConfig().getAuth_cookie_name())
					&& this.getSession().containsKey(cookie.getValue())
				) {
					return cookie;
				}
			}
		}
		
		return null;
	}
	
	public UsersEntity getAuthorizedUser(HttpServletRequest request) 
	{
		Cookie cookie = this.getAuthCookie(request);
		if (cookie == null) {
			return null;
		}
		
		UsersEntity authorizedUser = this.getSession().get(cookie.getValue());
		
		return (authorizedUser != null ? authorizedUser : null);
	}
	
	public boolean removeAuthorizedUser(HttpServletRequest request, HttpServletResponse response) 
	{
		Cookie cookie = this.getAuthCookie(request);
		if (cookie == null) {
			return false;
		}
		
		boolean result = this.removeUser(cookie.getValue());
		
		if (result == true) {
			cookie.setValue(null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		return result;
	}
}