package local.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wizarius.orm.database.DBException;

import local.home.lib.ActionAlert;
import local.home.lib.ServiceManager;
import local.home.model.AccountsEntity;
import local.home.model.AccountsStorage;
 
@Controller
@RequestMapping("/account")
public class AccountController extends AbstractController
{
    @RequestMapping(
		value = "/edit", 
		method = RequestMethod.POST
	)
    public String index(@ModelAttribute("account") AccountsEntity account) 
    {
    	System.out.println("---------------------------------");
    	System.out.println("Edit account");
    	System.out.println(account.getType());
    	System.out.println(account.getAccount_id());
    	System.out.println(account.getUsername());
    	System.out.println(account.getLink());
    	
    	AccountsStorage storage = null;
    	
    	try {
    		storage = new AccountsStorage(ServiceManager.getConnectionPool());
    	} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	
	    	ServiceManager.getData().setActionAlert("danger", "Невдалося створити сторедж");
	    	return "redirect:/" + account.getType();
	    }
    	
    	if (storage != null) {
    		try {
    			storage.getSession().getUpdateQuery().where("id", account.getId()).execute(account);
    		} catch (DBException ex) {
    	    	System.out.println("Update error: " + ex.getMessage());
    	    	
    	    	ServiceManager.getData().setActionAlert("danger", "Невдалося зберегти дані");
    	    	return "redirect:/" + account.getType();
    	    }
    		
    		ServiceManager.getData().loadAccounts();
    		
//  Set action alert
    		ServiceManager.getData().setActionAlert("success", "Аккаунт відредаговано");
        	
        	System.out.println("OK");
    	}
    	
    	return "redirect:/" + account.getType();
    }
}