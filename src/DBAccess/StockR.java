package DBAccess;


/**
 * Implements Read access to the stock list
 * The stock list is held in a relational DataBase
 * @author  Michael Alexander Smith
 * @version 2.0
 */

import java.sql.*;
import javax.swing.ImageIcon;
import Catalogue.*;
import Middle.*;

// There can only be 1 resultset opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

// mySQL
//    no spaces after SQL statement ;

/**
  * Implements read only access to the stock database.
  */
 
public class StockR implements StockReader
{
  private Connection theCon    = null;      // Connection to database
  private Statement  theStmt   = null;      // Statement object

  /**
   * Connects to database
   * Uses a factory method to help setup the connection
   */

  public StockR()
         throws StockException
  {
    try
    {
      DBAccess dbDriver = (new DBAccessFactory()).getNewDBAccess();
      dbDriver.loadDriver();
    
      theCon  = DriverManager.getConnection
                  ( dbDriver.urlOfDatabase(), 
                    dbDriver.username(), 
                    dbDriver.password() );

      theStmt = theCon.createStatement();
    }
    catch ( SQLException e )
    {
      throw new StockException( "SQL problem:" + e.getMessage() );
    }
    catch ( Exception e )
    {
        throw new StockException("Can not load JDBC/DBC driver.");
    }
  }


  /**
   * Returns a statement object that is used to process SQL statements
   * @return a statement object
   */
  
  public Statement getStatementObject()
  {
    return theStmt;
  }

  /**
   * Returns a connection object that is used to process
   * requests to the DataBase
   * @return a connection object
   */

  public Connection getConnectionObject()
  {
    return theCon;
  }

  /**
   * Checks if the product exits in the stock list
   * @return true if exists otherwise false
   */

  public synchronized boolean exists( String number )
         throws StockException
  {
    
    try
    {
      ResultSet rs   = getStatementObject().executeQuery(
        "select price from ProductTable " +
        "  where  ProductTable.productNo = '" + number + "'"
      );
      boolean res = rs.next();
      System.out.println( "DB StockR: exists(" + number + ") -> " + 
                          ( res ? "T" : "F" ) );
      return res;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL exists: " + e.getMessage() );
    }
  }

  /**
   * Returns details about the product in the stock list
   * @return StockNumber, Description, Price, Quantity
   */

  public synchronized Product getDetails( String number )
         throws StockException
  {
    try
    {
      Product   dt = new Product( "0", "", 0.00, 0 );
      ResultSet rs = getStatementObject().executeQuery(
        "select description, price, stockLevel " +
        "  from ProductTable, StockTable " +
        "  where  ProductTable.productNo = '" + number + "' " +
        "  and    StockTable.productNo   = '" + number + "'"
      );
      if ( rs.next() )
      {
        dt.setProductNo( number );
        dt.setDescription(rs.getString( "description" ) );
        dt.setPrice( rs.getDouble( "price" ) );
        dt.setQuantity( rs.getInt( "stockLevel" ) );
      }
      System.out.println( "DB StockR: getDetails(" + number + ")" );
      return dt;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL getDetails: " + e.getMessage() );
    }
  }

  public synchronized ImageIcon getImage( String number )
         throws StockException
  {
    String filename = "default.jpg";  
    try
    {
      ResultSet rs   = getStatementObject().executeQuery(
        "select picture from ProductTable " +
        "  where  ProductTable.productNo = '" + number + "'"
      );
      
      boolean res = rs.next();
      if ( res )
        filename = rs.getString( "picture" );
    } catch ( SQLException e )
    {
      throw new StockException( "SQL getImage: " + e.getMessage() );
    }
    
    System.out.println( "DB StockR: getImage -> " + filename );
    ImageIcon ic    = new ImageIcon( filename );
    
    return ic;
  }

}
