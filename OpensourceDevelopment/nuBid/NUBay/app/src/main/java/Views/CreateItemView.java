package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kthompson.nubay.R;

import java.math.BigDecimal;

import Interfaces.ViewListener;
import Models.Item;
import Service.ClientItemService;

/**
 * Created by kthompson on 2/3/2015.
 */
public class CreateItemView extends LinearLayout
{
    private TextView itemName;
    private TextView itemDesc;
    private TextView startPrice;
    private TextView startDate;
    private TextView endDate;
    private Button returnBtn;
    private boolean edit;
    private ViewListener listener;

    public CreateItemView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }

    public void setViewListener(ViewListener newListener)
    {
        listener = newListener;
    }

    @Override
    public void onFinishInflate()
    {
        super.onFinishInflate();

        itemName = (TextView)findViewById(R.id.createItemName);
        itemDesc = (TextView)findViewById(R.id.createItemDescription);
        startPrice = (TextView)findViewById(R.id.createItemPrice);
        startDate = (TextView)findViewById(R.id.createItemStartDate);
        endDate = (TextView) findViewById(R.id.createItemEndDate);
        returnBtn = (Button) findViewById(R.id.createItemBtn);
        if(edit) {
            returnBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPagePress(
                            ClientItemService.getInstance().findItemId(itemName.getText().toString()),
                            itemName.getText().toString(),
                            itemDesc.getText().toString(),
                            startPrice.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            true);
                }
            });
        }
        else
        {
            returnBtn.setOnClickListener(new OnClickListener()
            {
                @Override
            public void onClick(View v)
                {
                    listener.onPagePress(
                            0,
                            itemName.getText().toString(),
                            itemDesc.getText().toString(),
                            startPrice.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            false
                    );
                }
            });
        }
    }

    public void determinePageTitle(boolean isEdit)
    {
        edit = isEdit;
        if(isEdit)
        {
            TextView temp = (TextView)findViewById(R.id.titleBar);
            temp.setText("Update Item");
        }
    }

    public void resetPrice()
    {
        startPrice.setText("INVALID PRICE");
    }

    public void resetDates()
    {
        startDate.setText("INVALID DATE");
        endDate.setText("INVALID DATE");
    }
}
