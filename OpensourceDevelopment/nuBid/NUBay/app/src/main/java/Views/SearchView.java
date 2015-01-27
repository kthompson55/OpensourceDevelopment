package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import java.util.ArrayList;
import java.util.List;

import Interfaces.ViewListener;
import Models.Item;
import Service.ItemService;

/**
 * Created by kthompson on 1/23/2015.
 */
public class SearchView extends LinearLayout
{
    TextView searchBar;
    ListView itemResults;
    Button searchButton;

    private Context thisContext;
    private AttributeSet attrSet;

    private ViewListener listener;

    public SearchView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        thisContext = context;
        attrSet = attrs;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        searchBar = (TextView)findViewById(R.id.searchBar);
        itemResults = (ListView)findViewById(R.id.itemList);
        searchButton = (Button)findViewById(R.id.queryButton);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onPress();
            }
        });
        displayItems(ItemService.getInstance().getItems());
    }

    public void setViewListener(ViewListener newListener)
    {
        listener = newListener;
    }

    public String getQuery()
    {
        return searchBar.getText().toString();
    }

    public void displayItems(List<Item> items)
    {
        for(Item i : items)
        {
            SearchItemView itemOption = (SearchItemView)View.inflate(getContext(),R.layout.item_search_view,null);
            itemOption.setItem(i);
            addView(itemOption);
        }
    }
}
