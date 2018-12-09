/**
 * 	helloWorld.java
 * 	authors:
 *  Marnix Jansma, 23-11-18
 *  Gijs Hendriks (s2410540)
 *  Hidde
 *  
 *  To compile:
 *  javac -d build/main -classpath "$FLORADIR"/java/flora2java.jar:"$FLORADIR"/java/interprolog.jar: src/java/*.java
 *  javac -classpath "$FLORADIR/java/flora2java.jar":"$FLORADIR/java/interprolog.jar": src/java/helloWorld.java
 *  
 *  To run:
 *  java -DPROLOGDIR=$PROLOGDIR -DFLORADIR=$FLORADIR -classpath $FLORADIR/java/flora2java.jar:$FLORADIR/java/interprolog.jar:./ src/java/helloWorld
 */


package src.java;
import java.util.*;
import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;
import javax.swing.*;	//swing

public class helloWorld{
	
	public static void main(String[] args) {
				
			FloraSession session = new FloraSession();
			System.out.println("Flora-2 session started");
			
			
			
			ktpFrame frame = new ktpFrame();
			System.out.println("Made a Frame");
		}

}
