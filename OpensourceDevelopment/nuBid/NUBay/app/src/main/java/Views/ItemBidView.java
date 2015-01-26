package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import Models.Item;

/**
 * Created by kthompson on 1/23/2015.
 */
public class ItemBidView extends LinearLayout
{
    private Item bidItem;
    private Button bidButton;
    private TextView bidItemName;
    private TextView bidItemDescription;
    private TextView bidItemPriceLabel;
    private ImageView bidItemImage;

    public ItemBidView(Context context, AttributeSet attrs, Item item)
    {
        super(context,attrs);
        bidItem = item;
    }

    public interface ViewListener
    {
        public void onBidPress();
    }

    private ViewListener listener;
    public void setViewListener(ViewListener newListener)
    {
        listener = newListener;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        bidButton = (Button)findViewById(R.id.bidButton);
        bidItemName = (TextView)findViewById(R.id.bidItemName);
        bidItemDescription = (TextView)findViewById(R.id.bidItemDescription);
        bidItemPriceLabel = (TextView)findViewById(R.id.bidPriceLabel);
        bidItemImage = (ImageView)findViewById(R.id.bidItemImage);
        bidButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onBidPress();
            }
        });
        bidItemName.setText(bidItem.getName());
        bidItemDescription.setText(bidItem.getDescription());
        bidItemImage.setImageResource(bidItem.getImage());
    }

    public void incrementBid()
    {
        bidItem.increaseBid();
        String newLabel = "Current Bid: " + bidItem.getPrice();
        bidItemPriceLabel.setText((CharSequence)newLabel);
    }
}
