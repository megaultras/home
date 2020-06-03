package local.home.controller;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Calendar;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import local.home.model.CounterEntity;
 
@Controller
@RequestMapping("/charts")
public class ChartsController extends AbstractController
{
    @RequestMapping(
		value = "/load", 
		method = RequestMethod.POST
	)
    @ResponseBody
    public String loadCharts(@RequestParam("type") String type, @RequestParam("period_from") String period_from, @RequestParam("period_to") String period_to) 
    {
//  Init response
    	JSONObject response = new JSONObject();
    	response.put("result", false);
    	
//  Reset period range
    	this.context.getData().setPeriodFrom(period_from);
    	this.context.getData().setPeriodTo(period_to);
    	
//  Convert period to timestamp
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	Timestamp timestampFrom;
    	try {
    	    Date parsedDate = dateFormat.parse(period_from + "-01 00:00:00");
    	    timestampFrom = new Timestamp(parsedDate.getTime());
    	} catch(Exception ex) {
    	    System.out.println("Timestamp parse error: " + ex.getMessage());
    	    
    	    return response.toString();
    	}
    	
    	Timestamp timestampTo;
    	try {
    		Date parsedDate = dateFormat.parse(period_to + "-01 23:59:59");
    		timestampTo = new Timestamp(parsedDate.getTime());
    	} catch(Exception ex) {
    	    System.out.println("Timestamp parse error: " + ex.getMessage());
    	    
    	    return response.toString();
    	}
    	
//  Get records
    	ArrayList<CounterEntity> records = new ArrayList<CounterEntity>();
    	
    	for (CounterEntity record: this.context.getData().getRecords(type)) {
    		Timestamp recordTimestamp;
    		try {
        		Date recordDate = dateFormat.parse(record.getPeriod() + " 11:00:00");
        		recordTimestamp = new Timestamp(recordDate.getTime());
        	} catch(Exception ex) {
        	    System.out.println("Timestamp parse error: " + ex.getMessage());
        	    
        	    continue;
        	}
    		
    		if (recordTimestamp.after(timestampFrom) && recordTimestamp.before(timestampTo)) {
    			records.add(record);
    		}
    	}
    	
    	Collections.reverse(records);
    	
    	response.put("records", records);
    	response.put("result", true);
    	
    	return response.toString();
    }
    
    @RequestMapping(
		value = "/loadcompare", 
		method = RequestMethod.POST
	)
    @ResponseBody
    public String loadCompareCharts(@RequestParam("type") String type) 
    {
//  Init response
    	JSONObject response = new JSONObject();
    	response.put("result", false);
    	
//  Build list
    	HashMap<String, ArrayList<CounterEntity>> records = new HashMap<String, ArrayList<CounterEntity>>();
    	records.put("current", new ArrayList<CounterEntity>(this.context.getData().getRecords(type).subList(0, 12)));
    	Collections.reverse(records.get("current"));
    	
    	records.put("last", new ArrayList<CounterEntity>());
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	for (CounterEntity record: records.get("current")) {
    		try {
        		Date recordDate = dateFormat.parse(record.getPeriod());
        		Calendar c = Calendar.getInstance();
        		c.setTime(recordDate);
        		int lastYear = c.get(Calendar.YEAR) - 1;
        		int month = c.get(Calendar.MONTH) + 1;
        		String monthString = (month < 10 ? "0" + Integer.toString(month) : Integer.toString(month));
        		
        		String lastYearRecordKey = Integer.toString(lastYear) + "-" + monthString;
        		if (!this.context.getData().getRecordsByDate(type).containsKey(lastYearRecordKey)) {
        			continue;
        		}
        		CounterEntity lastYearRecord = this.context.getData().getRecordsByDate(type).get(lastYearRecordKey);
        		records.get("last").add(lastYearRecord);
        	} catch(Exception ex) {
        	    System.out.println("Timestamp parse error: " + ex.getMessage());
        	    
        	    continue;
        	}
    	}
    	
    	response.put("records", records);
    	response.put("result", true);
    	
    	return response.toString();
    }
}