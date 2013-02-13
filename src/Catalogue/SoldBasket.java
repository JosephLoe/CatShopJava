package Catalogue;

import java.io.Serializable;

/**
 * Holds a list of products that have been sold to a customer.
 * An order number is also held to uniquely
 * identify the customer's order.
 * @author  Michael Alexander Smith
 * @version 2.2
 *
 */

public class SoldBasket extends Basket implements Serializable
{
  private static final long serialVersionUID = 1;
  private int theOrderNo = 0;                    // Order number

  /**
   * A unique order number is supplied as a parameter to the
   * constructor.
   * See OrderManip.uniqueNumber()
   */

  public SoldBasket( int aOrderNo )
  {
     theOrderNo  = aOrderNo;
  }
   
  /**
   * Returns a string containing a description of all the products
   * ordered. Included is an order number.
   * @return string description of products
   */

  public String getDetails()
  {
    return "Order number: " + theOrderNo + "\n" + "\n" +
           super.getDetails();
  }
  /**
   * Returns the order number
   * @return order number
   */

  public int getOrderNo()
  {
    return theOrderNo;
  }
}

