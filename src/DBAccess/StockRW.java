package DBAccess;

/**
 * Implements Read /Write access to the stock list
 * The stock list is held in a relational DataBase
 * @author  Michael Alexander Smith
 * @version 2.0
 */

import java.sql.*;
import Catalogue.*;
import Middle.*;


// There can only be 1 resultset opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods
// 

/**
  * Implements read/write access to the stock database.
  */
 
public class StockRW extends StockR implements StockReadWriter 
{

  /*
   * Connects to database
   */

  public StockRW() throws StockException
  {    
    super();        // Connection done in StockR's constructor
  }
  
  /**
   * Customer buys stock
   * @return StockNumber, Description, Price, Quantity
   */

  // Need to Fix
  //  What happens if can not commit data
  //
  public synchronized boolean buyStock( String number, int amount )
         throws StockException
  {
    System.out.println("DB StockRW: buyStock(" + number + 
                       "," + amount + ")" );
    try
    {
    ResultSet rs   = getStatementObject().executeQuery(
      "select stockLevel " +
      "  from StockTable  " +
      "  where   StockTable.productNo   = '" + number + "'"
    );
    if ( rs.next() )
    {
      if ( rs.getInt( "stockLevel" ) >= amount )
      {
    	  getStatementObject().execute(
          "update StockTable set stockLevel = stockLevel-" + amount +
          "       where productNo = '" + number + "' and " +
          "             stockLevel >= " + amount + ""
        );
    	getConnectionObject().commit();
        return true;
      }
    }
    getConnectionObject().rollback();
    return false;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL buyStock: " + e.getMessage() );
    }
  }

  /**
   * Adds stock (Re-stocks) to the store.
   */

  public synchronized void addStock( String number, int amount )
         throws StockException
  {
    try
    {
    getStatementObject().execute(
      "update StockTable set stockLevel = stockLevel + " + amount +
      "       where productNo = '" + number + "'"
    );
    getConnectionObject().commit();
    System.out.println( "DB StockRW: addStock(" + number + 
                        "," + amount + ")" );
    } catch ( SQLException e )
    {
      throw new StockException( "SQL addStock: " + e.getMessage() );
    }
  }


  /**
   * Modifies Stock details for a given product number.
   * Information modified: Description, Price
   */

  public synchronized void modifyStock( Product detail )
              throws StockException
  {
    System.out.println( "DB StockRW: modifyStock(" + 
                        detail.getProductNo() + ")" );
    try
    {
      if ( ! exists( detail.getProductNo() ) )
      {
    	getStatementObject().execute( 
         "insert into ProductTable values ('" +
            detail.getProductNo() + "', " + 
             "'" + detail.getDescription() + "', " + 
             "'images/Pic" + detail.getProductNo() + ".jpg', " + 
             "'" + detail.getPrice() + "' " + ")"
            );
    	getStatementObject().execute( 
           "insert into StockTable values ('" + 
           detail.getProductNo() + "', " + 
           "'" + detail.getQuantity() + "' " + ")"
           ); 
      } else {
    	getStatementObject().execute(
          "update ProductTable " +
          "  set description = '" + detail.getDescription() + "' , " +
          "      price       = " + detail.getPrice() +
          "  where productNo = '" + detail.getProductNo() + "' "
         );
       
    	getStatementObject().execute(
          "update StockTable set stockLevel = " + detail.getQuantity() +
          "  where productNo = '" + detail.getProductNo() + "'"
        );
      }
      getConnectionObject().commit();
      
    } catch ( SQLException e )
    {
      throw new StockException( "SQL modifyStock: " + e.getMessage() );
    }
  }
}
