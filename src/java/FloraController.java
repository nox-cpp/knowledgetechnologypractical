package src.java;
import java.util.*;
import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;

public class FloraController {
  private FloraSession session;

  public FloraController() {
    this.session = new FloraSession();
  }

  public void loadModel() {
    /** The path here refers to the file location in the source files
     * Ideally, when we want to demo the program it is included with the rest of the files
     *  */
    if(this.session.loadFile("src/flora/knowledgebase.flr", "knowledgebase"))
      System.out.println("Knowledgebase loaded successfully.");
    else
      System.out.println("Knowledgebase loading failed.");
    return;
  }

  public void closeSession() {
    this.session.close();
  }

  // Sends a given query to the model and returns it as a Response type
  public Response queryModel(String query) {
    // We add the module name here, to avoid redundancy
    return new Response(this.session.executeQuery(query + "@knowledgebase."));
  }



  // Adds a frame (entity) to the knowledgebase with specified id and values for the fields
  // If the values list is too short, the missing values will default to void
  public Boolean addFact(String name, ArrayList<String> methods, ArrayList<String> values) {
    Response rMethods = new Response(methods);
    Response rValues = new Response(values);
    FloraEntity fe = new FloraEntity(this, name, rMethods, rValues);
    return addFact(fe);
  }

  public Boolean addFact(FloraEntity fe) {
    // If the given name is invalid or it already exists in the knowledgebase this will fail
    if(fe.getName() == "" | !this.isEntity(fe.getName())) {
      return false;
    }
    String addition = fe.getName() + "[";
    for(String method : fe.getMethods()) {
      addition += method + "->";
      if((fe.getMethods().indexOf(method) + 1) <= fe.getValues().size()) {
        addition += fe.getValues().get(fe.getMethods().indexOf(method));
      } else {
        addition += "false";
      }
      addition += ",";
    }
    // This removes the trailing comma and finishes the command
    if (addition.endsWith(","))
      addition = addition.substring(0, addition.length() - 1);
    addition +=  "]";
    return this.insertKnowledge(addition);
  }

  // Creates an empty frame (entity without methods/values)
  public Boolean addFact(String name) {
    if (name == "" | this.isEntity(name))
      return false;
    return this.insertKnowledge(name + "[]");
  }

  // NOTE: This does not delete the entity from the knowledgebase, only the facts attached to it.
  // Deleting names is currently impossible
  public Boolean deleteFact(String name) {
    if (name == "" | !this.isEntity(name))
      return false;
    return this.commandModel("deleteall{" + name + "[?X->?Y], " + name + "[?X->?Y]" + "}@knowledgebase.");
  }

  //
  public Boolean updateFact(FloraEntity fe) {
    if (this.isEntity(fe.getName())) {
      this.deleteFact(fe.getName());
      this.addFact(fe);
      return true;
    }
    return false;
  }

  private Boolean insertKnowledge(String knowledge) {
    return this.session.executeCommand("insert{" + knowledge + "}@knowledgebase.");
  }

  // Returns all Instances of a class in a Response type
  // Should return an empty list if the class does not exist
  public Response getClassInstances(String className) {
    return queryModel("?X:" + className);
  }

  // Returns all methods attached to an entity in response type.
  public Response getMethods(String name) {
    return queryModel(name + "[?X -> ?]");
  }

  // Returns true if an entity exists in the knowledgebase
  // This works for reasons
  public Boolean isEntity(String name) {
    return queryModel(name + "[]").toString().contains("Var");
  }

  // Returns a new FloraEntity by the given name
  public FloraEntity getEntity(String name) {
    return new FloraEntity(this, name);
  }

  // Returns the values for each method of an entity
  public Response getValues(String name) {
    return queryModel(name + "[? -> ?X]");
  }

  // Returns a String that lists the names of ALL entities in the knowledgebase
  public String listEntities() {
    return queryModel("?X[]").toString();
  }

  // Returns a String that lists the names of all entities that match the given type
  public String listEntities(String type) {
    return getClassInstances(type).toString();
  }

  // Lists all methods attached to a entity that goes by that name
  public String listMethods(String name) {
    return getMethods(name).toString();
  }

  // Sends a command to the inference engine. Does not return any value.
  public Boolean commandModel(String command) {
    return this.session.executeCommand(command);
  }
}