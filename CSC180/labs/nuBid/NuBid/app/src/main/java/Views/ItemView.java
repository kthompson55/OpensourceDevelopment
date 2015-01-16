package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kthompson.nubid.R;

public class ItemView extends LinearLayout
{
    private int bidCount;
    private Button bidButton;
    private TextView bidLabel;
    private TextView itemLabel;
    private ImageView itemImage;

    public interface ViewListener {
        public void onBidPress();
    }

    private ViewListener listener;
    public void setViewListener(ViewListener viewListener)
    {
        listener = viewListener;
    }

    public ItemView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        bidCount = 0;
    }

    public void incrementBid()
    {
        bidCount++;
        String newLabel = "Bid Count: " + bidCount;
        bidLabel.setText((CharSequence)newLabel);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        bidButton = (Button)findViewById(R.id.bidButton);
        bidLabel = (TextView)findViewById(R.id.bidCountLabel);
        bidButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onBidPress();
            }
        });
        itemLabel = (TextView)findViewById(R.id.bidItemLabel);
        itemImage = (ImageView)findViewById(R.id.bidItemImage);
    }

    public void setItemName(String itemName)
    {
        itemLabel.setText((CharSequence)itemName);
    }

    public void setImage(int imgResource)
    {
        itemImage.setImageResource(imgResource);
    }
}

