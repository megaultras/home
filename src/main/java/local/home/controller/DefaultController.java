package local.home.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class DefaultController extends AbstractController
{
    @RequestMapping(
		value = "/", 
		method = RequestMethod.GET
	)
    public String index(Model model) 
    {
    	if (this.context.getData().getPeriodFrom() == null) {
    		int endIndex = this.context.getData().getMonths().size() - 1;
    		int startIndex = endIndex - 11;
    		if (startIndex < 0) {
    			startIndex = 0;
    		}
    		
    		this.context.getData().setPeriodFrom(this.context.getData().getMonths().get(startIndex));
    		this.context.getData().setPeriodTo(this.context.getData().getMonths().get(endIndex));
    	}
    	
        model.addAttribute("periods", this.context.getData().getMonths());
        model.addAttribute("periodFrom", this.context.getData().getPeriodFrom());
        model.addAttribute("periodTo", this.context.getData().getPeriodTo());
    	
        return "default/index";
    }
}