package exceptions;

/**
 * Created by kthompson on 2/24/2015.
 */
public class ItemPriceException extends ItemBuildException
{
    public ItemPriceException()
    {
        super("Price is incorrect");
    }

    public ItemPriceException(String message)
    {
        super(message);
    }
}
