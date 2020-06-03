package local.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/electricity")
public class ElectricityController extends RecordsController
{
	public ElectricityController()
	{
		this.setType("electricity");
		this.setH1("Електроенергія");
	}
}