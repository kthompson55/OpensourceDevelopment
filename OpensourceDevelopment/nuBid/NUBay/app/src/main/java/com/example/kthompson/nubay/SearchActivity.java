package com.example.kthompson.nubay;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.logging.Handler;

import Interfaces.ViewListener;
import Models.Item;
import Service.ItemService;
import Views.SearchView;


public class SearchActivity extends Activity
{
    private SearchView view;

    private ViewListener listener = new ViewListener()
    {
        @Override
        public void onPress()
        {
            if(!view.getQuery().equals(""))
            {
                view.displayItems(ItemService.getInstance().search(view.getQuery()));
            }
            else
            {
                view.displayItems(ItemService.getInstance().getItems());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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
}
