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

  // Sends a given query to the model and returns the results as a string
  public String queryModel(String query) {
    Response response = new Response(this.session.executeQuery(query));
    String answervals = "";
    return response.toString();
  }

  // Returns a List of Strings with answers to the query
  // If the query is not `complex' this calls the base function
  public List<String> queryModel(String query, boolean complex) {
    ArrayList<String> response = new ArrayList<>();
    if (complex == false) {
      response.add(queryModel(query));
      return response;
    }
    return response;
  }

  public ArrayList<FloraObject> queryModelToList(String query) {
    return iteratorToList(this.session.executeQuery(query));
  }

  // Converts an iterator to a list.
  private ArrayList<FloraObject> iteratorToList(Iterator<FloraObject> iter) {
    ArrayList<FloraObject> list = new ArrayList<FloraObject>();
    while(iter.hasNext())
      list.add(iter.next());
    return list;
  }

  private ArrayList<FloraObject> getClassInstances(String className) {
    return queryModelToList("?X:" + className + "@knowledgebase.");
  }

  public String listEntities() {
    ArrayList<FloraObject> response = getClassInstances("person");
    String entities = "";
    for (FloraObject obj : response) {
      entities += obj.toString() + "\n";
    }
    return entities;
  }

  public String listMethods(String name) {
    return queryModel(name + "[?X -> ?]@knowledgebase.");
  }

  public void commandModel(String command) {
    this.session.executeCommand(command);
  }
}