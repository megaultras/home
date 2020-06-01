package local.home.controller;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PathVariable;

import local.home.lib.ServiceManager;
import local.home.model.CounterEntity;
 
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