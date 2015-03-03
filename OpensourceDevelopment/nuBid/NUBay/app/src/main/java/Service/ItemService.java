package Service;

import android.content.Context;
import android.content.res.Resources;

import com.example.kthompson.nubay.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import Models.Item;

/**
 * Created by kthompson on 1/26/2015.
 */
public class ItemService {
    private static ItemService serviceInstance = new ItemService();

    public static ItemService getInstance() {
        return serviceInstance;
    }

    private Map<Long, Item> items;
    private long keyCount;

    private ItemService()
    {
        keyCount = 0;
        items = new HashMap<>();
        items.put(keyCount,new Item("Bad Item", "Invalid Item is Invalid",new BigDecimal(5.00),"01/15/2015","03/15/2015",R.drawable.broken));
        keyCount++;
        items.put(keyCount,new Item("Penguin","A fancy bird for fancy occasions",new BigDecimal(5.00),"01/15/2015", "03/15/2015", R.drawable.emporerpenguin));
        keyCount++;
        items.put(keyCount,new Item("Smash Ball","Unlock your full potential!",new BigDecimal(5.00),"01/15/2015", "03/15/2015", R.drawable.smashball));
        keyCount++;
        items.put(keyCount,new Item("Falcon Punch","A refreshing beverage to quench your thirst",new BigDecimal(5.00),"01/15/2015", "03/15/2015", R.drawable.falconpunch));
        keyCount++;
        items.put(keyCount,new Item("Trap","Don't say you weren't warned",new BigDecimal(5.00),"01/15/2015", "03/15/2015",R.drawable.trap));
        keyCount++;
    }

    public Iterator<Item> getItems()
    {
        return items.values().iterator();
    }

    public Item getItemByName(String name)
    {
        Iterator<Item> i = getItems();
        while(i.hasNext())
        {
            Item next = i.next();
            if(next.getName().equals(name))
            {
                return next;
            }
        }
        return null;
    }

    public List<Item> search(String query)
    {
        List<Item> results = new ArrayList<Item>();

        String[] tokens = query.split(" ");
        Stack<String> operators = new Stack<String>();
        Queue<String> output = new PriorityQueue<String>();
        List<String> andBlocks = new ArrayList<String>();

        for(String token : tokens)
        {
            if(token.toLowerCase().equals("and"))
            {
                operators.push(token);
            }
            else if(token.toLowerCase().equals("or"))
            {
                String andStatement = output.poll() + ",";
                if(!operators.empty())
                {
                    while (operators.peek().equals("and"))
                    {
                        operators.pop();
                        andStatement += output.poll() + ",";
                        if(operators.empty()) break;
                    }
                }
                operators.push(token);
                andBlocks.add(andStatement);
            }
            else output.add(token);
        }
        andBlocks.add(output.poll() + ",");

        // Sue me.
        for(Item i : items.values())
        {
            boolean addedOnce = false;
            for(String terms : andBlocks)
            {
                String[] individualTerms = terms.split(",");
                for(String curTerm : individualTerms)
                {
                    boolean inName = i.getName().toLowerCase().contains(curTerm.toLowerCase());
                    boolean inDesc = i.getDescription().toLowerCase().contains(curTerm.toLowerCase());
                    addedOnce = addedOnce || (inName || inDesc);
                }
            }
            if(addedOnce)
            {
                results.add(i);
            }
        }

        return results;
    }

    public Item bid(long id, BigDecimal bidIncrease)
    {
        items.get(id).setPrice(items.get(id).increaseBid(bidIncrease));
        return items.get(id);
    }

    public void addItem(Item newItem)
    {
        items.put(keyCount,newItem);
        newItem.setId(keyCount);
        keyCount++;
    }

    public Item findItem(long id)
    {
        Item ret = null;
        if(items.keySet().contains(id))
        {
            ret = items.get(id);
        }
        return ret;
    }
}
