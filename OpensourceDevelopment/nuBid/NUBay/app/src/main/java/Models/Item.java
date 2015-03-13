package Models;

import android.media.Image;

import java.math.BigDecimal;

import Service.ItemService;

/**
 * Created by kthompson on 1/23/2015.
 */
public class Item
{
    private long id;
    private static int id_counter = 0;
    private BigDecimal itemBidPrice;
    private String itemName;
    private String itemDescription;
    private String startDate;
    private String endDate;
    private int imageResourceId;

    public Item(long setID, String name, String description, BigDecimal sPrice, String sDate, String eDate, int imgID)
    {
        id = setID;
        itemBidPrice = new BigDecimal(0);
        itemName = name;
        itemDescription = description;
        itemBidPrice = sPrice;
        startDate = sDate;
        endDate = eDate;
        imageResourceId = imgID;
    }

    public void setId(long itemID)
    {
        id = itemID;
    }

    public long getId()
    {
        return id;
    }

    public BigDecimal increaseBid(BigDecimal increase)
    {
        return itemBidPrice.add(increase);
    }

    public void setPrice(BigDecimal price)
    {
        itemBidPrice = price;
    }

    public BigDecimal getPrice()
    {
        return itemBidPrice;
    }

    public String getName()
    {
        return itemName;
    }

    public String getDescription()
    {
        return itemDescription;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public int getImage()
    {
        return imageResourceId;
    }
}
