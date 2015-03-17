package threads;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Models.Item;

/**
 * Created by kthompson on 3/15/2015.
 */
public class ServiceThread implements Runnable
{
    private Item retrievedItem;
    private List<Item> searchResults;
    private String request;
    private long nextID;
    private long retrievedID;

    public ServiceThread()
    {
        searchResults = new ArrayList<>();
    }

    public void createRequest(String threadRequest)
    {
        request = threadRequest;
    }

    public Item retrieveItem()
    {
        return retrievedItem;
    }

    public List<Item> searchItems()
    {
        return searchResults;
    }

    public long getNextId()
    {
        return nextID;
    }

    public long getIdByName()
    {
        return retrievedID;
    }

    @Override
    public void run()
    {
        char requestChar = request.charAt(0);
        try
        {
            Socket socket = new Socket("localhost", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            switch(requestChar)
            {
                case 'c': // create item
                    writer.write(request);
                    break;
                case 'u': // update item
                    writer.write(request);
                    while((line = reader.readLine()) != null)
                    {
                        String[] components = line.split("|");
                        long id = Long.parseLong(components[0]);
                        String name = components[1];
                        String desc = components[2];
                        BigDecimal price = new BigDecimal(Double.parseDouble(components[3]));
                        String sDate = components[4];
                        String eDate = components[5];
                        int image = Integer.parseInt(components[6]);
                        retrievedItem = new Item(id,name,desc,price,sDate,eDate,image);
                    }
                    break;
                case 'd': // delete item
                    writer.write(request);
                    break;
                case 's': // search items
                    writer.write(request);
                    if(searchResults.size() > 0)
                    {
                        searchResults.clear();
                    }
                    while((line = reader.readLine()) != null)
                    {
                        String[] components = line.split("|");
                        long id = Long.parseLong(components[0]);
                        String name = components[1];
                        String desc = components[2];
                        BigDecimal price = new BigDecimal(Double.parseDouble(components[3]));
                        String sDate = components[4];
                        String eDate = components[5];
                        int image = Integer.parseInt(components[6]);
                        searchResults.add(new Item(id,name,desc,price,sDate,eDate,image));
                    }
                    break;
                case 'f': // find item by id
                    writer.write(request);
                    while((line = reader.readLine()) != null)
                    {
                        String[] components = line.split("|");
                        long id = Long.parseLong(components[0]);
                        String name = components[1];
                        String desc = components[2];
                        BigDecimal price = new BigDecimal(Double.parseDouble(components[3]));
                        String sDate = components[4];
                        String eDate = components[5];
                        int image = Integer.parseInt(components[6]);
                        retrievedItem = new Item(id,name,desc,price,sDate,eDate,image);
                    }
                    break;
                case 'n': // find id by name
                    writer.write(request);
                    while((line = reader.readLine()) != null)
                    {
                        retrievedID = Long.parseLong(line);
                    }
                    break;
                case 'i': // get next id value
                    writer.write(request);
                    while((line = reader.readLine()) != null)
                    {
                        nextID = Long.parseLong(line);
                    }
                    break;
            }
            writer.close();
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
