package service;

import java.math.BigDecimal;
import java.util.List;

import exceptions.ItemServiceException;
import models.Item;

public interface ItemService 
{	
	public void addItem(Item newItem) throws ItemServiceException;
	public Item updateItem(Item newItem) throws ItemServiceException;
	public void deleteItem(long id) throws ItemServiceException;
	public List<Item> search(String query);
	public long getId();
	public Item bid(long id, BigDecimal bidIncrease) throws ItemServiceException;
	public Item findItem(long id);
	public long findItemId(String name);
	public void clear();
}