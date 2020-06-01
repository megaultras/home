package local.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/water")
public class WaterController extends RecordsController
{
	public WaterController()
	{
		this.setType("water");
		this.setH1("Вода");
	}
}