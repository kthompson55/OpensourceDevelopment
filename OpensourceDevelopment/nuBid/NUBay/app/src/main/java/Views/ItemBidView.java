package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import Interfaces.ViewListener;
import Models.Item;
import Service.ItemService;

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

    public ItemBidView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
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
                listener.onPagePress(bidItem.getId(),bidItem.getName(),bidItem.getDescription(),bidItem.getPrice().toString(),bidItem.getStartDate(),bidItem.getEndDate(),false);
            }
        });
        /*bidItemName.setText(bidItem.getName());
        bidItemDescription.setText(bidItem.getDescription());
        bidItemPriceLabel.setText("Item Price: " + bidItem.getPrice());
        bidItemImage.setImageResource(bidItem.getImage());*/
    }

    public void setItem(long id)
    {
        DecimalFormat fmt = new DecimalFormat("#0.00");
        bidItem = ItemService.getInstance().findItem(id);
        String thing = fmt.format(bidItem.getPrice());
        bidItemName.setText(bidItem.getName());
        bidItemDescription.setText(bidItem.getDescription());
        bidItemPriceLabel.setText("Item Price: $" + thing);
        bidItemImage.setImageResource(bidItem.getImage());
    }

    public void incrementBid(BigDecimal increment)
    {
        bidItem = ItemService.getInstance().bid(bidItem.getId(),increment);
        DecimalFormat fmt = new DecimalFormat("#0.00");
        String thing = fmt.format(bidItem.getPrice());
        bidItemPriceLabel.setText("Item Price: $" + thing);
    }
}
