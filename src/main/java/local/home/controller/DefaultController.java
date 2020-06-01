package local.home.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import local.home.lib.ServiceManager;
 
@Controller
public class DefaultController 
{
    @RequestMapping(
		value = "/", 
		method = RequestMethod.GET
	)
    public String index(Model model) 
    {
    	if (ServiceManager.getData().getPeriodFrom() == null) {
    		int endIndex = ServiceManager.getData().getMonths().size() - 1;
    		int startIndex = endIndex - 11;
    		if (startIndex < 0) {
    			startIndex = 0;
    		}
    		
    		ServiceManager.getData().setPeriodFrom(ServiceManager.getData().getMonths().get(startIndex));
    		ServiceManager.getData().setPeriodTo(ServiceManager.getData().getMonths().get(endIndex));
    	}
    	
        model.addAttribute("periods", ServiceManager.getData().getMonths());
        model.addAttribute("periodFrom", ServiceManager.getData().getPeriodFrom());
        model.addAttribute("periodTo", ServiceManager.getData().getPeriodTo());
    	
        return "default/index";
    }
}