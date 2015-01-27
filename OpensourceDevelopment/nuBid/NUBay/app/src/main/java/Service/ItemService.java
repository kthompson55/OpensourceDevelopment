package Service;

import android.content.Context;
import android.content.res.Resources;

import com.example.kthompson.nubay.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private List<Item> items;
    private Context context;

    private ItemService()
    {
        items = new ArrayList<Item>();
        items.add(new Item("Penguin","A fancy bird for fancy occasions", R.drawable.emporerpenguin));
        items.add(new Item("Smash Ball","Unlock your full potential!", R.drawable.smashball));
        items.add(new Item("Falcon Punch","A refreshing beverage to quench your thirst", R.drawable.falconpunch));
        items.add(new Item("Trap","Don't say you weren't warned",R.drawable.trap));
    }

    public List<Item> getItems()
    {
        return items;
    }

    public List<Item> search(String query)
    {
        List<Item> results = new ArrayList<Item>();

        String[] tokens = query.split(" ");
        Stack<String> operators = new Stack<String>();
        Queue<String> output = new PriorityQueue<String>();

        for(String token : tokens)
        {
            if(token.toLowerCase().equals("and"))
            {
                operators.push(token);
            }
            else if(token.toLowerCase().equals("or"))
            {
                while(operators.peek().equals("and"))
                {
                    operators.pop();
                }
                operators.push(token);
            }
            else output.add(token);
        }

        // each block of ands will contain a single string of the tags, separated by commas
        List<String> andBlocks = new ArrayList<String>();
        while(!output.isEmpty())
        {
            String builder = "";
            if(!operators.empty()) {
                while (operators.pop() != "OR") {
                    builder += output.poll() + ",";
                }
            }
            else
            {
                for(String o : output)
                {
                    builder += output.poll() + ",";
                }
            }

            andBlocks.add(builder);
        }

        // Sue me.
        for(Item i : items)
        {
            boolean willAdd = true;
            for(String terms : andBlocks)
            {
                String[] individualTerms = terms.split(",");
                for(String curTerm : individualTerms)
                {
                    willAdd = willAdd && i.getName().contains(curTerm) || i.getDescription().contains(curTerm);
                }
            }
            if(willAdd)
            {
                results.add(i);
            }
        }

        return results;
    }

    public Item bid(long id, BigDecimal bidIncrease)
    {
        Item returnItem = findItem(id);
        returnItem.increaseBid(bidIncrease);
        return returnItem;
    }

    private Item findItem(long id)
    {
        for(Item i : items)
        {
            if(i.getId() == id)
            {
                return i;
            }
        }
        return null;
    }
}
