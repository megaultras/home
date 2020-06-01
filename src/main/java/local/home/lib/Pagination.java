package local.home.lib;

import java.util.ArrayList;
import local.home.model.CounterEntity;

public class Pagination 
{
	private int currentPage;
	private int prevPage;
	private int nextPage;
	
	private int pages;
	
	private int pageSize;
	
	private int totalRecords;
	
	private ArrayList<CounterEntity> records;
	
	public Pagination(int currentPage, int pageSize)
	{
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.records = new ArrayList<CounterEntity>();
	}
	
	public int getPrevPage() 
	{
		return prevPage;
	}

	public void setPrevPage(int currentPage) 
	{
		if (currentPage > 1) {
			this.prevPage = currentPage - 1;
		} else {
			this.prevPage = 1;
		}
	}

	public int getNextPage() 
	{
		return nextPage;
	}

	public void setNextPage(int currentPage, int pages) 
	{
		if (currentPage < pages) {
			this.nextPage = currentPage + 1;
		} else {
			this.nextPage = pages;
		}
	}
	
	public int getCurrentPage() 
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage) 
	{
		this.currentPage = currentPage;
	}

	public int getPages() 
	{
		return pages;
	}

	public void setPages(int pages) 
	{
		this.pages = pages;
	}

	public ArrayList<CounterEntity> getRecords() 
	{
		return records;
	}

	public void setRecords(ArrayList<CounterEntity> records) 
	{
		this.records = records;
	}

	public int getPageSize() 
	{
		return pageSize;
	}

	public void setPageSize(int pageSize) 
	{
		this.pageSize = pageSize;
	}
	
	public int getTotalRecords() 
	{
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) 
	{
		this.totalRecords = totalRecords;
	}
}