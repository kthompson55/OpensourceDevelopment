package Models;

import android.media.Image;

import java.math.BigDecimal;

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
    private int imageResourceId;

    public Item(String name, String description, int imgID)
    {
        id = id_counter++;
        itemBidPrice = new BigDecimal(0);
        itemName = name;
        itemDescription = description;
        imageResourceId = imgID;
    }

    public long getId()
    {
        return id;
    }

    public void increaseBid(BigDecimal increase)
    {
        itemBidPrice.add(increase);
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

    public int getImage()
    {
        return imageResourceId;
    }
}
