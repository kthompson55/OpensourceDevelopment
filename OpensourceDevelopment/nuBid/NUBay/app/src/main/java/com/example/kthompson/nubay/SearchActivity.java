package com.example.kthompson.nubay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.math.BigDecimal;

import Adapters.ItemAdapter;
import Exceptions.ItemServiceException;
import Interfaces.ViewListener;
import Service.ClientItemService;
import Views.ItemBidView;
import Views.SearchView;


public class SearchActivity extends Activity
{
    private SearchView view;
    private Context con;

    private ViewListener listener = new ViewListener()
    {
        @Override
        public void onSearchPress()
        {
            String query = view.getQuery();
            if(!query.equals(""))
            {
                ItemAdapter adapter = createAdapter(query);
                view.displayItems(adapter);
            }
        }

        @Override
        public void onPagePress(long id, String itemName, String itemDesc, String startPrice, String startDate, String endDate, boolean isEdit)
        {

        }

        @Override
        public void onTransferPress(long itemID)
        {
            if(itemID > 0) {
                Intent i = new Intent(con, ItemActivity.class);
                i.putExtra("itemID", itemID);
                startActivity(i);
            }
            else
            {
                Intent i = new Intent(con, CreateActivity.class);
                i.putExtra("isEdit",false);
                startActivity(i);
            }
        }

        @Override
        public void onDeletePress(long itemID) {

        }
    };

    private ViewListener searchItemListener = new ViewListener()
    {
        @Override
        public void onSearchPress()
        {

        }

        @Override
        public void onPagePress(long id, String itemName, String itemDesc, String startPrice, String startDate, String endDate, boolean isEdit)
        {

        }

        @Override
        public void onTransferPress(long itemID)
        {
            Intent i = new Intent(con, CreateActivity.class);
            i.putExtra("isEdit", true);
            i.putExtra("itemId",itemID);
            startActivity(i);
        }

        @Override
        public void onDeletePress(long itemID)
        {
            try
            {
                ClientItemService.getInstance().deleteItem(itemID);
                ItemAdapter adapter = createAdapter("");
                view.displayItems(adapter);
            }
            catch (ItemServiceException e)
            {
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        con = this;

        view = (SearchView) View.inflate(this, R.layout.search_view, null);
        view.setViewListener(listener);

        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ItemAdapter createAdapter(String query)
    {
        return new ItemAdapter(this,ClientItemService.getInstance().search(query),searchItemListener);
    }
}
