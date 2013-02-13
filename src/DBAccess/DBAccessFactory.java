/**
 * @author  Michael Alexander Smith
 * @version 3.0
 */

package DBAccess;
import java.io.*;
import java.util.*;

/**
  * Manages the starting up of the database.
  * The database may be Access, mySQL etc.
  */

// Pattern: Abstract Factory
//          Fix to be 

public class DBAccessFactory
{
  static String theAction   = "";
  static String theDataBase = "";
  static String theOS       = "";

  public static void setAction( String name )
  {
    theAction = name;
  }

  private static String setEnvironment()
  {
    theDataBase  = fileToString( "DataBase.txt" ) + theAction;
    String os    = System.getProperties().getProperty( "os.name" );
    String arch  = System.getProperties().getProperty( "os.arch" );
    String osVer = System.getProperties().getProperty( "os.version" );
    theOS = String.format("%s %s %s",  os, osVer, arch );
    System.out.println( theOS );
    return theOS;
  }
  
  /**
   * Return an object to setup access to the database.
   */
  
  public DBAccess getNewDBAccess()
  {
    setEnvironment();
    System.out.printf("Using [%s] as database type\n", theDataBase );
    switch ( theDataBase )
    {
       case "Derby" :
         return new DerbyAccess();       // Derby
    
       case "DerbyCreate" :
         return new DerbyCreateAccess(); // Derby & create database
    
       case "Access" :
       case "AccessCreate" :
         return new WindowsAccess();     // Access Windows
    
       case "mySQL" :
       case "mySQLCreate" :
         return new LinuxAccess();       // MySQL linux
         
       default:
         System.out.printf("DataBase [%s] not known\n", theDataBase );
         System.exit(0);
    }
    return new DBAccess();               // Unknown
  }
  
  public static String fileToString( String file )
  {
    byte[] vec = fileToBytes( file );
    return new String( vec ).replaceAll("\n","");
  }

  /**
   * Write String to newley created file
   */

  public static byte[] fileToBytes( String file )
  {
    byte[] vec = new byte[0];
    try
    {
      final int len = (int) length( file );
      if ( len < 1000 )
      {
        vec = new byte[ len ];
        FileInputStream istream = new FileInputStream( file );
        istream.read( vec, 0, len );
        return vec;
      }
    }
    catch ( FileNotFoundException  err )
    {
      System.out.printf("File does not exist: fileToBytes [%s]\n", file );
      System.exit(0);
    }
    catch ( IOException err )
    {
      System.out.printf("IO error: fileToBytes [%s]\n", file );
      System.exit(0);
    }
    return vec;
  }
  
  public static long length( String path )
  {
    try
    {
      File in = new File( path );
      return in.length();
    }
    catch (SecurityException err )
    {
      System.out.printf("Security error: length of file [%s]\n", path);
      System.exit(0);
    }
    return -1;
  }

}

