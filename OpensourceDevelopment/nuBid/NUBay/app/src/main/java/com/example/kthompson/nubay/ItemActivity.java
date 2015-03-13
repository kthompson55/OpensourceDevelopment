package com.example.kthompson.nubay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.math.BigDecimal;

import Interfaces.ViewListener;
import Service.ItemService;
import Views.CreateItemView;
import Views.ItemBidView;

/**
 * Created by kthompson on 1/26/2015.
 */
public class ItemActivity extends Activity
{
    private Context con;
    private long itemID;
    private ItemBidView view;
    private ViewListener viewListener = new ViewListener()
    {
        @Override
        public void onTransferPress(long itemID)
        {
            // INTENT HERE TO RETURN TO SEARCH
            Intent i = new Intent(con, SearchActivity.class);
            startActivity(i);
        }

        @Override
        public void onDeletePress(long itemID)
        {

        }

        @Override
        public void onPagePress(long id, String itemName, String itemDesc, String startPrice, String startDate, String endDate, boolean isEdit)
        {
            view.incrementBid(new BigDecimal(.5));
        }

        @Override
        public void onSearchPress()
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemID = this.getIntent().getLongExtra("itemID",1);

        con = this;

        view = (ItemBidView) View.inflate(this, R.layout.item_bid_view, null);
        view.setViewListener(viewListener);
        view.setItem(itemID);

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
}
