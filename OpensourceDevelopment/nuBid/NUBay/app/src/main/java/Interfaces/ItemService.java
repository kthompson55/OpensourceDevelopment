package Interfaces;

import java.math.BigDecimal;
import java.util.List;

import Exceptions.ItemBuildException;
import Exceptions.ItemServiceException;
import Models.Item;

public interface ItemService 
{	
	public void addItem(Item newItem) throws ItemServiceException;
	public Item updateItem(Item newItem) throws ItemServiceException;
	public void deleteItem(long id) throws ItemServiceException;
	public List<Item> search(String query);
    public long getId();
	public Item bid(long id, BigDecimal bidIncrease) throws ItemServiceException;
	public Item findItem(long id);
    public Long findItemId(String name);
    public void clear();
    public Item buildItem(long id, String name, String desc, String price, String sDate, String eDate, int imageID) throws ItemBuildException;
}
