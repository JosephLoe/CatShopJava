package Middle;

/**
  * Exception throw if there is an error in accessing the stock list
  * @author  Michael Alexander Smith
  * @version 2.0
  */
 
public class StockException extends Exception
{
  private static final long serialVersionUID = 1;
  public StockException( String s )
  {
    super(s);
  }
}
