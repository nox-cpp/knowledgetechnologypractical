package src.java;
import java.util.*;

import javax.xml.ws.RespectBinding;

import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;
import src.java.FloraController;

// This class can hold all information available for an object in the knowledgebase
public class FloraEntity {
  private String name;
  private Response methods;
  private Response values;

  private FloraController fc;

  // Creates a FloraEntity by looking for an entity by a specified name in the module
  public FloraEntity(FloraController fc, String name) {
    if (fc.isEntity(name)) {
      this.name = name;
      this.methods = fc.getMethods(name);
      this.values = fc.getValues(name);
    }
  }

  // Creates a FloraEntity from two Response objects
  public FloraEntity(FloraController fc, String Name, Response methods, Response values) {
    this.name = name;
    this.methods = methods;
    this.values = values;
  }

  @Override
  public String toString() {
    if (this.methods.size() == 0 | this.values.size() == 0) {
      return this.name;
    }
    return this.name + "\n[" + this.methods.toString() + "\n]\n[" + this.values.toString() + "\n]\n";
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public Response getMethods() {
    return this.methods;
  }

  public Response getValues() {
    return this.values;
  }
}