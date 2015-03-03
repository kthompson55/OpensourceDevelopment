package com.example.kthompson.nubay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.regex.Pattern;

import Exceptions.ItemBuildException;
import Exceptions.ItemDateException;
import Exceptions.ItemPriceException;
import Interfaces.ViewListener;
import Models.CreateItemModel;
import Models.Item;
import Service.ItemBuilder;
import Service.ItemService;
import Views.CreateItemView;

/**
 * Created by kthompson on 2/5/2015.
 */
public class CreateActivity extends Activity
{
    private CreateItemView view;
    private Context con;

    private ViewListener listener = new ViewListener()
    {
        @Override
        public void onTransferPress(long itemID)
        {
            Intent i = new Intent(con,SearchActivity.class);
            startActivity(i);
        }

        @Override
        public void onPagePress(String itemName, String itemDesc, String startPrice, String startDate, String endDate)
        {
            try
            {
                Item newItem = ItemBuilder.createItem(itemName, itemDesc, startPrice, startDate, endDate);
                ItemService.getInstance().addItem(newItem);
                Intent i = new Intent(con,SearchActivity.class);
                startActivity(i);
            }
            catch(ItemDateException e)
            {
                view.resetDates();
            }
            catch(ItemPriceException e)
            {
                view.resetPrice();
            }
            catch(ItemBuildException e)
            {
                view.resetDates();
                view.resetPrice();
            }
        }

        @Override
        public void onSearchPress() {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        con = this;

        view = (CreateItemView) View.inflate(this,R.layout.create_item_view,null);
        view.setViewListener(listener);

        setContentView(view);
    }
}
