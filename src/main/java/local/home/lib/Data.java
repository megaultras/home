package local.home.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wizarius.orm.database.DBException;
import com.wizarius.orm.database.data.DBOrderType;
import com.wizarius.orm.database.connection.DBConnection;

import local.home.model.*;
import local.home.lib.AppContext;
import local.home.lib.Pagination;

public class Data 
{	
	AppContext context;
	
//	Storages
	CounterStorage counterStorage;
	AccountsStorage accountsStorage;
	
//	Lists
	private static HashMap<String, ArrayList<CounterEntity>> records = new HashMap<String, ArrayList<CounterEntity>>();
	private static HashMap<String, HashMap<Integer, CounterEntity>> recordsById = new HashMap<String, HashMap<Integer, CounterEntity>>();
	private static HashMap<String, HashMap<String, CounterEntity>> recordsByDate = new HashMap<String, HashMap<String, CounterEntity>>();
	
	private static HashMap<String, AccountsEntity> accounts = new HashMap<String, AccountsEntity>();
	
//	Aggregate data
	private static HashMap<String, List<CounterEntity>> max = new HashMap<String, List<CounterEntity>>();
	private static HashMap<String, List<CounterEntity>> min = new HashMap<String, List<CounterEntity>>();
	private static HashMap<String, Float> avg = new HashMap<String, Float>();
	
//	Form data
	private static ArrayList<String> months = new ArrayList<String>();
	
	public static String[] types = {
		"electricity",
		"gas",
		"water"
	};
	
//	Alert
	protected ActionAlert actionAlert;
	
//	Charts date range
	private String periodFrom;
	private String periodTo;
	
	public Data()
	{
		this.context = AppContext.getInstance();
		
		try {
			this.counterStorage = new CounterStorage(this.context.getConnectionPool());
			this.accountsStorage = new AccountsStorage(this.context.getConnectionPool());
		} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	}
	
//	Records =========================================================================================
	public void loadRecords(String type)
	{	
		List<CounterEntity> list = new ArrayList<CounterEntity>();
		
		try {
			list = this.counterStorage.getSession().getSelectQuery()
				.where("counter_type", type)
				.where("id != (SELECT MIN(id) FROM counter WHERE counter_type = '" + type + "')")
				.orderBy("period", DBOrderType.DESC)
				.execute();
		} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
		
		Data.records.put(type, (ArrayList<CounterEntity>) list);
		Data.recordsById.put(type, new HashMap<Integer, CounterEntity>());
		Data.recordsByDate.put(type, new HashMap<String, CounterEntity>());
		
		for (CounterEntity record : list) {
			Data.recordsById.get(record.getCounterType()).put(record.getId(), record);
			
			String date = record.getPeriod().substring(0, 7);
			Data.recordsByDate.get(record.getCounterType()).put(date, record);
		}
	}
	
	public ArrayList<CounterEntity> getRecords(String type)
	{
		return Data.records.get(type);
	}
	
	public CounterEntity getRecord(String type, int id)
	{
		return Data.recordsById.get(type).get(id);
	}
	
	public Pagination getRecordsByPages(String type, int page, int pageSize)
	{
		Pagination pagination = new Pagination(page, pageSize);
		pagination.setPages((int) Math.ceil((double) Data.records.get(type).size() / pageSize));
		pagination.setTotalRecords(Data.records.get(type).size());
		pagination.setNextPage(pagination.getCurrentPage(), pagination.getPages());
		pagination.setPrevPage(pagination.getCurrentPage());
		
		int startIndex = page * pageSize - pageSize;
		if (startIndex > Data.records.get(type).size() - 1) {
			return pagination;
		}
		
		int endIndex = startIndex + pageSize;
		if (endIndex > Data.records.get(type).size()) {
			endIndex = Data.records.get(type).size();
		}
		
		pagination.setRecords(new ArrayList<CounterEntity>(Data.records.get(type).subList(startIndex, endIndex)));
		
		return pagination;
	}
	
//	Accounts =========================================================================================
	public void loadAccounts()
	{
		List<AccountsEntity> list = new ArrayList<AccountsEntity>();
		
		try {
			list = this.accountsStorage.getSession().getSelectQuery()
				.execute();
		} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
		
		for (AccountsEntity record : list) {
			Data.accounts.put(record.getType(), record);
		}
	}
	
	public AccountsEntity getAccount(String type)
	{
		return Data.accounts.get(type);
	}
	
//	Aggregate data ===================================================================================
	public void loadMax(String type)
	{
		List<CounterEntity> list = new ArrayList<CounterEntity>();
		
		try {
			list = this.counterStorage.getSession().getSelectQuery()
				.where("counter_type", type)
				.where("id != (SELECT MIN(id) FROM counter WHERE counter_type = '" + type + "')")
				.orderBy("volume", DBOrderType.DESC)
				.setLimit(3)
				.execute();
			
			Data.max.put(type, list);
		} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	}
	
	public List<CounterEntity> getMax(String type)
	{
		return Data.max.get(type);
	}
	
	public void loadMin(String type)
	{
		List<CounterEntity> list = new ArrayList<CounterEntity>();
		
		try {
			list = this.counterStorage.getSession().getSelectQuery()
				.where("counter_type", type)
				.where("id != (SELECT MIN(id) FROM counter WHERE counter_type = '" + type + "')")
				.orderBy("volume", DBOrderType.ASC)
				.setLimit(3)
				.execute();
			
			Collections.reverse(list);
			Data.min.put(type, list);
		} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	}
	
	public List<CounterEntity> getMin(String type)
	{
		return Data.min.get(type);
	}
	
	public void loadAvg(String type) throws DBException
	{
		try {
			String fieldName = "avgVolume";
			String sql = "SELECT AVG(volume) AS " + fieldName + " "
				+ "FROM counter "
				+ "WHERE counter_type = '" + type + "' "
					+ "AND id != (SELECT MIN(id) FROM counter WHERE counter_type = '" + type + "')";
			DBConnection connection = this.counterStorage.getSession().getPool().getConnection();
			ResultSet queryResultSet = connection.executeSqlQuery(sql);
			
			queryResultSet.next();
			Data.avg.put(type, queryResultSet.getFloat(fieldName));
		} catch (SQLException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	}
	
	public Float getAvg(String type)
	{
		return Data.avg.get(type);
	}
	
	public void loadMonths() throws DBException
	{
		try {
			String fieldName = "period_date";
			String sql = "SELECT DISTINCT(period) AS " + fieldName + " "
				+ "FROM counter "
				+ "ORDER BY period";
			DBConnection connection = this.counterStorage.getSession().getPool().getConnection();
			ResultSet queryResultSet = connection.executeSqlQuery(sql);
			
			while (queryResultSet.next()) {
				Data.months.add(queryResultSet.getString(fieldName).substring(0, 7));
			}
		} catch (SQLException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    	System.exit(0);
	    }
	}
	
	public ArrayList<String> getMonths()
	{
		return Data.months;
	}
	
//	Chart date range
	public String getPeriodFrom() 
	{
		return periodFrom;
	}

	public void setPeriodFrom(String periodFrom) 
	{
		this.periodFrom = periodFrom;
	}

	public String getPeriodTo() 
	{
		return periodTo;
	}

	public void setPeriodTo(String periodTo) 
	{
		this.periodTo = periodTo;
	}

	public HashMap<String, CounterEntity> getRecordsByDate(String type) 
	{
		return recordsByDate.get(type);
	}

	public void setRecordsByDate(HashMap<String, HashMap<String, CounterEntity>> recordsByDate) 
	{
		Data.recordsByDate = recordsByDate;
	}
}