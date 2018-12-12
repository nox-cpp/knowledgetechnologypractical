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
			// Create a frame for the prgram
			ktpFrame frame = new ktpFrame();
			System.out.println("Made a Frame");
			// Ask the model to give info on all its persons
			System.out.println(floraController.queryModel("?X:person@knowledgebase."));
			// Query the model for persons that fit a specific profile
			System.out.println(floraController.queryModel("?X:person[age -> 21]@knowledgebase."));
			// Trying a complex query
			System.out.println("A complex response\n" + floraController.queryModel("?X[relatives->?Y]@knowledgebase.", true));
			// Now we modify one of the instances using a command and afterwards we check the values again
			String command = "delete{Marietje[relatives->{Tim}]}.";
			floraController.commandModel(command);
			System.out.println("A complex response\n" + floraController.queryModel("?X[relatives->?Y]@knowledgebase.", true));
			// Listing all entities in the kb
			System.out.println(floraController.listEntities());
			// Listing all methods to an entity (NOTE: it must be one that exists, for now)
			System.out.println(floraController.listMethods("Tim"));
			// Close the FloraSession prior to exiting the program
			floraController.closeSession();
		}

}
