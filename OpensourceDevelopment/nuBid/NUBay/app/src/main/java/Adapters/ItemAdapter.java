package Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import java.text.DecimalFormat;
import java.util.List;

import Interfaces.ViewListener;
import Models.Item;
import Views.SearchItemView;

/**
 * Created by kthompson on 1/27/2015.
 */
public class ItemAdapter extends BaseAdapter
{
    List<Item> itemList;
    Context context;
    ViewListener listener;

    public ItemAdapter(Context cont,List<Item> items,ViewListener list)
    {
        context = cont;
        itemList = items;
        listener = list;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = View.inflate(context,R.layout.item_search_view,null);
        ((SearchItemView)v).setListener(listener);

        if(v != null)
        {
            DecimalFormat fmt = new DecimalFormat("#0.00");
            Item p = (Item) getItem(position);

            if (p != null) {
                ImageView img = (ImageView) v.findViewById(R.id.searchItemImage); // Not the right view at the time of this being called [SearchView doesn't contain searchItemImage
                TextView name = (TextView) v.findViewById(R.id.searchItemName);
                TextView price = (TextView) v.findViewById(R.id.searchItemPrice);

                img.setImageResource(p.getImage());
                name.setText(p.getName());
                price.setText("$" + fmt.format(p.getPrice()));
            }
        }
        return v;
    }
}
