package com.example.kthompson.nubay;

import android.test.ActivityTestCase;

import java.math.BigDecimal;

import Exceptions.ItemBuildException;
import Exceptions.ItemServiceException;
import Models.Item;
import Service.ClientItemService;

/**
 * Created by kthompson on 3/12/2015.
 */
public class PersistenceTest extends ActivityTestCase
{
    public void testAddValidItem()
    {
        Item validItem = new Item(ClientItemService.getInstance().getId(),"Valid","An item that is valid",new BigDecimal(0),"05/08/2015","07/12/2015",R.drawable.nubay);
        try
        {
            ClientItemService.getInstance().addItem(validItem);
        }
        catch (ItemServiceException e)
        {
            fail("Valid Item was not added");
        }
    }

    public void testAddInvalidItem()
    {
        try
        {
            Item invalidItem = ClientItemService.getInstance().buildItem(ClientItemService.getInstance().getId(),"Invalid", "Invalid item", "0", "05/08/2015", "04/01/2015",0);
            if(invalidItem == null) throw new ItemBuildException("pass");
            ClientItemService.getInstance().addItem(invalidItem);
            fail("Invalid item was added");
        }
        catch(ItemBuildException e)
        {

        }
        catch (ItemServiceException e)
        {

        }
    }

    public void testDeleteValidItem()
    {
        try
        {
            ClientItemService.getInstance().deleteItem(0);
        }
        catch(ItemServiceException e)
        {
            fail("Couldn't delete valid item");
        }
    }

    public void testDeleteInvalidItem()
    {
        try
        {
            ClientItemService.getInstance().deleteItem(100000);
        }
        catch(ItemServiceException e)
        {
            fail("loud failure");
        }
    }

    public void testUpdateValidItem()
    {
        try
        {
            Item queueItem = ClientItemService.getInstance().findItem(1);
            Item updateItem = ClientItemService.getInstance().buildItem(1,queueItem.getName(),queueItem.getDescription(),
                    queueItem.getPrice().toString(), "11.11.1111","12.12.1212",0);
            updateItem.setId(queueItem.getId());

            ClientItemService.getInstance().updateItem(updateItem);
        }
        catch(ItemServiceException e)
        {
            fail("Couldn't update valid item");
        }
        catch(ItemBuildException e)
        {
            fail("Didn't even get to updating item");
        }
    }

    public void testUpdateInvalidItem()
    {
        try
        {
            Item queueItem = ClientItemService.getInstance().findItem(1);
            Item updateItem = ClientItemService.getInstance().buildItem(ClientItemService.getInstance().getId(),queueItem.getName(),queueItem.getDescription(),
                    queueItem.getPrice().toString(), "11.11.1111","12.12.1212",0);
            if(updateItem == null) throw new ItemBuildException("pass");
            updateItem.setId(8);

            ClientItemService.getInstance().updateItem(updateItem);
        }
        catch(ItemServiceException e)
        {

        }
        catch(ItemBuildException e)
        {
            fail("Didn't even get to updating item");
        }
    }
}
