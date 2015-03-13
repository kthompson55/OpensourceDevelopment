package Service;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.example.kthompson.nubay.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import Exceptions.ItemServiceException;
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

    private File file;

    // LINE FORMAT
    // c|ItemID|ItemName|ItemDescription|ItemPrice|StartDate|EndDate|ImageID

    private ItemService()
    {
        items = new HashMap<>();
        keyCount = 0;

        try
        {
            file = new File("/sdcard/itemlog.txt");
            InputStream fileStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
            String line;
            while((line = reader.readLine()) != null)
            {
                String[] itemArgs = line.split("\\|");
                String itemName = itemArgs[2];
                String itemDescription = itemArgs[3];
                BigDecimal itemPrice = new BigDecimal(Double.parseDouble(itemArgs[4]));
                String startDate = itemArgs[5];
                String endDate = itemArgs[6];
                int imgID = Integer.parseInt(itemArgs[7]);

                Item i = new Item(getID(),itemName,itemDescription,itemPrice,startDate,endDate,imgID);

                if(line.startsWith("c")) // create item
                {
//                    addItem(i);
                    items.put(i.getId(),i);
                }
                else if(line.startsWith("u")) // update item
                {
                    items.put(i.getId(), i);
                }
                else if(line.startsWith("d")) // delete item
                {
                    items.remove(i.getId());
                }
            }
        }
        catch(FileNotFoundException e)
        {
            try
            {
                file.createNewFile();
            }
            catch(IOException f)
            {
                f.printStackTrace();
            }
        }
        catch(IOException e)
        {
            items.put(keyCount,new Item(getID(),"Bad Item", "Invalid Item is Invalid",new BigDecimal(5.00),"01/15/2015","03/15/2015",R.drawable.broken));
            items.put(keyCount,new Item(getID(),"Penguin","A fancy bird for fancy occasions",new BigDecimal(5.00),"01/15/2015", "03/15/2015", R.drawable.emporerpenguin));
            items.put(keyCount,new Item(getID(),"Smash Ball","Unlock your full potential!",new BigDecimal(5.00),"01/15/2015", "03/15/2015", R.drawable.smashball));
            items.put(keyCount,new Item(getID(),"Falcon Punch","A refreshing beverage to quench your thirst",new BigDecimal(5.00),"01/15/2015", "03/15/2015", R.drawable.falconpunch));
            items.put(keyCount,new Item(getID(),"Trap","Don't say you weren't warned",new BigDecimal(5.00),"01/15/2015", "03/15/2015",R.drawable.trap));
        }
    }

    public long getID()
    {
        return keyCount;
    }

    public long findItemId(String name)
    {
        for(Item i : items.values())
        {
            if(i.getName().equals(name))
            {
                return i.getId();
            }
        }
        return -1;
    }

    public void addItem(Item newItem) throws ItemServiceException
    {
        items.put(keyCount,newItem);
        newItem.setId(keyCount);

        try(PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file))))
        {
            DecimalFormat fmt = new DecimalFormat("#0.00");
            StringBuilder sb = new StringBuilder();
            sb.append("c|").append(newItem.getId()).append("|").append(newItem.getName()).append("|").append(newItem.getDescription()).append("|").append(fmt.format(newItem.getPrice())).append("|");
            sb.append(newItem.getStartDate()).append("|").append(newItem.getEndDate()).append("|").append(newItem.getImage());
            writer.write(sb.toString());
        }
        catch(IOException e)
        {
            throw new ItemServiceException();
        }

        keyCount++;
    }

    public Item updateItem(Item newItem) throws ItemServiceException
    {
        Item ret = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("u|").append(newItem.getId()).append("|").append(newItem.getName()).append("|").append(newItem.getDescription()).append("|").append(newItem.getPrice()).append("|");
            sb.append(newItem.getStartDate()).append("|").append(newItem.getEndDate()).append("|").append(newItem.getImage());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(sb.toString());
            ret = newItem;

            items.put(ret.getId(),ret);
        }
        catch(IOException e)
        {
            throw new ItemServiceException();
        }
        return ret;
    }

    public void deleteItem(long id) throws ItemServiceException
    {
        if(items.containsKey(id))
        {
            items.remove(id);
            try
            {
                StringBuilder sb = new StringBuilder();
                sb.append("d|").append(id);
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
                writer.write(sb.toString());
            }
            catch(IOException e)
            {
                throw new ItemServiceException();
            }
        }
        // else, silent failure
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

    public Item findItem(long id)
    {
        Item ret = null;
        if(items.keySet().contains(id))
        {
            ret = items.get(id);
        }
        return ret;
    }

    public void clear()
    {
        Iterator<Long> it = items.keySet().iterator();
        List<Long> filler = new ArrayList<>();
        while(it.hasNext())
        {
            filler.add(it.next());
        }
        for(Long l : filler)
        {
            items.remove(l);
        }
        keyCount = 0;
    }
}
