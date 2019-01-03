package src.java;
import java.util.*;

// import javax.xml.ws.RespectBinding;

import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;
import src.java.FloraController;

/**
 * This class can hold all information available for an object in the knowledgebase
 * 
 *  */
public class FloraEntity {
  private String name;
  private Response methods;
  private Response values;

  // Creates a FloraEntity by looking for an entity by a specified name in the module
  // If the entity does not exist, the name is set to null
  public FloraEntity(FloraController fc, String name) {
    if (fc.isEntity(name)) {
      this.name = name;
      this.methods = fc.getMethods(name);
      this.values = fc.getValues(name);
    } else {
      this.name = null;
    }
  }

  // Creates a FloraEntity from two Response objects and a name
  public FloraEntity(FloraController fc, String name, Response methods, Response values) {
    this.name = name;
    this.methods = methods;
    this.values = values;
  }

  @Override
  public String toString() {
    if (this.methods == null | this.values == null) {
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

  /**
   * If the entity contains a field with the given name,
   * its value will be changed to the one given.
   */
  public Boolean setMethod(String name, String value) {
    if(this.methods.contains(name)) {
      int idx = this.methods.indexOf(name);
      this.values.set(idx, value);
      return true;
    }
    return false;
  }

  /** 
   * Returns the value of a given method name if it exists.
   * Will return 'INVALID' otherwise.
   */
  public String getValue(String name) {
    if(this.methods.contains(name)) {
      int idx = this.methods.indexOf(name);
      return this.values.get(idx);
    }
    return "INVALID";
  }

  /**
   * Adds a method to the Entity with a given value.
   * Value defaults to ""
   * Fails if it already exists.
   */
  public Boolean addMethod(String name) {
    return this.addMethod(name, "");
  }

  public Boolean addMethod(String name, String value) {
    if(this.methods.contains(name)) {
      return false;
    }
    this.methods.add(name);
    this.methods.add("");
    return true;
  }
}