package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import Models.Item;

/**
 * Created by kthompson on 1/26/2015.
 */
public class SearchItemView extends LinearLayout
{
    Item displayItem;
    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;

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
    }

    public void setItem(Item item)
    {
        displayItem = item;
        updateItem();
    }

    public void updateItem()
    {
        if(displayItem != null)
        {
            itemImage.setImageResource(displayItem.getImage());
            itemName.setText(displayItem.getName());
            itemPrice.setText("$" + displayItem.getPrice());
        }
    }
}
