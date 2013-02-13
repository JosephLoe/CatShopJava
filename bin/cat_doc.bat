javadoc -sourcepath c:\jdk1.1\src.jar -group Catalogue Catalogue -header "<FONT color="teal">CatShop</FONT>" -author -windowtitle "CatShop" -use -splitindex -d html -package *.java Catalogue\*.java dbaccess\*.java middle\*.java processing\*.java

: type *.java                     > tmp.1
: type Catalogue\*.java          >> tmp.1
: setdos/c~
: echo s/class/public class/      > tmp.f
: echo s/public public /public / >> tmp.f
: echo /package/d                >> tmp.f
: echo g/import/m0                > tmp.g
: echo w                         >> tmp.g
: echo q                         >> tmp.g
: setdos/c^
: sed -f tmp.f < tmp.1            > tmp.j
: ed tmp.j                        < tmp.g
: rename tmp.j tmp.java
: rm -f -r html
: mkdir html
: javadoc tmp.java -use -splitindex -windowtitle "Catalogue Shop" -d html -public -sourcepath c:\jdk1.1\src.jar
: del tmp.*
