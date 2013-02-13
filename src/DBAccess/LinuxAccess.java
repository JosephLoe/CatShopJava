
package DBAccess;

/**
  * Implements management of an mySQL database on linux.
  * @author  Michael Alexander Smith
  * @version 2.0
  */
 
class LinuxAccess extends DBAccess
{
  public void loadDriver() throws Exception
  {
    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
  }

  public String urlOfDatabase()
  {
    return "jdbc:mysql://localhost/cshop?user=root";
  }
}
