package goedegep.appgen.checkboxtree;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import goedegep.appgen.CheckBoxTreeCellEditor;
import goedegep.appgen.CheckBoxTreeCellRenderer;
import goedegep.appgen.CheckBoxTreeNodeUserObjectWrapper;
import goedegep.appgen.ConfigurableTreeCellEditor;
import goedegep.appgen.ConfigurableTreeCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.DefaultCustomization;
import goedegep.util.logging.MyLoggingFormatter;


@SuppressWarnings("serial")
public class CheckBoxTreeExample extends AppFrame {
  private final static Logger LOGGER = Logger.getLogger(CheckBoxTreeExample.class.getName()); 
  private static final Level logLevel = Level.INFO;
  
  public CheckBoxTreeExample() {
    super("Personen uit families selecteren", DefaultCustomization.getInstance());
    
    List<Family> families = createSomeFamilies();
    
    // Create the data model for the Tree, using the wrapper classes.
    final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Families");
    
    for (Family family: families) {
      DefaultMutableTreeNode familyNode = new DefaultMutableTreeNode(new CheckBoxTreeNodeUserObjectWrapper(new FamilyWrapper(family)));
      rootNode.add(familyNode);
      
      for (Person person: family.getFamilyMembers()) {
        DefaultMutableTreeNode familyMemberNode = new DefaultMutableTreeNode(new CheckBoxTreeNodeUserObjectWrapper(new PersonWrapper(person)));
        familyNode.add(familyMemberNode);
      }
    }

    JTree tree = new JTree(rootNode);

    ConfigurableTreeCellRenderer cellRenderer = new ConfigurableTreeCellRenderer();
    cellRenderer.addClassSpecificCellRenderer(CheckBoxTreeNodeUserObjectWrapper.class, new CheckBoxTreeCellRenderer());
    tree.setCellRenderer(cellRenderer);

    ConfigurableTreeCellEditor cellEditor = new ConfigurableTreeCellEditor(tree, new DefaultTreeCellRenderer());
    cellEditor.addClassSpecificCellEditor(CheckBoxTreeNodeUserObjectWrapper.class, new CheckBoxTreeCellEditor(tree, cellEditor));
    tree.setCellEditor(cellEditor);
    tree.setEditable(true);
    
    tree.getModel().addTreeModelListener(new TreeModelListener() {

      @Override
      public void treeNodesChanged(TreeModelEvent e) {
        LOGGER.info("=>");
        
        List<Family> selectedFamilies = new ArrayList<>();
        List<Person> selectedPersons = new ArrayList<>();
        @SuppressWarnings("rawtypes")
        Enumeration familyNodes = rootNode.children();
        
        while (familyNodes.hasMoreElements()) {
          DefaultMutableTreeNode familyNode = (DefaultMutableTreeNode) familyNodes.nextElement();
          CheckBoxTreeNodeUserObjectWrapper familyObjectWrapper = (CheckBoxTreeNodeUserObjectWrapper) familyNode.getUserObject();
          if (familyObjectWrapper.isSelected()) {
            FamilyWrapper familyWrapper = (FamilyWrapper) familyObjectWrapper.getUserObject();
            selectedFamilies.add(familyWrapper.getFamily());
          }
          
          @SuppressWarnings("rawtypes")
          Enumeration personNodes = familyNode.children();
          while (personNodes.hasMoreElements()) {
            DefaultMutableTreeNode personNode = (DefaultMutableTreeNode) personNodes.nextElement();
            CheckBoxTreeNodeUserObjectWrapper personObjectWrapper = (CheckBoxTreeNodeUserObjectWrapper) personNode.getUserObject();
            if (personObjectWrapper.isSelected()) {
              PersonWrapper personWrapper = (PersonWrapper) personObjectWrapper.getUserObject();
              selectedPersons.add(personWrapper.getPerson());
            }
          }
        }
        
        System.out.println("Geselecteerde families:");
        for (Family family: selectedFamilies) {
          System.out.println("   " + family.toString());
        }
        System.out.println();
        
        System.out.println("Geselecteerde personen:");
        for (Person person: selectedPersons) {
          System.out.println("   " + person.toString());
        }
        
        LOGGER.info("<=");
      }

      @Override
      public void treeNodesInserted(TreeModelEvent e) {
      }

      @Override
      public void treeNodesRemoved(TreeModelEvent e) {
      }

      @Override
      public void treeStructureChanged(TreeModelEvent e) {
      }
      
    });

    getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
    pack();
    setSize(400, 300);
    setLocationRelativeTo(null);
  }
  
  private static List<Family> createSomeFamilies() {
    List<Family> families = new ArrayList<>();
    Family family;
    
    family = new Family("First Family");
    family.getFamilyMembers().add(new Person("FirstFirstname", "First Family"));
    family.getFamilyMembers().add(new Person("SecondFirstname", "First Family"));
    family.getFamilyMembers().add(new Person("ThirdFirstname", "First Family"));
    families.add(family);
    
    family = new Family("Second Family");
    family.getFamilyMembers().add(new Person("FourthFirstname", "Second Family"));
    family.getFamilyMembers().add(new Person("FifthFirstname", "Third Family"));
    family.getFamilyMembers().add(new Person("SixtFirstname", "Second Family"));
    family.getFamilyMembers().add(new Person("seventhFirstname", "Fourth Family"));
    families.add(family);
    
    return families;
  }

  /**
   * Following classes are wrapper classes which will typically be used to show the wanted text in the Tree.
   */

  private class FamilyWrapper {
    Family family;

    public FamilyWrapper(Family family) {
      this.family = family;
    }

    protected Family getFamily() {
      return family;
    }

    public String toString() {
      return "Familie " + family.getFamilyName();
    }
  }

  private class PersonWrapper {
    Person person;

    public PersonWrapper(Person person) {
      this.person = person;
    }

    protected Person getPerson() {
      return person;
    }

    public String toString() {
      return person.getFirstname() + " " + person.getSurname();
    }
  }

  public static void main(String[] args) {
    logSetup();
    LOGGER.info("=>");

    CheckBoxTreeExample checkBoxTreeExample = new CheckBoxTreeExample();
    checkBoxTreeExample.setVisible(true);

    LOGGER.info("<=");
  }

  private static void logSetup() {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(logLevel);

    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(logLevel);
  }
}


/**
 * Following classes are example classes for filling the Tree
 */
class Family {
  private String familyName;
  private List<Person> familyMembers = new ArrayList<>();

  public Family(String familyName) {
    super();
    this.familyName = familyName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public List<Person> getFamilyMembers() {
    return familyMembers;
  }
  
  public String toString() {
    return familyName;
  }
}

class Person {
  private String firstname;
  private String surname;

  public Person(String firstname, String surname) {
    super();
    this.firstname = firstname;
    this.surname = surname;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getSurname() {
    return surname;
  }
  
  public String toString() {
    return firstname + " " + surname;
  }
}
