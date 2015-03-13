package Interfaces;

import java.math.BigDecimal;

/**
 * Created by kthompson on 1/26/2015.
 */
public interface ViewListener
{
    public void onTransferPress(long itemID);
    public void onDeletePress(long itemID);
    public void onPagePress(long id, String itemName, String itemDesc, String startPrice, String startDate, String endDate, boolean isEdit);
    public void onSearchPress();
}
