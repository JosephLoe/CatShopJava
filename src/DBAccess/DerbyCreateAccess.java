package DBAccess;

/**
  * Implements management of an Access database.
  * @author  Michael Alexander Smith
  * @version 2.0
  */
 
class DerbyCreateAccess extends DBAccess
{

  private static final String URLdb =
                 "jdbc:derby:catshop.db;create=true";
  private static final String DRIVER =
                 "org.apache.derby.jdbc.EmbeddedDriver";

  public void loadDriver() throws Exception
  {
          Class.forName(DRIVER).newInstance();
  }

  public String urlOfDatabase()
  {
    return URLdb;
  }
}

