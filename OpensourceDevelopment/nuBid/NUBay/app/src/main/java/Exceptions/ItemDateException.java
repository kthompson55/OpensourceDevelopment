package Exceptions;

/**
 * Created by kthompson on 2/24/2015.
 */
public class ItemDateException extends ItemBuildException
{
    public ItemDateException()
    {
        super("Dates are wrong");
    }

    public ItemDateException(String message)
    {
        super(message);
    }
}
