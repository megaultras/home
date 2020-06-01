package local.home.model_HN;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Counter 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String counter_type;
	
	private String period;
	
	private int counter_value;
	
	private int volume;
	
	private String create_time;

	public Integer getId() 
	{
		return id;
	}
	
	public void setId(Integer id) 
	{
	    this.id = id;
	}
	
	public String getCounter_type() 
	{
	    return this.counter_type;
	}
	
	public void setCounter_type(String counter_type) 
	{
	    this.counter_type = counter_type;
	}
	
	public String getPeriod() 
	{
	    return this.period;
	}
	
	public void setPeriod(String period) 
	{
	    this.period = period;
	}
	
	public int getCounter_value() 
	{
	    return this.counter_value;
	}
	
	public void setCounter_value(int counter_value) 
	{
	    this.counter_value = counter_value;
	}
	
	public int getVolume() 
	{
	    return this.volume;
	}
	
	public void setVolume(int volume) 
	{
	    this.volume = volume;
	}
	
	public String getCreate_time() 
	{
	    return this.create_time;
	}
	
	public void setCreate_time(String create_time) 
	{
	    this.create_time = create_time;
	}
}