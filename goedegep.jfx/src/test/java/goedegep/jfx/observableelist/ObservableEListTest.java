package goedegep.jfx.observableelist;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import goedegep.util.logging.MyLoggingFormatter;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.util.Pair;


public class ObservableEListTest {
  private static String NEWLINE = System.getProperty("line.separator");
  
  /*
   * The created Person data model items.
   */
  private static EPackage personsPackage = null;
  private static EFactory personsFactory = null;
  
  private static EClass personEClass = null;
  private static EAttribute person_name = null;
  private static EReference person_children = null;
  
  private static EClass personListEClass = null;
  private static EReference personList_persons = null;
  
  // changes received
  private Change<? extends Person> change;
  private Change<? extends EObject> eChange;
  
  static {
    logSetup(Level.SEVERE, null);
    createPersonsDataModel();
  }
  
  @BeforeEach
  public void clearChanges() {
    change = null;
    eChange = null;
  }
  
  /**
   * Test firing a change on list.add(element), both on the EList and on the ObservableEList.
   */
  @Test
  public void testAdd() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    
    Person person = new Person("Six");
    observablePersonsList.add(person);
    
    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    EObject ePerson = createEPerson("Six");
    ePersonsEListPersons.add(ePerson);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
    
    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePerson = createEPerson("Six");
    ePersonEList.add(ePerson);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.add(index, element), both on the EList and on the ObservableEList.
   */
  @Test
  public void testAddAtIndex() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();

    Person person = new Person("Six");
    observablePersonsList.add(2, person);

    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    EObject ePerson = createEPerson("Six");
    ePersonsEListPersons.add(2, ePerson);

    assertTrue(compareChanges(change, eChange), "eChange has wrong value");

    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePerson = createEPerson("Six");
    ePersonEList.add(2, ePerson);

    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.addAll(Collection elements), both on the EList and on the ObservableEList.
   */
  @Test
  public void testAddAllCollection() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();

    List<Person> personsToAdd = new ArrayList<>();
    Person person = new Person("Six");
    personsToAdd.add(person);
    person = new Person("Seven");
    personsToAdd.add(person);

    observablePersonsList.addAll(personsToAdd);

    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    List<EObject> ePersonsToAdd = new ArrayList<>();
    EObject ePerson = createEPerson("Six");
    ePersonsToAdd.add(ePerson);
    ePerson = createEPerson("Seven");
    ePersonsToAdd.add(ePerson);
    ePersonsEListPersons.addAll(ePersonsToAdd);

    assertTrue(compareChanges(change, eChange), "eChange has wrong value");

    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePersonsToAdd = new ArrayList<>();
    ePerson = createEPerson("Six");
    ePersonsToAdd.add(ePerson);
    ePerson = createEPerson("Seven");
    ePersonsToAdd.add(ePerson);
    ePersonEList.addAll(ePersonsToAdd);

    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.addAll(index, Collection elements), both on the EList and on the ObservableEList.
   */
  @Test
  public void testAddAllCollectionAtIndex() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    
    List<Person> personsToAdd = new ArrayList<>();
    Person person = new Person("Four");
    personsToAdd.add(person);
    person = new Person("Five");
    personsToAdd.add(person);
    
