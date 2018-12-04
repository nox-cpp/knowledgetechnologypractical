# knowledgetechnologypractical

## ABOUT
This repository contains a group project for a course on Knowledge Technology. Specifically it contains an expert system based on Flora, with a Java interface.

The project focuses on an expert system on Hereditary diseases, mainly breastcancer.
## SETUP
The first step in working on/with this code is to install flora. The version we use here (2.0 Pyrus nivalis) can be found on [this page](https://sourceforge.net/projects/flora/files/FLORA-2/). Instructions for installation can be found [here](http://flora.sourceforge.net/installation.html).

Further information on working with Flora can be found on [this page](http://flora.sourceforge.net/documentation.html)
### COMPILATION
The command to compile the project in a linux terminal is as follows:
```bash
javac -d build/main -classpath "$FLORADIR"/java/flora2java.jar:"$FLORADIR"/java/interprolog.jar src/java/*.java
```
Here it is assumed that the variable $FLORADIR (%FLORADIR% on Windows) is set using either the supplied scripts in the Flora-2 installation directory or manually. I had to set this manually, as the scripts seem to be somewhat specific to a certain machine.

Compiled classes are added to the build folder

NOTE: The quotes ("") are not a luxury here. It is often the case that the path contains spaces, and to account for that we need to include quotes.

#### SETTING THE VARIABLES
One way of setting these variables on Linux would be to add the following lines to the `.bashrc` file, the location of which may vary but for our purposes it can be found in `/home/[USER]` where [USER] is your studentnumber. If the file does not yet exist, create it instead.
```bash
FLORADIR="[PATH-TO-FLORA]/Flora-2/flora2"
PROLOGDIR="[PATH-TO-FLORA]/Flora-2/XSB/config/x86_64-unknown-linux-gnu/bin"
FLORA2JAVA="[PATH-TO-FLORA]/Flora-2/flora2/java/API"
```
[PATH-TO-FLORA] being the path to your Flora installation folder.

On Windows, system variables may be set through the settings GUI. As a general note, on Windows all system variables follow this format `%VAR%`. Whenever a command in this manual shows `$VAR` it should be replaced with `%VAR%` on Windows.
### RUNNING
To run the program (on linux) I use the following line (it should all be one command):
```bash
java -DPROLOGDIR="$PROLOGDIR" -DFLORADIR="$FLORADIR" -classpath "$FLORADIR"/java/flora2java.jar:"$FLORADIR"/java/interprolog.jar:./build/main/ helloWorld
```
NOTE: It is assumed that these commands are run from the top level of the repository.

The first two arguments are used to make clear to java where it can find the Flora and Java executables. This is required for the packages to work. The third argument specifies to java where it can find the Flora package and the flora and interprolog .jar's. This was giving me trouble at first because the paths and references are either incorrect in the flora api source or there was some parameter we were missing to point to them.
I made it work by placing the package in the same directory as the helloWorld java class AND moving the flrException class folder (util) to the same folder as the rest of the flora classes. Otherwise it wouldn't quite work.

So it's a bit of a mess right now, but we're able to compile and run our own flora-java code which is neato.

## ON THE REPO
I decided it would be a good idea to adhere to some structure for the repository, so I did a quick search and decided to shamelessly steal [this](https://github.com/kriasoft/Folder-Structure-Conventions).