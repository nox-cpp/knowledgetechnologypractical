# Using the FloraController class
This readme contains some examples of how to use the various functions provided by the FloraController class

```java
// Create a new FloraController type
FloraController floraController = new FloraController();
System.out.println("Flora-2 session started");
// Load the knowledgebase into the Flora session
floraController.loadModel();
// Ask the model to give info on all its persons
System.out.println(floraController.queryModel("?X:person@knowledgebase."));
// Query the model for persons that fit a specific profile
System.out.println(floraController.queryModel("?X:person[age -> 21]@knowledgebase."));
// Trying a complex query
System.out.println("A complex response\n" + floraController.queryModel("?X[relatives->?Y]@knowledgebase."));
// Now we modify one of the instances using a command and afterwards we check the values again
String command = "delete{Marietje[relatives->{Tim}]}.";
floraController.commandModel(command);
System.out.println("A complex response\n" + floraController.queryModel("?X[relatives->?Y]@knowledgebase."));
// Listing all entities in the kb
System.out.println(floraController.listEntities());
// Listing all methods to an entity (NOTE: it must be one that exists, for now)
System.out.println(floraController.listMethods("Tim"));
// Close the FloraSession prior to exiting the program
floraController.closeSession();

////
System.out.println(floraController.getMethods("Tim"));
System.out.println(floraController.getValues("Tim"));
System.out.println(floraController.getEntity("Tim").toString());
```

This should get you something like the following output:

```
Flora-2 session started
Knowledgebase loaded successfully.

Marnix
Tim
Marietje

Marnix
A complex response

Tim
Marietje
A complex response

Tim
Marietje

\object
\modular
\callable
\integer
\decimal
\double

age
relatives
risk_level
has_disease


//////

age
relatives
risk_level
has_disease

17
Marietje
overwhelming
true
Tim
[
age
relatives
risk_level
has_disease
]
[
17
Marietje
overwhelming
true
]
```

## Updating and changing the knowledgebase using the floraController class
```java
// Load the information about an entity into a variable
FloraEntity fe = floraController.getEntity("Tim");
// Modify the information
fe.setName("Peter");
// Add new information to the kb
floraController.addFact(fe);
System.out.println(floraController.isEntity("Peter"));
System.out.println(floraController.getEntity("Peter"));
System.out.println(floraController.getEntity("Tim"));
// Delete information from the kb (NOTE: This does not delete the name/id from the kb, so far this seems impossible)
floraController.deleteFact("Tim");
System.out.println(floraController.getEntity("Tim").toString());
fe.setName("Tim");
// Change the information of some fact
floraController.updateFact(fe);
System.out.println(floraController.updateFact(fe));
System.out.println(floraController.getEntity("Tim").toString());
```

```bash
false
null
Tim
[
age
relatives
risk_level
has_disease
]
[
17
Marietje
overwhelming
true
]

Tim
[
]
[
]

true
Tim
[
age
relatives
risk_level
has_disease
]
[
17
Marietje
overwhelming
true
]
```

## Changing an entity in the knowledgebase
```java
FloraEntity fe = floraController.getEntity("Tim");
System.out.println(fe);
fe.setMethod("age", "18");
floraController.updateFact(fe);
System.out.println(fe);
```
```bash
Tim
[
age
relatives
risk_level
has_disease
]
[
17
Marietje
overwhelming
true
]

Tim
[
age
relatives
risk_level
has_disease
]
[
18
Marietje
overwhelming
true
]
```
Adding a field is done in a similar fashing using the ` fe.addMethod(String name, String value)` method.
## Getting the value of a field
```java
FloraEntity fe = floraController.getEntity("Tim");
System.out.println(fe.getValue("has_disease"));
```
```bash
true
```