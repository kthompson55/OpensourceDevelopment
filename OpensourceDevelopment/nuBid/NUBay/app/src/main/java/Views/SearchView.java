package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import java.util.List;

import Models.Item;

/**
 * Created by kthompson on 1/23/2015.
 */
public class SearchView extends LinearLayout
{
    TextView searchBar;
    ListView itemResults;

    private Context thisContext;
    private AttributeSet attrSet;

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
    }

    public void displayItems(Item[] items)
    {
        for(Item i : items)
        {
            SearchItemView itemOption = new SearchItemView(thisContext,attrSet);
            itemOption.setItem(i);
        }
    }

    public void displayItems(List<Item> items)
    {
        for(Item i : items)
        {
            SearchItemView itemOption = new SearchItemView(thisContext,attrSet);
            itemOption.setItem(i);
        }
    }
}
