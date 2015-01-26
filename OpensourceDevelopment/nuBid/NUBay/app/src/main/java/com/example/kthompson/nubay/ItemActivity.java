package com.example.kthompson.nubay;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import Views.ItemBidView;

/**
 * Created by kthompson on 1/26/2015.
 */
public class ItemActivity extends Activity
{
    private ItemBidView view;
    private ItemBidView.ViewListener viewListener = new ItemBidView.ViewListener()
    {
        @Override
        public void onBidPress()
        {
            view.incrementBid();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = (ItemBidView) View.inflate(this, R.layout.item_bid_view, null);
        view.setViewListener(viewListener);

        setContentView(view);
    }

    public void incrementBid(View view)
    {
        if(view instanceof ItemBidView)
        {
            ((Views.ItemBidView)view).incrementBid();
        }
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
