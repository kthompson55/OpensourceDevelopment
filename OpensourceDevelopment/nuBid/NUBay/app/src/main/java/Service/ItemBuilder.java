package Service;

import com.example.kthompson.nubay.R;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.ItemBuildException;
import Exceptions.ItemDateException;
import Exceptions.ItemPriceException;
import Models.Item;

/**
 * Created by kthompson on 2/13/2015.
 */
public class ItemBuilder
{
    private static final Pattern pricePattern = Pattern.compile("(?:\\$)?((?:[0-9]{1,3})(?:,?[0-9]{3})*)\\.?([0-9]+)?");
    private static final Pattern mdyDate = Pattern.compile("(\\d+)[\\.\\/\\-](\\d+)[\\.\\/\\-](\\d{4})");
    private static final Pattern myDate = Pattern.compile("[\\s](\\d)[\\.\\-\\/](\\d{4})");
    private static final Pattern wordDate = Pattern.compile("([A-z]+)\\s(\\d+)\\,? ?(\\d{4})");

    public static Item createItem(String itemName, String itemDesc, String startPrice, String startDate, String endDate) throws ItemBuildException
    {
        Item i = null;

        BigDecimal price = testPrice(startPrice);
        String sDate = testDate(startDate);
        String eDate = testDate(endDate);

        if(price != null && sDate != null && eDate != null)
        {
            i = new Item(itemName, itemDesc, price, sDate, eDate, R.drawable.smashball);
        }
        else
        {
            throw new ItemBuildException("Field is invalid");
        }
        return i;
    }

    private static BigDecimal testPrice(String price) throws ItemPriceException
    {
        BigDecimal ret = null;
        if(price.contains("-"))
        {
            ret = null;
        }
        else if(price.isEmpty() || price.equals("Start Price"))
        {
            ret = new BigDecimal(0.01);
        }
        else
        {
            String dollars = "";
            String cents = "";

            String temp = price;

            if(price.contains(","))
            {
                temp = price.replace(",","");
            }
            Matcher m = pricePattern.matcher(temp);
            if(m.matches())
            {
                dollars = m.group(1);
                cents = m.group(2);

                StringBuilder sb = new StringBuilder();
                sb.append(dollars).append(".").append(cents);
                try
                {
                    ret = new BigDecimal(Double.parseDouble(sb.toString()));
                }
                catch (NumberFormatException e)
                {
                    ret = null;
                }
            }
        }
        return ret;
    }

    private static String testDate(String date) throws ItemDateException
    {
        String ret = null;

        Matcher mdyDateMatch = mdyDate.matcher(date);
        Matcher myDateMatch = myDate.matcher(date);
        Matcher wordDateMatch = wordDate.matcher(date);

        DateFormat formatter;
        Date d;
        StringBuilder dateBuilder = new StringBuilder();

        if(wordDateMatch.find())
        {
            dateBuilder.append(wordDateMatch.group(1)).append(" ");
            if(wordDateMatch.group(2) != null)
            {
                dateBuilder.append(wordDateMatch.group(2));
            }
            else
            {
                dateBuilder.append("1, ");
            }
            dateBuilder.append(wordDateMatch.group(3));
            try
            {
                formatter = new SimpleDateFormat("MMM d, yyyy");
                d = formatter.parse(dateBuilder.toString());
                ret = d.toString();
            }
            catch(ParseException e)
            {
                throw new ItemDateException();
            }

        }
        else if(mdyDateMatch.find())
        {
            int month = Integer.parseInt(mdyDateMatch.group(1));
            int day = Integer.parseInt(mdyDateMatch.group(2));
            int year = Integer.parseInt(mdyDateMatch.group(3));
            if(month <= 12 && month > 0)
            {
                if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                {
                    if(day > 31 || day < 0)
                    {
                        throw new ItemDateException();
                    }
                }
                else if(month == 4 || month == 6 || month == 9 || month == 11)
                {
                    if(day > 30 || day < 0)
                    {
                        throw new ItemDateException();
                    }
                }
                else if(month == 2)
                {
                    if(day > 29 || day < 0)
                    {
                        throw new ItemDateException();
                    }
                }
            }
            else throw new ItemDateException();
            dateBuilder.append(mdyDateMatch.group(1)).append("/").append(mdyDateMatch.group(2)).append("/").append(mdyDateMatch.group(3));
            formatter = new SimpleDateFormat("MM/dd/yyyy");
            try
            {
                d = formatter.parse(dateBuilder.toString());
                ret = d.toString();
            }
            catch(ParseException e)
            {
                throw new ItemDateException();
            }
        }
        else if(myDateMatch.find())
        {
            dateBuilder.append(myDateMatch.group(1)).append("/1").append("/").append(myDateMatch.group(2));
            formatter = new SimpleDateFormat("MM/d/yyyy");
            try
            {
                d = formatter.parse(dateBuilder.toString());
                ret = d.toString();
            }
            catch(ParseException e)
            {
                throw new ItemDateException();
            }
        }

        return ret;
    }
}
