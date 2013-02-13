DEPRECATED=-Xlint:deprecation 
DEPRECATED=
DERBY_HOME=/apache/derby
DERBY_HOME=c:/apache/derby
FLAGS=
FLAGS= -source 1.7 -Xlint:unchecked
FLAGS= -source 1.7

compile:	
	javac $(DEPRECATED) $(FLAGS)          \
                            Catalogue/*.java  \
                            Middle/*.java     \
                            DBAccess/*.java   \
                            Processing/*.java \
                            Clients/*.java    \
                            Remote/*.java     
	rmic  -v1.2 Remote.R_StockR
	rmic  -v1.2 Remote.R_StockRW
	rmic  -v1.2 Remote.R_Order

clean:
	rm -f -r catshop.db
	rm -f */*.class */*.bak *.bak
	rm -f -r html
	rm -f 00text 00text.rtf 000text 000text.rtf
	rm -f -r doc/*.* doc/*
	rm -f Clients/__SHEL*
	rm -f _list.sh

documentation:
	echo "Making documentation"
	javadoc -sourcepath $(WIN_SDK1)\\src.zip         \
	-group Catalogue Catalogue                       \
	-header "<FONT color="teal">CatShop</FONT>"      \
	-author -windowtitle "CatShop" \
	-use -splitindex -d html \
	-package Clients/*.java Catalogue/*.java Middle/*.java   \
                 DBAccess/*.java Processing/*.java Remote/*.java

run:
	java Clients/Main

distributed:
	java Middle/Server&
	sleep 1
	java Clients/BackDoorClient&
	java Clients/CustomerClient&
	java Clients/CashierClient&
	java Clients/PickClient&
	java Clients/DisplayClient&
	java Clients/CollectionClient&

database:
	java Clients/Setup

test:
	java Other/StandAloneTest
