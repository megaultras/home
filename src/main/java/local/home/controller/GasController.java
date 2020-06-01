package local.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gas")
public class GasController extends RecordsController
{
	public GasController()
	{
		this.setType("gas");
		this.setH1("Газ");
	}
}