    observablePersonsList.addAll(2, personsToAdd);
    
    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    List<EObject> ePersonsToAdd = new ArrayList<>();
    EObject ePerson = createEPerson("Four");
    ePersonsToAdd.add(ePerson);
    ePerson = createEPerson("Five");
    ePersonsToAdd.add(ePerson);
    ePersonsEListPersons.addAll(2, ePersonsToAdd);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
    
    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePersonsToAdd = new ArrayList<>();
    ePerson = createEPerson("Four");
    ePersonsToAdd.add(ePerson);
    ePerson = createEPerson("Five");
    ePersonsToAdd.add(ePerson);
    ePersonEList.addAll(2, ePersonsToAdd);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.clear(Collection elements), both on the EList and on the ObservableEList.
   */
  @Test
  public void testClear() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    
    observablePersonsList.clear();
    
    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    
    ePersonsEListPersons.clear();
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
    
    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePersonEList.clear();
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.remove(index), both on the EList and on the ObservableEList.
   */
  @Test
  public void testRemoveIndex() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    
    observablePersonsList.remove(1);
    
    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    
    ePersonsEListPersons.remove(1);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
    
    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePersonEList.remove(1);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.remove(Object), both on the EList and on the ObservableEList.
   */
  @Test
  public void testRemoveObject() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    
    observablePersonsList.remove(observablePersonsList.get(1));
    
    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    
    ePersonsEListPersons.remove(ePersonsEListPersons.get(1));
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
    
    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePersonEList.remove(ePersonEList.get(1));
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.remove(from, to), on the ObservableEList (not on the EList as that doesn't support remove(from, to)).
   */
  @Test
  public void testRemoveFromTo() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    
    observablePersonsList.remove(0, 2);
    
    // Test EList - Not applicable as List and EList don't support remove(from, to)
    
    // Test ObservableEList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePersonEList.remove(0, 2);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on list.removeAll(Collection), both on the EList and on the ObservableEList.
   */
  @Test
  public void testRemoveAllCollection() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    List<Person> personsToRemove = new ArrayList<>();
    personsToRemove.add(observablePersonsList.get(0));
    personsToRemove.add(observablePersonsList.get(3));
    
    observablePersonsList.removeAll(personsToRemove);
    
    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    List<EObject> ePersonsToRemove = new ArrayList<>();
    ePersonsToRemove.add(ePersonsEListPersons.get(0));
    ePersonsToRemove.add(ePersonsEListPersons.get(3));
    
    ePersonsEListPersons.removeAll(ePersonsToRemove);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
    
    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePersonsToRemove = new ArrayList<>();
    ePersonsToRemove.add(ePersonEList.get(0));
    ePersonsToRemove.add(ePersonEList.get(3));
    
    ePersonEList.removeAll(ePersonsToRemove);
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Test firing a change on updating an item in the list, both on the EList and on the ObservableEList.
   */
  @Test
  public void testUpdate() {
    // Reference
    ObservableList<Person> observablePersonsList = createObservablePersonsList();
    Person person = observablePersonsList.get(1);
    person.setPersonsName("Peter");
    
    // Test EList
    Pair<EObject, ObservableList<EObject>> ePersonListPair = createObservableEPersonsEList();
    @SuppressWarnings("unchecked")
    EList<EObject> ePersonsEListPersons = (EList<EObject>) ePersonListPair.getKey().eGet(personList_persons);
    EObject ePerson = ePersonsEListPersons.get(1);
    ePerson.eSet(person_name, "Peter");
    
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
    
    // Test ObservableEList
    eChange = null;
    ePersonListPair = createObservableEPersonsEList();
    ObservableList<EObject> ePersonEList = ePersonListPair.getValue();
    ePerson = ePersonEList.get(1);
    ePerson.eSet(person_name, "Peter");
        
    assertTrue(compareChanges(change, eChange), "eChange has wrong value");
  }
  
  /**
   * Create an Observable list with some persons.
   */
  private ObservableList<Person> createObservablePersonsList() {
    List<Person> persons = new ArrayList<>();
    persons.add(new Person("One"));
    persons.add(new Person("Two"));
    persons.add(new Person("Three"));
    persons.add(new Person("Four"));
    persons.add(new Person("Five"));
    
    Callback<Person, Observable[]> extractor = person -> {
      System.out.println("extractor called");
      return new Observable[] {
          person.nameProperty()
      };
    };
    ObservableList<Person> observablePersonsList = FXCollections.observableList(persons, extractor);
    
    ListChangeListener<Person> lcl = new ListChangeListener<>() {

      @Override
      public void onChanged(Change<? extends Person> c) {
        System.out.println(changeToString(c));
        if (change != null) {
          throw new RuntimeException("eChange alread set");
        }
        change = c;
        for (Person person: observablePersonsList) {
          System.out.println(person.getPersonsName());
        }
        
      }
      
    };
    observablePersonsList.addListener(lcl);
    
    return observablePersonsList;
  }
  
  /**
   * Create an ObservableEList with some ePersons
   */
  private Pair<EObject, ObservableList<EObject>> createObservableEPersonsEList() {
    EObject personList = personsFactory.create(personListEClass);
    @SuppressWarnings("unchecked")
    EList<EObject> personListPersons = (EList<EObject>) personList.eGet(personList_persons);
    
    personListPersons.add(createEPerson("One"));
    personListPersons.add(createEPerson("Two"));
    personListPersons.add(createEPerson("Three"));
    personListPersons.add(createEPerson("Four"));
    personListPersons.add(createEPerson("Five"));
    
    ObservableEList<EObject> observablePersonsEList = new ObservableEList<>(false, personList, personList_persons);
    
    ListChangeListener<EObject> eListChangeListener = new ListChangeListener<>() {

      @Override
      public void onChanged(Change<? extends EObject> c) {
        if (eChange != null) {
          throw new RuntimeException("eChange alread set");
        }
        eChange = c;
        System.out.println(changeToString(c));
        for (EObject eObject: observablePersonsEList) {
          System.out.println(eObject.eGet(person_name));
        }
      }
      
    };
    observablePersonsEList.addListener(eListChangeListener);
    
    return new Pair<EObject, ObservableList<EObject>>(personList, observablePersonsEList);
  }
  
  private static EObject createEPerson(String name) {
    EObject ePerson = personsFactory.create(personEClass);
    ePerson.eSet(person_name, name);
    
    return ePerson;
  }
  
  private static void createPersonsDataModel() {
    personsPackage = EcoreFactory.eINSTANCE.createEPackage();
    
    // Create the personEClass
    personEClass = EcoreFactory.eINSTANCE.createEClass();
    personEClass.setName("Person");
    EList<EStructuralFeature> structuralFeatures = personEClass.getEStructuralFeatures();
    
    person_name = EcoreFactory.eINSTANCE.createEAttribute();
    person_name.setName("name");
    person_name.setEType(EcorePackage.eINSTANCE.getEString());
    structuralFeatures.add(person_name);
    
    person_children = EcoreFactory.eINSTANCE.createEReference();
    person_children.setName("children");
    person_children.setUpperBound(-1);
    person_children.setContainment(true);
    person_children.setEType(personEClass);
    structuralFeatures.add(person_children);
    
    personsPackage.getEClassifiers().add(personEClass);
    
    // Create the personListEClass
    personListEClass = EcoreFactory.eINSTANCE.createEClass();
    personListEClass.setName("PersonList");
    structuralFeatures = personListEClass.getEStructuralFeatures();
   
    personList_persons = EcoreFactory.eINSTANCE.createEReference();
    personList_persons.setName("persons");
    personList_persons.setUpperBound(-1);
    personList_persons.setContainment(true);
    personList_persons.setEType(personEClass);
    structuralFeatures.add(personList_persons);
    
    personsPackage.getEClassifiers().add(personListEClass);
    
    personsFactory = personsPackage.getEFactoryInstance();
  }
  
  public static String changeToString(Change<?> change) {
    StringBuilder buf = new StringBuilder();

    while (change.next()) {    
      buf.append("from: ").append(change.getFrom()).append(NEWLINE);
      buf.append("to: ").append(change.getTo()).append(NEWLINE);
      buf.append("addedSize: ").append(change.getAddedSize()).append(NEWLINE);
      buf.append("removedSize: ").append(change.getRemovedSize()).append(NEWLINE);
      buf.append("wasAdded: ").append(change.wasAdded()).append(NEWLINE);
      buf.append("wasPermutated: ").append(change.wasPermutated()).append(NEWLINE);
      if (change.wasPermutated()) {
        buf.append("permutation: ").append(change.getPermutation(0)).append(NEWLINE);  // how to know the range
      }
      buf.append("wasRemoved: ").append(change.wasRemoved()).append(NEWLINE);
      buf.append("wasReplaced: ").append(change.wasReplaced()).append(NEWLINE);
      buf.append("wasUpdated: ").append(change.wasUpdated()).append(NEWLINE);
      List<?> addedList = change.getAddedSubList();
      if (addedList.isEmpty()) {
        buf.append("Added list is empty ").append(NEWLINE);
      } else {
        for (Object object: addedList) {
          buf.append("Added object: ").append(listItemToString(object)).append(NEWLINE);
        }
      }
      ObservableList<?> list = change.getList();
      if (list.isEmpty()) {
        buf.append("List is empty ").append(NEWLINE);
      } else {
        for (Object object: list) {
          buf.append("List object: ").append(listItemToString(object)).append(NEWLINE);
        }
      }
      List<?> removedList = change.getRemoved();
      if (removedList.isEmpty()) {
        buf.append("Removed list is empty ").append(NEWLINE);
      } else {
        for (Object object: removedList) {
          buf.append("Removed object: ").append(listItemToString(object)).append(NEWLINE);
        }
      }
    }

    return buf.toString();
  }
  
  private static String listItemToString(Object item) {
    if (item instanceof Person person) {
      return person.toString();
    } else if (item instanceof EObject eObject) {
      return (String) eObject.eGet(person_name);
    } else {
      throw new IllegalArgumentException();
    }
  }
  
  private boolean compareChanges(Change<?> change1, Change<?> change2) {
    if (change1 == null  ||  change2 == null) {
      return false;
    }
    change1.reset();
    change2.reset();

    while (change1.next()) {
      if (!change2.next()) {
        return false;
      }
      // from
      if (change1.getFrom() != change2.getFrom()) {
        return false;
      }
      
      // to
      if (change1.getTo() != change2.getTo()) {
        return false;
      }
      
      // addedSize
      if (change1.getAddedSize() != change2.getAddedSize()) {
        return false;
      }
      
      // removedSize
      if (change1.getRemovedSize() != change2.getRemovedSize()) {
        return false;
      }
      
      // wasAdded
      if (change1.wasAdded() != change2.wasAdded()) {
        return false;
      }
      
      // wasPermutated
      if (change1.wasPermutated() != change2.wasPermutated()) {
        return false;
      }
            
      // permutation
      if (change1.wasPermutated()) {
        if (change1.getPermutation(0) != change2.getPermutation(0)) {  // TODO
          return false;
        }
      }
      
      // wasRemoved
      if (change1.wasRemoved() != change2.wasRemoved()) {
        return false;
      }
      
      // wasReplaced
      if (change1.wasReplaced() != change2.wasReplaced()) {
        return false;
      }
      
      // wasUpdated
      System.out.println("Updated: " + change1.wasUpdated() + " <=> " + change2.wasUpdated());
      if (change1.wasUpdated() != change2.wasUpdated()) {
        return false;
      }
      
      // Added list
      List<?> addedList1 = change1.getAddedSubList();
      List<?> addedList2 = change2.getAddedSubList();
      if (addedList1.size() != addedList2.size()) {
        return false;
      }
      for (int i = 0; i < addedList1.size(); i++) {
        String item1Sting = listItemToString(addedList1.get(i));
        String item2Sting = listItemToString(addedList2.get(i));
        if (!item1Sting.equals(item2Sting)) {
          return false;
        }
      }
      
      // List
      List<?> list1 = change1.getList();
      List<?> list2 = change2.getList();
      if (list1.size() != list2.size()) {
        return false;
      }
      for (int i = 0; i < list1.size(); i++) {
        String item1Sting = listItemToString(list1.get(i));
        String item2Sting = listItemToString(list2.get(i));
        if (!item1Sting.equals(item2Sting)) {
          return false;
        }
      }
      
      // Removed list
      List<?> removedList1 = change1.getRemoved();
      List<?> removedList2 = change2.getRemoved();
      if (removedList1.size() != removedList2.size()) {
        return false;
      }
      for (int i = 0; i < removedList1.size(); i++) {
        String item1Sting = listItemToString(removedList1.get(i));
        String item2Sting = listItemToString(removedList2.get(i));
        if (!item1Sting.equals(item2Sting)) {
          return false;
        }
      }      
    }
    
    return !change2.next();
  }
  
  /**
   * Logging setup.
   * <p>
   * The following setup is performed:
   * <ul>
   * <li>Set the specified logging level</li>
   * <li>Install a {@link MyLoggingFormatter}.</li>
   * <li>Install logging to a file, if a logFileBaseName is specified.</li>
   * </ul>
   * 
   * @param level the logging level to be set up.
   * @param logFileBaseName base name of the file to which logging information will be written. The actual filename is this base name with ".txt" appended to it.
   *                        If null, no file logging takes place.
   */
  protected static void logSetup(Level level, String logFileBaseName) {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(level);

    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(level);

    if (logFileBaseName != null) {
      try {
        FileHandler fileHandler = new FileHandler(logFileBaseName + ".txt", false);   // true forces append mode
        Formatter simpleFormatter = new MyLoggingFormatter();
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);
      } catch (SecurityException | IOException e) {
        e.printStackTrace();
      }
    }

  }
}

class Person {
  private String name = "John Doe";
  private StringProperty nameProperty = new SimpleStringProperty(name);
  
  public Person(String name) {
    this.name = name;
  }
  
  public Observable nameProperty() {
    return nameProperty;
  }

  public void setPersonsName(String name) {
    this.name = name;
    nameProperty.set(name);
  }
  
  public String getPersonsName() {
    return name;
  }
  
  public String toString() {
    return name;
  }
}
