package local.home.model;

import java.util.HashMap;

import com.wizarius.orm.database.connection.DBConnectionPool;
import com.wizarius.orm.database.DBException;
import com.wizarius.orm.database.DatabaseStorage;

import local.home.model.CounterEntity;
import local.home.lib.ServiceManager;

public class CounterStorage extends DatabaseStorage<CounterEntity>
{
	public CounterStorage(DBConnectionPool pool) throws DBException 
	{
        super(pool, CounterEntity.class);
    }
	
	public HashMap<String, String> validate(CounterEntity record, String type)
	{	
		HashMap<String, String> errors = new HashMap<String, String>();
		
		CounterEntity lastRecord = ServiceManager.getData().getRecords(type).get(0);
		if (record.getCounterValue() <= lastRecord.getCounterValue()) {
			errors.put("counterValue", "Значення лічільнника не може бути меньшим за " + lastRecord.getCounterValue());
		}
		
		return errors;
	}
	
	public HashMap<String, String> validateEdit(CounterEntity record, String type)
	{	
		HashMap<String, String> errors = new HashMap<String, String>();
		
		CounterEntity prevRecord = ServiceManager.getData().getRecords(type).get(1);
		if (record.getCounterValue() <= prevRecord.getCounterValue()) {
			errors.put("counterValue", "Значення лічільнника не може бути меньшим за " + prevRecord.getCounterValue());
		}
		
		return errors;
	}
}