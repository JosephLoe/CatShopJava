package Catalogue;

import java.io.Serializable;

/**
 * Used to hold the following information about
 * a product: Product number, Description, Price and
 * Stock level.
 * @author  Michael Alexander Smith
 * @version 2.0
 */

public class Product implements Serializable
{
  private static final long serialVersionUID = 20092506;
  private String theProductNo;        // Product number
  private String theDescription;      // Description of product
  private double thePrice;            // Price of product
  private int    theQuantity;         // Quantity involved

  /**
   * Construct a product details
   */

  public Product( String aProductNo, String aDescription,
                  double aPrice, int aQuantity )
  {
    theProductNo   = aProductNo;      // Product number
    theDescription = aDescription;    // Description of product
    thePrice       = aPrice;          // Price of product
    theQuantity    = aQuantity;       // Quantity involved
  }
  

  public String getProductNo()   { return theProductNo; }
  public String getDescription() { return theDescription; }
  public double getPrice()       { return thePrice; }
  public int    getQuantity()    { return theQuantity; }
  
  public void setProductNo( String aProductNo )
  { 
    theProductNo = aProductNo;
  }
  
  public void setDescription( String aDescription )
  { 
    theDescription = aDescription;
  }
  
  public void setPrice( double aPrice )
  { 
    thePrice = aPrice;
  }
  
  public void setQuantity( int aQuantity )
  { 
    theQuantity = aQuantity;
  }

}

