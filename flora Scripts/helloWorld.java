/** helloWorld.java, author: Marnix Jansma, 23-11-18 */

// To compile: javac -classpath "$FLORADIR/java/flora2java.jar":"$FLORADIR/java/interprolog.jar" helloWorld.java

/* java 
  -DPROLOGDIR=$PROLOGDIR 
  -DFLORADIR=$FLORADIR 
  -classpath $FLORADIR/java/flora2java.jar:$FLORADIR/java/interprolog.jar./flora\ Scripts/ 
  helloWorld
*/
import java.util.*;
import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;

public class helloWorld{
	
  public static void main(String[] args) {
		
  FloraSession session = new FloraSession();
	System.out.println("Flora-2 session started");
	session.close();
	System.exit(0);
    }
}
