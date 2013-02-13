/**
 * @author  Michael Alexander Smith
 * Interface Middle factory
 * @version 2.0
 */

package Middle;

/**
  * Provide access to middle tier components.
  */

// Pattern: Abstract Factory

public interface MiddleFactory
{
 
  /**
   * Return an object to access the database for read only access
   */
  
  public StockReader makeStockReader() throws StockException;

  /**
   * Return an object to access the database for read/write access
   */
  
  public StockReadWriter makeStockReadWriter() throws StockException;

  
  /**
   * Return an object to access the order processing system
   */
   
  public OrderProcessing makeOrderProcessing() throws OrderException;

}

