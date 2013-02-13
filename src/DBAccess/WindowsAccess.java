
package DBAccess;

/**
  * Implements management of an Access database.
  * @author  Michael Alexander Smith
  * @version 2.0
  */
 
class WindowsAccess extends DBAccess
{
  public void loadDriver() throws Exception
  {
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
  }

  public String urlOfDatabase()
  {
    return "jdbc:odbc:cshop";
  }
}