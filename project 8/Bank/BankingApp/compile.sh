#!/bin/bash 

#	This is covered under GPL licence version 2 or higher

echo "This is covered under GPL licence version 2 or higher. All right reserved. ODOH KENNETH EMEKA"
DIRECTORY=$(cd `dirname $0` && pwd)
DIRECTORY=$DIRECTORY"/mysql-connector-java-5.0.8-bin.jar:"
#get the path to the jdbc connector
DIRECTORY=$DIRECTORY$CLASSPATH

#set the path to the jdbc connector
export set CLASSPATH=$DIRECTORY

#clear the screen
clear

#remove all existing .class file
rm -rf *.class

#run the java code
javac CustomerLoginview.java
java CustomerLoginview



#remove all remaining .class file
rm -rf *.class

