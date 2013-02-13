The file DataBase.txt must contain Derby

BlueJ
To use the Derby database, you must add in 
Tools -> Preferences -> Libaries
%DERBY_HOME%\lib\derby.jar
%DERBY_HOME%\lib\derbytools.jar

REPLACE %DERBY_HOME% with the path to the base of the Derby database

Eclipse
To use the Derby database, after importing the jd3.tgz archieve
Move the file DataBase.txt to be in the toplevel directory of your project 
(with src and the JRE System library)
Copy also the directory Images to the same top level directory as DataBase.txt

Then in Properties _> Resource -> Java Build Path -> Libraries
Select Add external JARs
%DERBY_HOME%\lib\derby.jar
%DERBY_HOME%\lib\derbytools.jar

REPLACE %DERBY_HOME% with the path to the base of the Derby database

To create the Derby database, after compiling the system run Setup.java

Exit BlueJ/Eclipse
Now run the application Main.java


