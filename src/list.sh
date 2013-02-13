#  PageSplit Y/N     Core S/X          FileName  
export PATH=../../../bin:$PATH
export LINE=-n
export FIRSTPAGE="yes"

function list_file
{
  if  test x$1 = xY
  then
    if test $FIRSTPAGE = "yes"
    then
      FIRSTPAGE="no"
    else
      echo ""                                  >> 00text
      echo -n "NEWPAGE "                       >> 00text
    fi
  else
    echo " "                                   >> 00text
    echo " "                                   >> 00text
    echo " "                                   >> 00text
    FIRSTPAGE="no"
  fi
  
  if  test x$2 = xC 
  then
   echo -n "+++ CORE FILE "                    >> 00text
  else
   echo -n "+++ DISTRIBUTED "                  >> 00text
  fi
  
  echo CatShop file     +++ $3 +++             >> 00text
  echo                                         >> 00text
  # cat -n    # with line no's
  cat $LINE   $3                               >> 00text
}

echo -n ""                                     >  00text

list_file Y  C Clients/Main.java
list_file Y  X Clients/Setup.java
list_file Y  C Clients/CustomerGUI.java
list_file Y  C Clients/CashierGUI.java
# list_file Y  X Clients/Test.java
list_file Y  C Clients/Picture.java
list_file Y  X Clients/WebCustomerClient.java
list_file N  X Clients/CustomerClient.java
list_file Y  X Clients/CashierClient.java
list_file N  X WebCustomerClient.htm

list_file Y  C Catalogue/SoldBasket.java
list_file Y  C Catalogue/Basket.java
list_file Y  C Catalogue/Product.java

list_file Y  C DBAccess/DBAccess.java
list_file Y  C DBAccess/DBAccessFactory.java
list_file N  C DBAccess/LinuxAccess.java
list_file Y  C DBAccess/StockR.java
list_file Y  C DBAccess/StockRW.java
list_file Y  C DBAccess/WindowsAccess.java

list_file N  C Middle/MiddleFactory.java
list_file Y  C Middle/LocalMiddleFactory.java
list_file Y  C Middle/OrderException.java
list_file N  C Middle/OrderProcessing.java
list_file N  C Middle/StockException.java
list_file Y  C Middle/StockReadWriter.java
list_file N  C Middle/StockReader.java

list_file Y  C Processing/Order.java

list_file N  X Middle/Names.java
list_file Y  X Middle/F_Order.java
list_file Y  X Middle/F_StockR.java
list_file Y  X Middle/F_StockRW.java
list_file Y  X Middle/Server.java
list_file Y  X Middle/F_Order.java
list_file Y  X Middle/F_StockR.java
list_file Y  X Middle/F_StockRW.java


list_file N  X Remote/RemoteOrder_I.java
list_file Y  X Remote/RemoteStockRW_I.java
list_file N  X Remote/RemoteStockR_I.java
list_file Y  X Remote/R_Order.java
list_file Y  X Remote/R_StockR.java
list_file Y  X Remote/R_StockRW.java

sed -e "/..REMOVE-FROM-HERE../,/..REMOVE-TO-HERE../d" < 00text > 000text
rm 00text

sh to_rtf6.sh 000text

