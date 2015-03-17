package com.example.kthompson.nubay;

import android.test.ActivityTestCase;

import java.math.BigDecimal;
import java.util.List;

import Exceptions.ItemServiceException;
import Interfaces.ItemService;
import Models.Item;
import Service.ClientItemService;

public class ServiceTest extends ActivityTestCase
{
    private boolean stateGenerated = false;

    private void generateState()
    {
        Long fPunchIn = ClientItemService.getInstance().findItemId("Falcon Punch");
        Long penguinIn = ClientItemService.getInstance().findItemId("Penguin");
        boolean noItems = fPunchIn == null && penguinIn == null;
        if(noItems)
        {
            try
            {
                ClientItemService.getInstance().addItem(new Item(ClientItemService.getInstance().getId(), "Falcon Punch", "A refreshing beverage for your face", new BigDecimal(3.5),
                        "11/11/1111", "12/12/1212", R.drawable.falconpunch));
                ClientItemService.getInstance().addItem(new Item(ClientItemService.getInstance().getId(), "Penguin", "A dapper bird for dapper occasions", new BigDecimal(375),
                        "11/11/1111", "12/12/1212", R.drawable.emporerpenguin));
            }
            catch(ItemServiceException e)
            {
                e.printStackTrace();
            }
            stateGenerated = true;
        }
    }

    public void testSingleQuerySearch()
    {
        generateState();
        Item fPunch = ClientItemService.getInstance().findItem(ClientItemService.getInstance().findItemId("Falcon Punch"));
        List<Item> items = ClientItemService.getInstance().search("Falcon");
        assert(items.contains(fPunch));
    }

    public void testAndQuerySearch()
    {
        System.out.println("AAAAAAAAAAAAAAAAAAAAA");
        generateState();
        Long id = ClientItemService.getInstance().findItemId("Falcon Punch");
        System.out.println("AAAAAAAAAAAAAAAAAAAAA" + id);
        if(id == null)
        {
            fail("No punch");
        }
        Item fPunch = ClientItemService.getInstance().findItem(id);
        System.out.println("AAAAAAAAAAAAAAAAAAAAA" + fPunch.getName());
        List<Item> items = ClientItemService.getInstance().search("Falcon AND Punch");
        System.out.println("AAAAAAAAAAAAAAAAAAAAA TEST" + items.size());
        boolean contains = false;
        System.out.println(items.size());
        System.out.println(fPunch.getId());
        for(Item i : items)
        {
            if(i.equals(fPunch))
            {
                contains = true;

            }
        }
        assertEquals(true,contains);
    }

    public void testOrQuerySearch()
    {
        generateState();
        Long fID = ClientItemService.getInstance().findItemId("Falcon Punch");
        Long pID = ClientItemService.getInstance().findItemId("Penguin");
        if(fID == null || pID == null)
        {
            fail("Null IDs");
        }
        Item fPunch = ClientItemService.getInstance().findItem(fID);
        Item penguin = ClientItemService.getInstance().findItem(pID);
        List<Item> items = ClientItemService.getInstance().search("Falcon OR Penguin");
        boolean fPunchIn = items.contains(fPunch);
        boolean penguinIn = items.contains(penguin);
        assertEquals(true,fPunchIn);
        assertEquals(true,penguinIn);
    }

    public void testOrAndAndQuerySearch()
    {
        generateState();
        Long fPunchID = ClientItemService.getInstance().findItemId("Falcon Punch");
        Long penguinID = ClientItemService.getInstance().findItemId("Penguin");
        if(fPunchID == null || penguinID == null)
        {
            fail("Null IDs");
        }
        Item fPunch = ClientItemService.getInstance().findItem(fPunchID);
        Item penguin = ClientItemService.getInstance().findItem(penguinID);
        List<Item> items = ClientItemService.getInstance().search("Falcon AND Punch OR Penguin");

        boolean fPunchIn = items.contains(fPunch);
        boolean penguinIn = items.contains(penguin);
        assert(fPunchIn && penguinIn);
    }

    public void testBid()
    {
        generateState();
        try
        {
            Item fPunch = ClientItemService.getInstance().findItem(ClientItemService.getInstance().findItemId("Falcon Punch"));
            BigDecimal beforeBid = fPunch.getPrice();
            fPunch = ClientItemService.getInstance().bid(fPunch.getId(), new BigDecimal(5));
            assert (beforeBid.compareTo(fPunch.getPrice()) < 0);
        }
        catch(ItemServiceException e)
        {
            fail("Bid failed");
        }
    }
}
