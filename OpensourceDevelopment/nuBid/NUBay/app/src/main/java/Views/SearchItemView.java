package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import Exceptions.ItemServiceException;
import Interfaces.ViewListener;
import Models.Item;
import Service.ItemService;

/**
 * Created by kthompson on 1/26/2015.
 */
public class SearchItemView extends LinearLayout
{
    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    Button editItem;
    Button deleteItem;
    ViewListener listener;

    public SearchItemView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        itemImage = (ImageView)findViewById(R.id.searchItemImage);
        itemName = (TextView)findViewById(R.id.searchItemName);
        itemPrice = (TextView)findViewById(R.id.searchItemPrice);
        editItem = (Button)findViewById(R.id.editItemButton);
        deleteItem = (Button)findViewById(R.id.deleteItemButton);

        editItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listener.onTransferPress(ItemService.getInstance().findItemId(itemName.getText().toString()));
            }
        });
       deleteItem.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v)
           {
               listener.onDeletePress(ItemService.getInstance().findItemId(itemName.getText().toString()));
           }
       });
    }

    public void setListener(ViewListener newListener)
    {
        listener = newListener;
    }
}
