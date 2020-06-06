package local.home.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import local.home.model.UsersEntity;
import local.home.model.UsersStorage;

import com.wizarius.orm.database.DBException;
 
@Controller
@RequestMapping("/auth")
public class AuthController extends AbstractController
{
//	Login ==========================================================
	@RequestMapping(
		value = "/login", 
		method = RequestMethod.GET
	)
    public String loginPage(Model model) 
    {
    	return "auth/login";
    }
	
    @RequestMapping(
		value = "/login", 
		method = RequestMethod.POST
	)
    public String loginProcess(
		@RequestParam("username") String username, 
		@RequestParam("password") String password,
		HttpServletResponse response
	) 
    {
    	System.out.println("---------------------------------");
    	System.out.println("Auth user");
    	System.out.println(username);
    	System.out.println(password);
    	
//  Create storage
    	UsersStorage storage = null;
    	
    	try {
    		storage = new UsersStorage(this.context.getConnectionPool());
    	} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	this.context.getAlert().setAlert("danger", "Невдалося створити сторедж");
	    	
	    	return "redirect:/auth/login";
	    }
    	
    	if (storage != null) {
//  Get user
    		UsersEntity user = null;
    		
    		try {
    			user = storage.getSession().getSelectQuery()
    				.where("login", username)
    				.where("password", password)
    				.getOne();
    		} catch (DBException ex) {
    	    	System.out.println("Get user error: " + ex.getMessage());
    	    	this.context.getAlert().setAlert("danger", "Невдалося отримати дані");
    	    	
    	    	return "redirect:/auth/login";
    	    }
    		
    		if (user == null) {
    			this.context.getAlert().setAlert("danger", "Користувача не знайдено");
    	    	
    	    	return "redirect:/auth/login";
    		}
    		
//  Set session
    		this.context.getAuthSession().addUser(user, response);
    		
        	System.out.println("OK");
    	}
    	
    	String redirectUrl;
    	if (this.context.getAuthSession().getRedirectUrl() != null) {
    		redirectUrl = this.context.getAuthSession().getRedirectUrl();
    		this.context.getAuthSession().setRedirectUrl(null);
    	} else {
    		redirectUrl = "/";
    	}
    	
    	return "redirect:" + redirectUrl;
    }
    
//  Logout ==============================================================
    @RequestMapping(
		value = "/logout", 
		method = RequestMethod.GET
	)
    public String logout(HttpServletRequest request, HttpServletResponse response) 
    {
    	boolean result = this.context.getAuthSession().removeAuthorizedUser(request, response);
		
		if (result == true) {
			this.context.getAlert().setAlert("success", "Сесію припинено");
		} else {
			this.context.getAlert().setAlert("danger", "Помилка деавторизації");
		}
    	
    	return "redirect:/";
    }
}