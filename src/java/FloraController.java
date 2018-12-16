package src.java;
import java.util.*;
import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;

public class FloraController {
  private FloraSession session;

  public FloraController() {
    session = new FloraSession();
  }

  public void loadModel() {
    /** The path here refers to the file location in the source files
     * Ideally, when we want to demo the program it is included with the rest of the files
     *  */
    if(session.loadFile("src/flora/knowledgebase.flr", "knowledgebase"))
      System.out.println("Knowledgebase loaded successfully.");
    else
      System.out.println("Knowledgebase loading failed.");
    return;
  }

  public void closeSession() {
    session.close();
  }

  // Sends a given query to the model and returns it as a Response type
  public Response queryModel(String query) {
    return new Response(this.session.executeQuery(query));
  }

  // Returns all Instances of a class in a Response type
  // Should return an empty list if the class does not exist
  private Response getClassInstances(String className) {
    return queryModel("?X:" + className + "@knowledgebase.");
  }

  // Returns a String that lists the names of ALL entities in the knowledgebase
  public String listEntities() {
    Response response = new Response(this.session.executeQuery("?X[]."));
    return response.toString();
  }

  // Returns a String that lists the names of all entities that match the given type
  public String listEntities(String type) {
    return getClassInstances(type).toString();
  }

  // Lists all methods attached to a entity that goes by that name
  public String listMethods(String name) {
    return queryModel(name + "[?X -> ?]@knowledgebase.").toString();
  }

  // Sends a command to the inference engine. Does not return any value.
  public void commandModel(String command) {
    this.session.executeCommand(command);
  }
}