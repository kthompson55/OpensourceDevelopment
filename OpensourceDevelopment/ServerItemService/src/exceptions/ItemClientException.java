package exceptions;

/**
 * Created by kthompson on 3/5/2015.
 */
public class ItemClientException extends Exception
{
    public ItemClientException()
    {
        super("User provided invalid data");
    }

    public ItemClientException(String message)
    {
        super(message);
    }
}
