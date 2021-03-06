package com.example.kthompson.nubay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import Exceptions.ItemBuildException;
import Exceptions.ItemDateException;
import Exceptions.ItemPriceException;
import Exceptions.ItemServiceException;
import Interfaces.ViewListener;
import Models.Item;
import Service.ClientItemService;
import Views.CreateItemView;

/**
 * Created by kthompson on 2/5/2015.
 */
public class CreateActivity extends Activity
{
    private CreateItemView view;
    private Context con;
    private boolean isEdit;

    private ViewListener listener = new ViewListener()
    {
        @Override
        public void onTransferPress(long itemID)
        {
            Intent i = new Intent(con,SearchActivity.class);
            startActivity(i);
        }

        @Override
        public void onDeletePress(long itemID)
        {

        }

        @Override
        public void onPagePress(long id, String itemName, String itemDesc, String startPrice, String startDate, String endDate, boolean isEdit)
        {
            try
            {
                Item newItem = ClientItemService.getInstance().buildItem(id,itemName,itemDesc,startPrice,startDate,endDate,0);
                if(!isEdit)
                {
                    ClientItemService.getInstance().addItem(newItem);
                }
                else
                {
                    newItem.setId(id);
                    ClientItemService.getInstance().updateItem(newItem);
                }
                Intent i = new Intent(con, ItemActivity.class);
                i.putExtra("itemID",newItem.getId());
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
            catch(ItemServiceException e)
            {
                view.resetPrice();
                view.resetDates();
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

        isEdit = getIntent().getBooleanExtra("isEdit",false);

        con = this;
        view = (CreateItemView) View.inflate(this,R.layout.create_item_view,null);
        view.setViewListener(listener);
        view.determinePageTitle(isEdit);

        setContentView(view);
    }
}
