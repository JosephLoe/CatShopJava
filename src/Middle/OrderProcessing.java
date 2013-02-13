package Middle;

import java.util.List;
import java.util.Map;

import Catalogue.*;

/**
  * Defines the interface for accessing the order processing system.
  * @author  Michael Alexander Smith
  * @version 2.0
  */

public interface OrderProcessing
{
 
  public void newOrder( SoldBasket bought ) throws OrderException;

  public int  uniqueNumber() throws OrderException;
   
  public SoldBasket getOrderToPick() throws OrderException;
 
  public void informOrderPicked( int orderNo ) throws OrderException;
         
  public boolean informOrderColected( int orderNo )throws OrderException;
         
  public Map<String, List<Integer> > getOrderState() throws OrderException;
}
