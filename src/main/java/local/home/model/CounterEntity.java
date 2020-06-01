package local.home.model;

import com.wizarius.orm.database.annotations.DBModel;
import com.wizarius.orm.database.annotations.DBField;

@DBModel(tableName = "counter")
public class CounterEntity extends RecordsEntity
{
	@DBField(fieldName = "id", isAutoIncrement = true)
	private int id;
	
	@DBField(fieldName = "counter_type")
	private String counterType;
	
	@DBField(fieldName = "period")
	private String period;
	
	@DBField(fieldName = "counter_value")
	private int counterValue;
	
	@DBField(fieldName = "volume")
	private int volume;
	
	@DBField(fieldName = "create_time")
	private String createTime;

	public CounterEntity() 
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
	
	public String getCounterType() 
	{
	    return this.counterType;
	}
	
	public void setCounterType(String counterType) 
	{
	    this.counterType = counterType;
	}
	
	public String getPeriod() 
	{
	    return this.period;
	}
	
	public void setPeriod(String period) 
	{
	    this.period = period;
	}
	
	public int getCounterValue() 
	{
	    return this.counterValue;
	}
	
	public void setCounterValue(int counterValue) 
	{
	    this.counterValue = counterValue;
	}
	
	public int getVolume() 
	{
	    return this.volume;
	}
	
	public void setVolume(int volume) 
	{
	    this.volume = volume;
	}
	
	public String getCreateTime() 
	{
	    return this.createTime;
	}
	
	public void setCreateTime(String createTime) 
	{
	    this.createTime = createTime;
	}
}