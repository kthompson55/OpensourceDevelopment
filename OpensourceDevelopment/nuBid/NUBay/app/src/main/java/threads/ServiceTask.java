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
 * blah
 */
public class ServiceTask extends AsyncTask<Item,Void,Void>
{
    private Item retrievedItem;
    private List<Item> searchResults;
    private String request;
    private Long retrievedID;
    private int numElements;

    public ServiceTask()
    {
        searchResults = new ArrayList<>();
    }

    public void createRequest(String threadRequest)
    {
        request = threadRequest;
    }

    public Item retrieveItem()
    {
        long startTime = System.currentTimeMillis();
        while(retrievedItem == null && System.currentTimeMillis() - startTime < 5000)
        {

        }
        return retrievedItem;
    }

    public Long retrieveID()
    {
        long startTime = System.currentTimeMillis();
        while(retrievedID == null && System.currentTimeMillis() - startTime < 5000)
        {

        }
        return retrievedID;
    }

    public List<Item> searchItems()
    {
        long startTime = System.currentTimeMillis();
        while(searchResults.size() < numElements+1 && System.currentTimeMillis() - startTime < 1000)
        {

        }
        return searchResults;
    }

    public Long getIdByName()
    {
        long startTime = System.currentTimeMillis();
        while(retrievedID == null && System.currentTimeMillis() - startTime < 5000)
        {

        }
        return retrievedID;
    }

    @Override
    protected Void doInBackground(Item... params)
    {
        char requestChar = request.charAt(0);
        try
        {
            Socket socket = new Socket("10.0.2.2", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            String[] components;
            long id;
            String name;
            String desc;
            BigDecimal price;
            String sDate;
            String eDate;
            int image;
//            switch(requestChar)
//            {
//                case 'c': // create item
//                    writer.println(request);
//                    break;
//                case 'u': // update item
//                    writer.println(request);
//                    line = reader.readLine();
//
//                    components = line.split("\\|");
//                    id = Long.parseLong(components[0]);
//                    name = components[1];
//                    desc = components[2];
//                    price = new BigDecimal(Double.parseDouble(components[3]));
//                    sDate = components[4];
//                    eDate = components[5];
//                    image = Integer.parseInt(components[6]);
//                    retrievedItem = new Item(id,name,desc,price,sDate,eDate,image);
//
//                    break;
//                case 'd': // delete item
//                    writer.println(request);
//                    break;
//                case 's': // search items
//                    writer.println(request);
//                    if(searchResults.size() > 0)
//                    {
//                        searchResults.clear();
//                    }
//                    while((line = reader.readLine()) != null)
//                    {
//                        components = line.split("\\|");
//                        if(components.length == 7)
//                        {
//                            id = Long.parseLong(components[0]);
//                            name = components[1];
//                            desc = components[2];
//                            price = new BigDecimal(Double.parseDouble(components[3]));
//                            sDate = components[4];
//                            eDate = components[5];
//                            image = Integer.parseInt(components[6]);
//                            searchResults.add(new Item(id, name, desc, price, sDate, eDate, image));
//                        }
//                        else break;
//                    }
//                    break;
//                case 'f': // find item by id
//                    writer.println(request);
//                    while(!(line = reader.readLine()).equals("300"))
//                    {
//                        components = line.split("\\|");
//                        id = Long.parseLong(components[0]);
//                        name = components[1];
//                        desc = components[2];
//                        price = new BigDecimal(Double.parseDouble(components[3]));
//                        sDate = components[4];
//                        eDate = components[5];
//                        image = Integer.parseInt(components[6]);
//                        retrievedItem = new Item(id, name, desc, price, sDate, eDate, image);
//                    }
//                    // being set after the request is made to get it. how to stall?
//
//                    break;
//                case 'n': // find id by name
//                    writer.println(request);
//                    line = reader.readLine();
//                    retrievedID = Long.parseLong(line);
//                    break;
//                case 'i': // get next id value
//                    writer.println(request);
//                    line = reader.readLine();
//                    nextID = Long.parseLong(line);
//                    break;
//                case 'z':
//                    writer.println(request);
//                    line = reader.readLine();
//                    break;
//            }
            writer.println(request);
            int headerID = Integer.parseInt(reader.readLine());
            String numElementsLine = reader.readLine();

            try
            {
                numElements = Integer.parseInt(numElementsLine);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Debug");
            }
            if(headerID == 300)
            {
                if (numElements == 1)
                {
                    line = reader.readLine();
                    try
                    {
                        retrievedID = Long.parseLong(line);
                    }
                    catch (NumberFormatException e)
                    {
                        components = line.split("\\|");
                        id = Long.parseLong(components[0]);
                        name = components[1];
                        desc = components[2];
                        price = new BigDecimal(Double.parseDouble(components[3]));
                        sDate = components[4];
                        eDate = components[5];
                        image = Integer.parseInt(components[6]);
                        retrievedItem = new Item(id, name, desc, price, sDate, eDate, image);
                        if(requestChar == 's')
                        {
                            System.out.println("AAAAAAAAAAAAAAAAAAAAA");
                            searchResults.add(retrievedItem);
                        }
                    }
                }
                else if (numElements > 1)
                {
                    for (int i = 0; i < numElements; i++)
                    {
                        line = reader.readLine(); // second line is not sent????
                        System.out.println("AAAAAAAAAAAAAAAAAAAAA" + line);
                        if(line.equals(""))
                        {
                            i--;
                        }
                        else {
                            System.out.println("AAAAAAAAAAAAAAAAAAAAA LINE:" + i + line);
                            System.out.println("AAAAAAAAAAAAAAAAAAAAA LINE SIZE:" + line.length());
                            components = line.split("\\|");
                            id = Long.parseLong(components[0]);
                            name = components[1];
                            desc = components[2];
                            price = new BigDecimal(Double.parseDouble(components[3]));
                            sDate = components[4];
                            eDate = components[5];
                            image = Integer.parseInt(components[6]);
                            retrievedItem = new Item(id, name, desc, price, sDate, eDate, image);
                            searchResults.add(retrievedItem);
                        }
                    }
                }
            }
            else
            {
                //retrievedID = null;
                //retrievedItem = null;
                //searchResults.clear();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
