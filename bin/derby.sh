#
# Base of Java system 
#
export JAVA_HOME=/opt/java/current
export JAVA_HOME=c:/opt/java/current
#
# Base of Derby System
#
export DERBY_HOME=/apache/derby
export DERBY_HOME=c:/apache/derby
#
#
export PATH=$DERBY_HOME/bin:$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$DERBY_HOME/lib/derby.jar:$DERBY_HOME/lib/derbytools.jar:.
echo "DERBY_HOME " $DERBY_HOME
echo "JAVA_HOME  " $JAVA_HOME
echo "CLASSPATH  " $CLASSPATH
