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

  public FloraEntity(FloraController fc, String name) {
    if (fc.isEntity(name)) {
      this.name = name;
      this.methods = fc.getMethods(name);
      this.values = fc.getValues(name);
    }
  }

  public String toString() {
    return this.name + "\n[" + this.methods.toString() + "\n]\n[" + this.values.toString() + "\n]\n";
  }

  public Response getMethods() {
    return this.methods;
  }

  public Response getValues() {
    return this.values;
  }
}