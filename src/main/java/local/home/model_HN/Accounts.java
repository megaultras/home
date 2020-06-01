package local.home.model_HN;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Accounts 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String type;
	
	private String account_id;
	
	private String username;
	
	private String link;

	public Integer getId() 
	{
		return id;
	}
	
	public void setId(Integer id) 
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