package com.example.kthompson.nubay;

import android.test.ActivityTestCase;

import Exceptions.ItemBuildException;
import Exceptions.ItemServiceException;
import Models.Item;
import Service.ClientItemService;

/**
 * Created by kthompson on 2/24/2015.
 */
public class CreateTest extends ActivityTestCase
{
    public void testInvalidDate()
    {
        try
        {
            // 15 becomes March of the next year. seriously.
            Item newItem = ClientItemService.getInstance().buildItem(ClientItemService.getInstance().getId(),"Test Item", "Test Desc", "$3.50", "15.02.1023", "02.03.2016",0);
            if(newItem == null) throw new ItemBuildException("Pass");
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
            Item newItem = ClientItemService.getInstance().buildItem(ClientItemService.getInstance().getId(), "Test Item", "Test Desc", "$3s.7a", "05.01.2015", "02.03.2016", 0);
            if(newItem == null) throw new ItemBuildException("Pass");
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
            newItem = ClientItemService.getInstance().buildItem(ClientItemService.getInstance().getId(),"addToItemService", "Test Desc", "$3.50", "05.01.2015", "02.03.2016",0);
            try
            {
                ClientItemService.getInstance().addItem(newItem);
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
        assertEquals(newItem,ClientItemService.getInstance().findItem(newItem.getId()));
    }
}
