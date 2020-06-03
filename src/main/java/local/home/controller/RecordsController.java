package local.home.controller;

import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.JSONObject;

import com.wizarius.orm.database.DBException;

import local.home.lib.ServiceManager;
import local.home.lib.Pagination;
import local.home.model.CounterEntity;
import local.home.model.CounterStorage;
 
@Controller
public class RecordsController extends AbstractController
{
	protected String type;
	protected String h1;
	
	protected int pageSize = 12;
	
	protected void setType(String type)
	{
		this.type = type;
	}
	
	protected void setH1(String h1)
	{
		this.h1 = h1;
	}
	
    @RequestMapping(
		value = "", 
		method = RequestMethod.GET
	)
    public String index(Model model, @RequestParam(required = false) Integer page, Principal principal)
    {	
//  Titles
    	model.addAttribute("h1", this.h1);
    	model.addAttribute("type", this.type);
    	
//  Records
    	if (page == null) {
    		page = 1;
    	}
    	Pagination pagination = ServiceManager.getData().getRecordsByPages(this.type, (int) page, this.pageSize);
        model.addAttribute("pagination", pagination);
        
//  Account
        model.addAttribute("account", ServiceManager.getData().getAccount(this.type));
        
//  Statistic
        model.addAttribute("max", ServiceManager.getData().getMax(this.type));
        model.addAttribute("min", ServiceManager.getData().getMin(this.type));
        model.addAttribute("avg", ServiceManager.getData().getAvg(this.type));
        
//  Last record
        model.addAttribute("last", ServiceManager.getData().getRecords(this.type).get(0));
        
//  Model for add form
        CounterEntity record = new CounterEntity();
        record.setCounterType(this.type);
        
        String last_period = ServiceManager.getData().getRecords(this.type).get(0).getPeriod();
        Integer lastYear = Integer.parseInt(last_period.substring(0, 4));
        Integer lastMonth = Integer.parseInt(last_period.substring(5, 7));
        lastMonth++;
        if (lastMonth > 12) {
        	lastMonth = 1;
        	lastYear++;
        }
        String nextPeriod = lastYear.toString() 
    		+ "-" 
    		+ (lastMonth.toString().length() == 1 ? "0" : "")
    		+ lastMonth.toString();
        record.setPeriod(nextPeriod);
        
        model.addAttribute("record", record);
        
//  Alerts
        if (ServiceManager.getData().getActionAlert() != null) {
        	model.addAttribute("alert_type", ServiceManager.getData().getActionAlert().getType());
        	model.addAttribute("alert_message", ServiceManager.getData().getActionAlert().getMessage());
        	ServiceManager.getData().unsetActionAlert();
        }
        
        return "records/index";
    }
    
//  CRUD actions ================================================================
    @RequestMapping(
		value = "/add", 
		method = RequestMethod.POST
	)
    @ResponseBody
    public String add(@ModelAttribute("record") CounterEntity record) 
    {
    	System.out.println("---------------------------------");
    	System.out.println("Add record");
    	System.out.println(record.getCounterType());
    	System.out.println(record.getCounterValue());
    	
//  Init response
    	JSONObject response = new JSONObject();
    	response.put("result", true);
    	HashMap<String, String> errors;
    	
    	CounterStorage storage = null;
    	
    	try {
    		storage = new CounterStorage(ServiceManager.getConnectionPool());
    	} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	
	    	response.put("result", false);
	    	errors = new HashMap<String, String>();
	    	errors.put("counterValue", "Storage creation error");
	    	response.put("errors", errors);
	    	
	    	return response.toString();
	    }
    	
//  Validation
    	errors = storage.validate(record, this.type);
    	if (errors.size() > 0) {
    		response.put("result", false);
	    	response.put("errors", errors);
	    	
	    	return response.toString();
    	}
    	
//  Set record props
    	record.setPeriod(record.getPeriod() + "-01");
    	
    	CounterEntity lastRecord = ServiceManager.getData().getRecords(type).get(0);
    	record.setVolume(record.getCounterValue() - lastRecord.getCounterValue());
    	
    	Date currentDate = Calendar.getInstance().getTime();
    	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	record.setCreateTime(formater.format(currentDate));
    	
//  Add record
		try {
			storage.getSession().getInsertQuery().execute(record);
		} catch (DBException ex) {
	    	System.out.println("Insert error: " + ex.getMessage());
	    	
	    	response.put("result", false);
	    	errors = new HashMap<String, String>();
	    	errors.put("counterValue", "Unable to create record");
	    	response.put("errors", errors);
	    	
	    	return response.toString();
	    }
    	
//  Reload data by type
    	ServiceManager.getData().loadRecords(this.type);
    	ServiceManager.getData().loadMin(this.type);
    	ServiceManager.getData().loadMax(this.type);
    	try {
	    	ServiceManager.getData().loadAvg(this.type);
	    } catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    }
    	
//  Set action alert
    	ServiceManager.getData().setActionAlert("success", "Запис додано");
    	
    	System.out.println("OK");
    	
    	return response.toString();
    }
    
    @RequestMapping(
		value = "/edit", 
		method = RequestMethod.POST
	)
    @ResponseBody
    public String edit(@ModelAttribute("record") CounterEntity record) 
    {
    	System.out.println("---------------------------------");
    	System.out.println("Edit record");
    	System.out.println(record.getId());
    	System.out.println(record.getCounterType());
    	System.out.println(record.getCounterValue());
    	
//  Init response
    	JSONObject response = new JSONObject();
    	response.put("result", true);
    	HashMap<String, String> errors;
    	
    	CounterStorage storage = null;
    	
    	try {
    		storage = new CounterStorage(ServiceManager.getConnectionPool());
    	} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	
	    	response.put("result", false);
	    	errors = new HashMap<String, String>();
	    	errors.put("counterValue", "Storage creation error");
	    	response.put("errors", errors);
	    	
	    	return response.toString();
	    }
    	
//  Validation
    	errors = storage.validateEdit(record, this.type);
    	if (errors.size() > 0) {
    		response.put("result", false);
	    	response.put("errors", errors);
	    	
	    	return response.toString();
    	}
    	
//  Set record props
    	CounterEntity prevRecord = ServiceManager.getData().getRecords(type).get(1);
    	record.setVolume(record.getCounterValue() - prevRecord.getCounterValue());
    	
    	CounterEntity oldRecord = ServiceManager.getData().getRecord(type, record.getId());
    	record.setCreateTime(oldRecord.getCreateTime());
    	record.setPeriod(oldRecord.getPeriod());
    	
//  Edit record
		try {
			storage.getSession().getUpdateQuery().where("id", record.getId()).execute(record);
		} catch (DBException ex) {
	    	System.out.println("Update error: " + ex.getMessage());
	    	
	    	response.put("result", false);
	    	errors = new HashMap<String, String>();
	    	errors.put("counterValue", "Unable to update record");
	    	response.put("errors", errors);
	    	
	    	return response.toString();
	    }
    	
//  Reload data by type
    	ServiceManager.getData().loadRecords(this.type);
    	ServiceManager.getData().loadMin(this.type);
    	ServiceManager.getData().loadMax(this.type);
    	try {
	    	ServiceManager.getData().loadAvg(this.type);
	    } catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    }
    	
//  Set action alert
    	ServiceManager.getData().setActionAlert("success", "Запис відредаговано");
    	
    	System.out.println("OK");
    	
    	return response.toString();
    }
}