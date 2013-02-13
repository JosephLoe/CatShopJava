rem ---------------------------------------------------------------
rem Set to location of your downloaded copy of the junit jar file
rem Download from http://sourceforge.net/projects/junit/files/junit/
rem Download the zip file, extract contents to a suitable location
rem  Then set the Variable JUNIT to be the path to the .jar file
rem  For version 4.8 of JUnit a possability is shown below
rem  The h: drive would be a good choice at the University
rem ---------------------------------------------------------------

set JUNIT=c:\junit\junit-4.8.jar;.

rem ---------------------------------------------------------------

javac -classpath %JUNIT% -source 1.5 -Xlint:unchecked Catalogue\*.java Middle\*.java DBAccess\*.java Processing\*.java Clients\*.java
rmic  -v1.2 Remote.R_StockR
rmic  -v1.2 Remote.R_StockRW
rmic  -v1.2 Remote.R_Order


javac -classpath %JUNIT% -source 1.5 -Xlint:unchecked Other\UT.java
java  -classpath %JUNIT% junit.textui.TestRunner Other.UT

