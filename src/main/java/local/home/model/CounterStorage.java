package local.home.model;

import java.util.HashMap;

import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.DBException;

import local.home.model.common.AbstractStorage;
import local.home.model.CounterEntity;

public class CounterStorage extends AbstractStorage<CounterEntity>
{
	public CounterStorage(DBConnectionPool pool) throws DBException 
	{
        super(pool, CounterEntity.class);
    }
	
	public HashMap<String, String> validate(CounterEntity record, String type)
	{
		HashMap<String, String> errors = new HashMap<String, String>();
		
		CounterEntity lastRecord = this.context.getData().getRecords(type).get(0);
		if (record.getCounterValue() <= lastRecord.getCounterValue()) {
			errors.put("counterValue", "Значення лічільнника не може бути меньшим за " + lastRecord.getCounterValue());
		}
		
		return errors;
	}
	
	public HashMap<String, String> validateEdit(CounterEntity record, String type)
	{	
		HashMap<String, String> errors = new HashMap<String, String>();
		
		CounterEntity prevRecord = this.context.getData().getRecords(type).get(1);
		if (record.getCounterValue() <= prevRecord.getCounterValue()) {
			errors.put("counterValue", "Значення лічільнника не може бути меньшим за " + prevRecord.getCounterValue());
		}
		
		return errors;
	}
}