package Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import Exceptions.ItemBuildException;
import Exceptions.ItemServiceException;
import Interfaces.ItemService;
import Models.Item;
import threads.ServiceTask;

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
        return t.retrieveID();
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

    public Long findItemId(String itemName)
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

    @Override
    public Item buildItem(long id, String name, String desc, String price, String sDate, String eDate, int imageID) throws ItemBuildException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("b|").append(id).append("|");
        sb.append(name).append("|");
        sb.append(desc).append("|");
        sb.append(price).append("|");
        sb.append(sDate).append("|");
        sb.append(eDate).append("|");
        sb.append(imageID);

        ServiceTask t = new ServiceTask();
        t.createRequest(sb.toString());
        t.execute();
        return t.retrieveItem();
    }
}
