package local.home.model;

import com.wizarius.orm.database.annotations.DBModel;
import com.wizarius.orm.database.annotations.DBField;

@DBModel(tableName = "accounts")
public class AccountsEntity
{
	@DBField(fieldName = "id", isAutoIncrement = true)
	private int id;
	
	@DBField(fieldName = "type")
	private String type;
	
	@DBField(fieldName = "account_id")
	private String account_id;
	
	@DBField(fieldName = "username")
	private String username;
	
	@DBField(fieldName = "link")
	private String link;

	public AccountsEntity() 
	{
		
    }
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
	    this.id = id;
	}
	
	public String getType() 
	{
	    return this.type;
	}
	
	public void setType(String type) 
	{
	    this.type = type;
	}
	
	public String getAccount_id() 
	{
	    return this.account_id;
	}
	
	public void setAccount_id(String account_id) 
	{
	    this.account_id = account_id;
	}
	
	public String getUsername() 
	{
	    return this.username;
	}
	
	public void setUsername(String username) 
	{
	    this.username = username;
	}
	
	public String getLink() 
	{
	    return this.link;
	}
	
	public void setLink(String link) 
	{
	    this.link = link;
	}
}