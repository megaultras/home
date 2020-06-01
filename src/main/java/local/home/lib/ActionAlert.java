package local.home.lib;

import java.util.ArrayList;

public class ActionAlert 
{
	private String type;
	
	private String message;
	
	private static ArrayList<String> types = new ArrayList<String>();
	
	{
		ActionAlert.types.add("success");
		ActionAlert.types.add("danger");
	}
	
	public ActionAlert(String type, String message) 
	{
		try {
			this.setType(type);
		} catch (Exception ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    }
		
		this.setMessage(message);
	}

	public String getType() 
	{
		return type;
	}

	private void setType(String type) throws Exception
	{
		if (!ActionAlert.types.contains(type)) {
			throw new Exception("Unallowed alert type");
		}
		
		this.type = type;
	}

	public String getMessage() 
	{
		return message;
	}

	private void setMessage(String message) 
	{
		this.message = message;
	}
}