package com.example.kthompson.nubay;

import android.test.ActivityTestCase;

import java.math.BigDecimal;

import Exceptions.ItemBuildException;
import Exceptions.ItemServiceException;
import Models.Item;
import Service.ItemBuilder;
import Service.ItemService;

/**
 * Created by kthompson on 3/12/2015.
 */
public class PersistenceTest extends ActivityTestCase
{
    public void testAddValidItem()
    {
        Item validItem = new Item(ItemService.getInstance().getID(),"Valid","An item that is valid",new BigDecimal(0),"05/08/2015","07/12/2015",R.drawable.nubay);
        try
        {
            ItemService.getInstance().addItem(validItem);
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
            Item invalidItem = ItemBuilder.createItem("Invalid", "Invalid item", "0", "05/08/2015", "04/01/2015");
            ItemService.getInstance().addItem(invalidItem);
            fail("Invalid item was added");
        }
        catch(ItemBuildException e)
        {

        }
        catch (ItemServiceException e)
        {

        }
    }

    private void generateDummyState()
    {
        clearDummyState();
        try
        {
            ItemService.getInstance().addItem(new Item(ItemService.getInstance().getID(), "Penguin", "A dapper bird", new BigDecimal(350), "07/05/2015", "08/23/2016", R.drawable.emporerpenguin));
            ItemService.getInstance().addItem(new Item(ItemService.getInstance().getID(), "Falcon Punch", "A refreshing beverage for your face", new BigDecimal(3.50),"01/01/1000","12/25/3500",R.drawable.falconpunch));
        }
        catch(ItemServiceException e)
        {

        }
    }

    private void clearDummyState()
    {
        ItemService.getInstance().clear();
    }

    public void testDeleteValidItem()
    {
        generateDummyState();
        try
        {
            ItemService.getInstance().deleteItem(0);
        }
        catch(ItemServiceException e)
        {
            fail("Couldn't delete valid item");
        }
    }

    public void testDeleteInvalidItem()
    {
        generateDummyState();
        try
        {
            ItemService.getInstance().deleteItem(3);
        }
        catch(ItemServiceException e)
        {
            fail("loud failure");
        }
    }

    public void testUpdateValidItem()
    {
        generateDummyState();
        try
        {
            Item queueItem = ItemService.getInstance().findItem(0);
            Item updateItem = ItemBuilder.createItem(queueItem.getName(),queueItem.getDescription(), queueItem.getPrice().toString(), queueItem.getStartDate(),queueItem.getEndDate());
            updateItem.setId(queueItem.getId());

            ItemService.getInstance().updateItem(updateItem);
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
        generateDummyState();
        try
        {
            Item queueItem = ItemService.getInstance().findItem(0);
            Item updateItem = ItemBuilder.createItem(queueItem.getName(),queueItem.getDescription(), queueItem.getPrice().toString(), queueItem.getStartDate(),queueItem.getEndDate());
            updateItem.setId(3);

            ItemService.getInstance().updateItem(updateItem);
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