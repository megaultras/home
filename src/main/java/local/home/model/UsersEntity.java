package local.home.model;

import com.wizarius.orm.database.annotations.DBModel;
import com.wizarius.orm.database.annotations.DBField;

@DBModel(tableName = "users")
public class UsersEntity
{
	@DBField(fieldName = "id", isAutoIncrement = true)
	private int id;
	
	@DBField(fieldName = "login")
	private String login;
	
	@DBField(fieldName = "password")
	private String password;
	
	public UsersEntity() 
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
	
	public String getLogin() 
	{
	    return this.login;
	}
	
	public void setLogin(String login) 
	{
	    this.login = login;
	}
	
	public String getPassword() 
	{
	    return this.password;
	}
	
	public void setPassword(String password) 
	{
	    this.password = password;
	}
}