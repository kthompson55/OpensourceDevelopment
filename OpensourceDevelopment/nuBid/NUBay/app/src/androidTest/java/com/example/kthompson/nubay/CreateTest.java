package com.example.kthompson.nubay;

import android.test.ActivityTestCase;

import Exceptions.ItemBuildException;
import Exceptions.ItemServiceException;
import Models.Item;
import Service.ItemBuilder;
import Service.ItemService;

/**
 * Created by kthompson on 2/24/2015.
 */
public class CreateTest extends ActivityTestCase
{
    public void testValidItem()
    {
        try
        {
            Item newItem = ItemBuilder.createItem("Test Item", "Test Desc", "$3.50", "05.01.2015", "02.03.2016");
        }
        catch(ItemBuildException e)
        {
            fail("Real item was not created");
        }
    }

    public void testInvalidDate()
    {
        try
        {
            // 15 becomes March of the next year. seriously.
            Item newItem = ItemBuilder.createItem("Test Item", "Test Desc", "$3.50", "15.02.1023", "02.03.2016");
            fail("Bad item date was created");
        }
        catch(ItemBuildException e)
        {
            // success
        }
    }

    public void testInvalidPrice()
    {
        try
        {
            Item newItem = ItemBuilder.createItem("Test Item", "Test Desc", "$3s.7a", "05.01.2015", "02.03.2016");
            fail("Bad item price was created");
        }
        catch(ItemBuildException e)
        {
            // success
        }
    }

    public void testAddToItemService()
    {
        Item newItem = null;
        try
        {
            newItem = ItemBuilder.createItem("Test Item", "Test Desc", "$3.50", "05.01.2015", "02.03.2016");
            try
            {
                ItemService.getInstance().addItem(newItem);
            }
            catch(ItemServiceException e)
            {
                e.printStackTrace();
            }
        }
        catch(ItemBuildException e)
        {
            fail("Item addition failed");
        }
        assertEquals(newItem,ItemService.getInstance().findItem(newItem.getId()));
    }
}
