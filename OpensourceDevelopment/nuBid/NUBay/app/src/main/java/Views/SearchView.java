package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import java.math.BigDecimal;
import java.util.List;

import Adapters.ItemAdapter;
import Interfaces.ViewListener;
import Models.Item;
import Service.ClientItemService;

/**
 * Created by kthompson on 1/23/2015.
 */
public class SearchView extends LinearLayout
{
    TextView searchBar;
    ListView itemResults;
    Button searchButton;
    Button createButton;
    private ViewListener listener;

    public SearchView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        searchBar = (TextView)findViewById(R.id.searchBar);
        itemResults = (ListView)findViewById(R.id.itemList);
        searchButton = (Button)findViewById(R.id.queryButton);
        createButton = (Button)findViewById(R.id.createItemButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSearchPress();
            }
        });
        createButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onTransferPress(-1);
            }
        });
        itemResults.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listener.onTransferPress(id);
            }
        });
    }

    public void setViewListener(ViewListener newListener)
    {
        listener = newListener;
    }

    public String getQuery()
    {
        return searchBar.getText().toString();
    }

    public void displayItems(ItemAdapter adapter)
    {
        itemResults.setAdapter(adapter);
    }
}
