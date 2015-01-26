package Models;

import android.media.Image;

/**
 * Created by kthompson on 1/23/2015.
 */
public class Item
{
    private int itemBidPrice;
    private String itemName;
    private String itemDescription;
    private int imageResourceId;

    public Item(String name, String description, int imgID)
    {
        itemBidPrice = 0;
        itemName = name;
        itemDescription = description;
        imageResourceId = imgID;
    }

    public void increaseBid()
    {
        itemBidPrice++;
    }

    public int getPrice()
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
