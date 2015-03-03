package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import Interfaces.ViewListener;
import Models.Item;

/**
 * Created by kthompson on 1/26/2015.
 */
public class SearchItemView extends LinearLayout
{
    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    Button itemBid;

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

    public void updateInfo(Item i)
    {
        itemImage.setImageResource(i.getImage());
        itemName.setText(i.getName());
        itemPrice.setText("$" + i.getPrice());
    }
}
