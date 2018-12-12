package src.java;
import java.util.*;
import net.sf.flora2.API.*;
import net.sf.flora2.API.util.*;

public class Response extends ArrayList<FloraObject> {
    public Response(Iterator<FloraObject> source) {
        while(source.hasNext()) {
            this.add(source.next());
        }
    }

    public String toString() {
        String str = "";
        for (FloraObject obj : this) {
            str += obj.toString();
        }
        return str;
    }
}