# ABOUT
The helloWorld java class opens a session of the Flora Engine, says something, closes the session and then exits.
# SETUP
Compiling and running the examples is not very straightforward, and there are quite some prerequisites but it's at the point now where I can reliably compile and run classes using the following steps.
## COMPILATION
The command to compile the file is as follows:

  javac -classpath "$FLORADIR/java/flora2java.jar":"$FLORADIR/java/interprolog.jar" helloWorld.java
  
Here it is assumed that the variable $FLORADIR (%FLORADIR% on Windows) is set using either the supplied scripts in the Flora-2 installation directory or manually. I had to set this manually, as the scripts seem to be somewhat specific to a certain machine.
## RUNNING
To run the program I use the following line (it should all be one command):

  java -DPROLOGDIR=$PROLOGDIR -DFLORADIR=$FLORADIR -classpath $FLORADIR/java/flora2java.jar:$FLORADIR/java/interprolog.jar:./ helloWorld

The first two arguments are used to make clear to java where it can find the Flora and Java executables. This is required for the packages to work. The third argument specifies to java where it can find the Flora package and the flora and interprolog .jar's. This was giving me trouble at first because the paths and references are either incorrect in the flora api source or there was some parameter we were missing to point to them.
I made it work by placing the package in the same directory as the helloWorld java class AND moving the flrException class folder (util) to the same folder as the rest of the flora classes. Otherwise it wouldn't quite work.

So it's a bit of a mess right now, but we're able to compile and run our own flora-java code which is neato.

P.S.: In order to do all this on Windows, the commands need to be adapted to use the proper directory separators and variable names (%VAR% i.o. $VAR)