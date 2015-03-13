package com.example.kthompson.nubay;

import android.test.ActivityTestCase;

import java.math.BigDecimal;
import java.util.List;

import Exceptions.ItemServiceException;
import Models.Item;
import Service.ItemService;

public class ServiceTest extends ActivityTestCase
{
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

    public void testSingleQuerySearch()
    {
        generateDummyState();
        Item fPunch = ItemService.getInstance().findItem(1);
        List<Item> items = ItemService.getInstance().search("Falcon");
        assert(items.contains(fPunch));
        clearDummyState();
    }

    public void testAndQuerySearch()
    {
        generateDummyState();
        Item fPunch = ItemService.getInstance().findItem(1);
        List<Item> items = ItemService.getInstance().search("Falcon AND Punch");
        assert(items.contains(fPunch));
        clearDummyState();
    }

    public void testOrQuerySearch()
    {
        generateDummyState();
        Item fPunch = ItemService.getInstance().findItem(1);
        Item penguin = ItemService.getInstance().findItem(0);
        List<Item> items = ItemService.getInstance().search("Falcon OR Penguin");
        assertEquals(2,items.size());
        boolean fPunchIn = items.get(0).getName().equals(fPunch.getName()) || items.get(1).getName().equals(fPunch.getName());
        boolean penguinIn = items.get(0).getName().equals(penguin.getName()) || items.get(1).getName().equals(penguin.getName());
        assert(fPunchIn && penguinIn);
        clearDummyState();
    }

    public void testOrAndAndQuerySearch()
    {
        generateDummyState();
        Item fPunch = ItemService.getInstance().findItem(1);
        Item penguin = ItemService.getInstance().findItem(0);
        List<Item> items = ItemService.getInstance().search("Falcon AND Punch OR Penguin");
        assertEquals(2,items.size());
        boolean fPunchIn = items.get(0).getName().equals(fPunch.getName()) || items.get(1).getName().equals(fPunch.getName());
        boolean penguinIn = items.get(0).getName().equals(penguin.getName()) || items.get(1).getName().equals(penguin.getName());
        assert(fPunchIn && penguinIn);
        clearDummyState();
    }

    public void testBid()
    {
        generateDummyState();
        Item fPunch = ItemService.getInstance().findItem(1);
        BigDecimal beforeBid = fPunch.getPrice();
        fPunch = ItemService.getInstance().bid(fPunch.getId(),new BigDecimal(5));
        assert(beforeBid.compareTo(fPunch.getPrice()) < 0);
        clearDummyState();
    }
}
