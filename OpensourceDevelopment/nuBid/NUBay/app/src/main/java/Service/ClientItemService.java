package Service;

import android.app.Activity;
import android.app.Service;
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
import java.net.Socket;
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
import Interfaces.ItemService;
import Models.Item;
import threads.ServiceTask;
import threads.ServiceThread;

/**
 * Created by kthompson on 1/26/2015.
 */
public class ClientItemService implements ItemService
{
    private static ClientItemService serviceInstance = new ClientItemService();

    public static ClientItemService getInstance() {
        return serviceInstance;
    }

    // LINE FORMAT
    // c|ItemID|ItemName|ItemDescription|ItemPrice|StartDate|EndDate|ImageID

    public void addItem(Item newItem) throws ItemServiceException
    {
        StringBuilder sb = new StringBuilder();
        DecimalFormat fmt = new DecimalFormat("#0.00");
        sb.append("c|").append(newItem.getId()).append("|");
        sb.append(newItem.getName()).append("|");
        sb.append(newItem.getDescription()).append("|");
        sb.append(fmt.format(newItem.getPrice())).append("|");
        sb.append(newItem.getStartDate()).append("|");
        sb.append(newItem.getEndDate()).append("|");
        sb.append(newItem.getImage());

//        ServiceThread t = new ServiceThread();
//        t.createRequest(sb.toString());
//        t.run();

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
    }

    public Item updateItem(Item newItem) throws ItemServiceException
    {
        DecimalFormat fmt = new DecimalFormat("#0.00");
        StringBuilder sb = new StringBuilder();
        sb.append("u|").append(newItem.getId()).append("|");
        sb.append(newItem.getName()).append("|");
        sb.append(newItem.getDescription()).append("|");
        sb.append(fmt.format(newItem.getPrice())).append("|");
        sb.append(newItem.getStartDate()).append("|");
        sb.append(newItem.getEndDate()).append("|");
        sb.append(newItem.getImage());

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
        return t.retrieveItem();
    }

    public void deleteItem(long id) throws ItemServiceException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("d|").append(id);

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
    }

    public List<Item> search(String query)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("s|").append(query);

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
        return t.searchItems();
    }

    public long getId()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("i");

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
        return t.getNextId();
    }

    public Item bid(long id, BigDecimal bidIncrease) throws ItemServiceException
    {
        Item ret = findItem(id);
        ret.increaseBid(bidIncrease);
        return updateItem(ret);
    }

    public Item findItem(long id)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("f|").append(id);

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
        return t.retrieveItem();
    }

    public long findItemId(String itemName)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("n|").append(itemName);

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
        return t.getIdByName();
    }

    public void clear()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("z|");

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
    }
}
