package local.home.lib;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionAlert 
{
	private String type = "";
	private String message = "";
	private boolean enabled = false;
	
	private static ArrayList<String> types = new ArrayList<String>() {{
		add("success");
		add("danger");
	}};
	
	public void setAlert(String type, String message) 
	{
		try {
			this.setType(type);
		} catch (Exception ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    }
		
		this.setMessage(message);
		this.enabled = true;
	}
	
	public String useAlert() throws Exception
	{	
		String message = this.getMessage();
		
		this.type = "";
		this.message = "";
		this.enabled = false;
		
		return message;
	}
	
	public String getType() 
	{
		String output = this.type;
		
		return output;
	}

	private void setType(String type) throws Exception
	{
		if (!ActionAlert.types.contains(type)) {
			throw new Exception("Unallowed alert type: '" + type + "'");
		}
		
		this.type = type;
	}

	private String getMessage() 
	{
		return this.message;
	}
	
	private void setMessage(String message) 
	{
		this.message = message;
	}
	
	public boolean isEnabled() 
	{
		return this.enabled;
	}
}