package Clients;

import java.sql.*;
import java.util.ArrayList;
import DBAccess.*;

/**
 * Repopulate the database with test data
 * @author  Michael Alexander Smith
 * @version 3.0 Derby
 */

class Setup 
{
  private static String[] SQL = {

//  " SQL code to set up database tables",

//  "drop table ProductList",
//  "drop table StockList",


  "drop table ProductTable",
  "create table ProductTable ("+
      "productNo      Char(4)," +
      "description    Varchar(40)," +
      "picture        Varchar(80)," +
      "price          Float)",

  "insert into ProductTable values " +
     "('0001', 'LCD 32 inch TV',  'images/Pic0001.jpg', 299.99)",
  "insert into ProductTable values " +
     "('0002', 'DAB Radio',       'images/Pic0002.jpg', 29.99)",
  "insert into ProductTable values " +
     "('0003', 'Toaster',         'images/Pic0003.jpg', 19.99)",
  "insert into ProductTable values " +
     "('0004', 'Watch',           'images/Pic0004.jpg', 29.99)",
  "insert into ProductTable values " +
     "('0005', 'Digital Camera',  'images/Pic0005.jpg', 129.99)",
  "insert into ProductTable values " +
     "('0006', 'MP3 player',      'images/Pic0006.jpg', 12.99)",
  "insert into ProductTable values " +
     "('0007', '16Gb USB2 drive', 'images/Pic0007.jpg', 5.99)",
//  "select * from ProductTable",


  "drop table StockTable",
  "create table StockTable ("+
      "productNo      Char(4)," +
      "stockLevel     Integer)",

  "insert into StockTable values ( '0001',  90 )",
  "insert into StockTable values ( '0002',   4 )",
  "insert into StockTable values ( '0003',   3 )",
  "insert into StockTable values ( '0004',  10 )",
  "insert into StockTable values ( '0005',   7 )",
  "insert into StockTable values ( '0006',   5 )",
  "insert into StockTable values ( '0007',  10 )",

  "select * from StockTable, ProductTable " +
          " where StockTable.productNo = ProductTable.productNo"

};

  public static void main(String[] args)
  {
    Connection theCon    = null;      // Connection to database
    DBAccess   dbDriver  = null;
    DBAccessFactory.setAction("Create");
    System.out.println("Setup CatShop database of stock items");
    try
    {
      dbDriver = (new DBAccessFactory()).getNewDBAccess();
      dbDriver.loadDriver();
      theCon  = DriverManager.getConnection
                  ( dbDriver.urlOfDatabase(), 
                    dbDriver.username(), 
                    dbDriver.password() );
    }
    catch ( SQLException e )
    {
      System.err.println( "Problem with connection to " + 
                           dbDriver.urlOfDatabase() );
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState:     " + e.getSQLState());
      System.out.println("VendorError:  " + e.getErrorCode());
      System.exit( -1 );
    }
    catch ( Exception e )
    {
        System.err.println("Can not load JDBC/DBC driver.");
        System.exit( -1 );
    }

    Statement stmt = null;
    try {
        stmt = theCon.createStatement();
    } catch (Exception e) {
        System.err.println("problems creating statement object" );
    }

    // execute SQL commands to create table, insert data
    for (int i=0; i<SQL.length; i++)
    {
      try
      {
        System.out.println( SQL[i] );
        switch ( SQL[i].charAt(0) )
        {
           case '/' :
             System.out.println("------------------------------");
             break;
           case 's' :
           case 'f' :
             query( stmt, dbDriver.urlOfDatabase(), SQL[i] );
             break;
           case '*' :
             if ( SQL[i].length() >= 2 )
               switch( SQL[i].charAt(1)  )
               {
                 case 'c' :
                   theCon.commit();
                   break;
                 case 'r' :
                   theCon.rollback();
                   break;
                 case '+' :
                   theCon.setAutoCommit( true );
                   break;
                 case '-' :
                   theCon.setAutoCommit( false );
                   break;
                }
              break;
           default :
            stmt.execute(SQL[i]);
        }
        //System.out.println();
      } catch (Exception e)
      {
        System.err.println("problems with SQL sent to " +
                           dbDriver.urlOfDatabase() +
                           "\n" + e.getMessage());
      }
    }

    try {
      theCon.close();
    } catch (Exception e)
    {
      System.err.println("problems with close " +
                         ": "+e.getMessage());
    }

  }


  public static void query( Statement stmt, String url, String stm )
  {
    try
    {
      ResultSet res = stmt.executeQuery( stm );
      
      ArrayList<String> names = new ArrayList<String>(10);

      ResultSetMetaData md = res.getMetaData();
      int cols = md.getColumnCount();

      for ( int j=1; j<=cols; j++ )
      {
        String name = md.getColumnName(j);
        System.out.printf( "%-14.14s ", name );
        names.add( name );
      }
      System.out.println();

      for ( int j=1; j<=cols; j++ )
      {
        System.out.printf( "%-14.14s ",  md.getColumnTypeName(j)  );
      }
      System.out.println();

      while ( res.next() )
      {
        for ( int j=0; j<cols; j++ )
        {
          String name = names.get(j);
          System.out.printf( "%-14.14s ", res.getString( name )  );
        }
        System.out.println();
      }


    } catch (Exception e)
    {
      System.err.println("problems with SQL sent to "+url+
                         "\n" + e.getMessage());
    }
  }
  
  public static String m( int len, String s )
  {
    if ( s.length() >= len )
    {
      return s.substring( 0, len-1 ) + " ";
    }
    else
    {
      StringBuffer res = new StringBuffer( len );
      res.append( s );
      for ( int i = s.length(); i<len; i++ )
        res.append( ' ' );
      return res.toString();
    }
  }


}
