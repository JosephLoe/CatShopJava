;
; Base of Java system
;
set JAVA_HOME=c:\opt-portable\java

;
; Base of Derby System
;
set DERBY_HOME=c:\apache\derby

;
;
set PATH=%DERBY_HOME%\bin;%PATH%

set CLASSPATH=.;%DERBY_HOME%\lib\derby.jar;%DERBY_HOME%\lib\derbytools.jar;.

echo %CLASSPATH%

echo %PATH%
