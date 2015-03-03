package com.example.kthompson.nubay;

import android.test.ActivityTestCase;

import java.math.BigDecimal;
import java.util.List;

import Models.Item;
import Service.ItemService;

public class ServiceTest extends ActivityTestCase
{
    public void testSingleQuerySearch()
    {
        Item fPunch = ItemService.getInstance().findItem(2);
        List<Item> items = ItemService.getInstance().search("Falcon");
        assert(items.contains(fPunch));
    }

    public void testAndQuerySearch()
    {
        Item fPunch = ItemService.getInstance().findItem(2);
        List<Item> items = ItemService.getInstance().search("Falcon AND Punch");
        assert(items.contains(fPunch));
    }

    public void testOrQuerySearch()
    {
        Item fPunch = ItemService.getInstance().findItem(2);
        Item penguin = ItemService.getInstance().findItem(0);
        List<Item> items = ItemService.getInstance().search("Falcon OR Penguin");
        assertEquals(2,items.size());
        boolean fPunchIn = items.get(0).getName().equals(fPunch.getName()) || items.get(1).getName().equals(fPunch.getName());
        boolean penguinIn = items.get(0).getName().equals(penguin.getName()) || items.get(1).getName().equals(penguin.getName());
        assert(fPunchIn && penguinIn);
    }

    public void testOrAndAndQuerySearch()
    {
        Item fPunch = ItemService.getInstance().findItem(2);
        Item penguin = ItemService.getInstance().findItem(0);
        List<Item> items = ItemService.getInstance().search("Falcon AND Punch OR Penguin");
        assertEquals(2,items.size());
        boolean fPunchIn = items.get(0).getName().equals(fPunch.getName()) || items.get(1).getName().equals(fPunch.getName());
        boolean penguinIn = items.get(0).getName().equals(penguin.getName()) || items.get(1).getName().equals(penguin.getName());
        assert(fPunchIn && penguinIn);
    }

    public void testBid()
    {
        Item fPunch = ItemService.getInstance().findItem(2);
        BigDecimal beforeBid = fPunch.getPrice();
        fPunch = ItemService.getInstance().bid(fPunch.getId(),new BigDecimal(5));
        assert(beforeBid.compareTo(fPunch.getPrice()) > 0);
    }
}
