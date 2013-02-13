package Catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Formatter;
import java.util.Currency;

/**
 * Holds a collection of products from the CatShop.
 * @author  Michael Alexander Smith
 * @version 2.2
 *
 */
public class Basket implements Serializable
{
  private static final long serialVersionUID = 1;
  private ArrayList<Product> theContents = new ArrayList<Product>(10);
  
  /**
   * Adds a product to the Basket.
   */

  public void add( Product pr )    // Add a product to the
  {                                //  product list
    theContents.add( pr );         //
  }

  /**
   * Returns the number of products held in the basket.
   * @return number of products
   */

  public int number()              // Return the number of
  {                                //  entries in the
    return theContents.size();     //  product list
  }

  /**
   * The last product added (to the basket) is removed and returned.
   * It is an error to attempt to remove a non existent product.
   * @return product removed
   */

  public Product remove()          // Remove and return last item
  {
    int items = theContents.size();
    if ( items < 1 )
      throw new Error("EMPTY: Basket.remove()");
    return theContents.remove( items-1 );
  }

  /**
   * All items stored are removed.
   */

  public void clear()              // Clear the
  {                                // the product list
    theContents.clear();
  }


  /**
   * Returns a reference to a copy of the ArrayList that holds 
   * the products contained in the Basket.
   * @return ArrayList of held products
   */
  
  @SuppressWarnings("unchecked")
  public ArrayList<Product> getProducts()
  {
    return (ArrayList<Product>)theContents.clone();      // return
  }

  /**
   * Changes the contents of the Basket to that contained
   * in the passed ArrayList.
   * @param items - List of products
   */
  @SuppressWarnings("unchecked")
  public  void setProducts( ArrayList<Product> items )
  {
    theContents = (ArrayList<Product>)items.clone();     // copy
  }
  
  /**
   * Returns a string containing a description of all the products held.
   * @return string description of products
   */

  public String getDetails()        // Return list of products as string
  {
    Locale uk = Locale.UK;
    StringBuilder sb = new StringBuilder(256);
    Formatter     fr = new Formatter(sb, uk);
    String csign = (Currency.getInstance( uk )).getSymbol();
    double total = 0.00;
    for ( Product pr: theContents )
    {
      int     no = pr.getQuantity();
      fr.format("%-7s",       pr.getProductNo() );
      fr.format("%-14.14s ",  pr.getDescription() );
      fr.format("(%3d) ",     no );
      fr.format("%s%7.2f",    csign, pr.getPrice()*no );
      fr.format("\n");
      total += pr.getPrice() * no;
    }
    fr.format("----------------------------\n");
    fr.format("Total                       ");
    fr.format("%s%7.2f\n",    csign, total );
    return sb.toString();
  }
}
