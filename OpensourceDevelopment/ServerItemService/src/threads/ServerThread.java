package threads;

import headers.Header;
import items.ItemBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;

import exceptions.ItemBuildException;
import exceptions.ItemDateException;
import exceptions.ItemPriceException;
import exceptions.ItemServiceException;
import models.Item;
import service.ItemService;

public class ServerThread implements Runnable 
{
	private Socket s;
	private ItemService service;
	
	public ServerThread(Socket socket, ItemService itemService)
	{
		s = socket;
		service = itemService;
	}
	
	private void ReceiveMessage(BufferedReader reader) throws IOException, ItemServiceException
	{
		String line = reader.readLine();
		System.out.println(line);
		String[] components = line.split("\\|");
		Item newItem = null;
		PrintWriter socketWriter = new PrintWriter(s.getOutputStream(),true);
		StringBuilder sb = new StringBuilder();
		Header h;
		switch(components[0].charAt(0))
		{
		case 'c': // create item
			newItem = new Item(Long.parseLong(components[1]),components[2],components[3],new BigDecimal(Double.parseDouble(components[4])),
					components[5],components[6],Integer.parseInt(components[7]));
			service.addItem(newItem);
			h = Header.OKAY;
			socketWriter.println(h.getHeader());
			socketWriter.println(h.getNumElements(0));
			break;
		case 'u': // update item
			try
			{
				newItem = ItemBuilder.createItem(Long.parseLong(components[1]),components[2], components[3], components[4], components[5], components[6], 0);
			}
			catch(ItemDateException e)
			{
				h = Header.DATE_ERROR;
			}
			catch(ItemPriceException e)
			{
				h = Header.PRICE_ERROR;
			}
			catch(ItemBuildException e)
			{
				h = Header.DATE_AND_PRICE_ERROR;
			}
			if(newItem != null)
			{
				if(newItem.getId() < service.getCount())
				{
					try
					{
						service.updateItem(newItem);
						h = Header.OKAY;
						socketWriter.println(h.getHeader());
						socketWriter.println(h.getNumElements(1));
					}
					catch(ItemServiceException e)
					{
						h = Header.ITEM_SERVICE_ERROR;
						socketWriter.println(h.getHeader());
						socketWriter.println(h.getNumElements(0));
					}
				}
				else
				{
					h = Header.ITEM_SERVICE_ERROR;
					socketWriter.println(h.getHeader());
					socketWriter.println(h.getNumElements(0));
				}
			}
			else
			{
				h = Header.ITEM_SERVICE_ERROR;
				socketWriter.println(h.getHeader());
				socketWriter.println(h.getNumElements(0));
			}
			if(h == Header.OKAY)
			{
				sb = new StringBuilder();
				sb.append(newItem.getId()).append("|");
				sb.append(newItem.getName()).append("|");
				sb.append(newItem.getDescription()).append("|");
				sb.append(newItem.getPrice()).append("|");
				sb.append(newItem.getStartDate()).append("|");
				sb.append(newItem.getEndDate()).append("|");
				sb.append(newItem.getImage());
				
				socketWriter.println(sb.toString());
			}
			break;
		case 'd': // delete item
			try
			{
				service.deleteItem(Long.parseLong(components[1]));
			}
			catch(ItemServiceException e)
			{
				
			}
			h = Header.OKAY;
			socketWriter.println(h.getHeader());
			socketWriter.println(h.getNumElements(0));
			break;
		case 's': // search with query
			List<Item> results = service.search(components[1]);
			h = Header.OKAY;
			socketWriter.println(h.getHeader());
			socketWriter.println(h.getNumElements(results.size()));
			//String secondLine = "";
			for(Item i : results)
			{
				sb = new StringBuilder();
				sb.append(i.getId()).append("|");
				sb.append(i.getName()).append("|");
				sb.append(i.getDescription()).append("|");
				sb.append(i.getPrice()).append("|");
				sb.append(i.getStartDate()).append("|");
				sb.append(i.getEndDate()).append("|");
				sb.append(i.getImage()).append("\n");
				String secondLine = sb.toString();
				socketWriter.println(secondLine);
			}
			//socketWriter.println("");//secondLine);
			break;
		case 'f': // find item by id
			Long desiredID = Long.parseLong(components[1]);
			Item ret = null;
			if(desiredID < service.getCount())
			{
				ret = service.findItem(desiredID);
				h = Header.OKAY;
				
				sb = new StringBuilder();
				sb.append(ret.getId()).append("|");
				sb.append(ret.getName()).append("|");
				sb.append(ret.getDescription()).append("|");
				sb.append(ret.getPrice()).append("|");
				sb.append(ret.getStartDate()).append("|");
				sb.append(ret.getEndDate()).append("|");
				sb.append(ret.getImage());
				socketWriter.println(h.getHeader());
				socketWriter.println(h.getNumElements(1));
				socketWriter.println(sb.toString());
			}
			else
			{
				h = Header.ITEM_SERVICE_ERROR;
				socketWriter.println(h.getHeader());
				socketWriter.println(h.getNumElements(0));
			}
			break;
		case 'n': // find id by name
			long id = service.findItemId(components[1]);
			if(id == -1)
			{
				h = Header.ITEM_SERVICE_ERROR;
			}
			else
			{
				h = Header.OKAY;
			}
			socketWriter.println(h.getHeader());
			socketWriter.println(h.getNumElements(1));
			socketWriter.println(id+"");
			break;
		case 'i': // get next id value
			long newId = service.getId();
			h = Header.OKAY;
			socketWriter.println(h.getHeader());
			socketWriter.println(h.getNumElements(1));
			socketWriter.println(newId+"");
			break;
		case 'b': // build item
			try
			{
				newItem = service.buildItem(Long.parseLong(components[1]), components[2], components[3], components[4], components[5], components[6], 0);
				h = Header.OKAY;
			}
			catch(ItemDateException e)
			{
				h = Header.DATE_ERROR;
			}
			catch(ItemPriceException e)
			{
				h = Header.PRICE_ERROR;
			}
			catch(ItemBuildException e)
			{
				h = Header.DATE_AND_PRICE_ERROR;
			}
			socketWriter.println(h.getHeader());
			if(h == Header.OKAY)
			{
				sb = new StringBuilder();
				sb.append(newItem.getId()).append("|");
				sb.append(newItem.getName()).append("|");
				sb.append(newItem.getDescription()).append("|");
				sb.append(newItem.getPrice()).append("|");
				sb.append(newItem.getStartDate()).append("|");
				sb.append(newItem.getEndDate()).append("|");
				sb.append(newItem.getImage());
				
				socketWriter.println(h.getNumElements(1));
				socketWriter.println(sb.toString());
			}
			else
			{
				socketWriter.println(h.getNumElements(0));
			}
			break;
		}		
	}
	
	@Override
	public void run() 
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			ReceiveMessage(reader);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ItemServiceException e)
		{
			e.printStackTrace();
		}
	}
}
