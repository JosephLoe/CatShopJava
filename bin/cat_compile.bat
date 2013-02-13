
rem javac -source 1.5 -Xlint:unchecked Catalogue\*.java Middle\*.java DBAccess\*.java Processing\*.java Clients\*.java
javac  Catalogue\*.java Middle\*.java DBAccess\*.java Processing\*.java Clients\*.java
rmic  -v1.2 Remote.R_StockR
rmic  -v1.2 Remote.R_StockRW
rmic  -v1.2 Remote.R_Order
