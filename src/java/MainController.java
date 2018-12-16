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
import src.java.*;

import javax.swing.*;	//swing

public class MainController{
	public static void main(String[] args) {
				
			FloraController floraController = new FloraController();
			System.out.println("Flora-2 session started");
			// Load the knowledgebase into the Flora session
			floraController.loadModel();
			// Create a frame for the program
			ktpFrame frame = new ktpFrame();
			System.out.println("Made a Frame");
			floraController.closeSession();
		}

}
