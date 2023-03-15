package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableColumnGroupDescriptor;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.rolodex.model.Employee;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Phone;
import goedegep.rolodex.model.PhoneAddressBookEntry;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.PhoneNumberHolder;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.string.StringUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;

/**
 * This class provides a window with phone memories.
 */
public class PhoneMemoriesWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhoneMemoriesWindow.class.getName());
  private static final String WINDOW_TITLE   = "Phone memories";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private Rolodex rolodex;
  private CustomizationFx customization;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<EObject> phoneMemoriesTable;
  
  private EFactory eFactory;
  private EClass memoryEntriesClass;
  private EAttribute entriesAttribute;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhoneMemoriesWindow(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        phoneMemoriesTable.setFilterExpression(newValue, null);
      }
      
    });
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The phone memories table</li>
   * <li>A panel to add a new entry</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createPhoneMemoriesTable());
        
    setScene(new Scene(rootLayout, 700, 700));
  }
  
  /**
   * Create the addressesTable.
   * 
   * @return the created citiesTable
   */
  private EObjectTable<EObject> createPhoneMemoriesTable() {
    createMemoryEntriesModel();
    List<Phone> phonesWithMemory = getPhonesWithMemory();
    List<EObject> items = fillData(phonesWithMemory);
    phoneMemoriesTable = new EObjectTable<EObject>(customization, ROLODEX_PACKAGE.getAddressHolder(), new PhoneMemoriesTableDescriptor(customization, phonesWithMemory, entriesAttribute), items);
        
    return phoneMemoriesTable;
  }

  private List<Phone> getPhonesWithMemory() {
    List<Phone> phonesWithMemory = new ArrayList<>();
    
    for (Phone phone: rolodex.getPhoneList().getPhones()) {
      if (phone.getPhoneAddressBook() != null) {
        phonesWithMemory.add(phone);
      }
    }
    
    return phonesWithMemory;
  }
  private List<EObject> fillData(List<Phone> phonesWithMemory) {
    if (phonesWithMemory.isEmpty()) {
      return null;
    }

    List<EObject> items = new ArrayList<>();
    
    Phone phone = phonesWithMemory.get(0);
       
    // Add each entry of the first phone as an item and maintain a map of phone numbers to item.
    Map<PhoneNumber, EObject> phoneNumbersToItem = new HashMap<>();
    for (PhoneAddressBookEntry entry: phone.getPhoneAddressBook().getEntries()) {
      EObject memoryEntries = eFactory.create(memoryEntriesClass);
      @SuppressWarnings("unchecked")
      List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
      entries.add(entry);
      items.add(memoryEntries);
      
      phoneNumbersToItem.put(entry.getPhoneNumber(), memoryEntries);
    }
    
    // Add entries of other phones
    for (int phoneIndex = 1; phoneIndex < phonesWithMemory.size(); phoneIndex++) {
      phone = phonesWithMemory.get(phoneIndex);
      
      for (PhoneAddressBookEntry entry: phone.getPhoneAddressBook().getEntries()) {
        EObject memoryEntries = phoneNumbersToItem.get(entry.getPhoneNumber());
        
        if (memoryEntries != null) {
          // Add entry to existing item
          @SuppressWarnings("unchecked")
          List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
          while (entries.size() < phoneIndex) {
            entries.add(ROLODEX_FACTORY.createPhoneAddressBookEntry());
          }
          entries.add(entry);
        } else {
          memoryEntries = eFactory.create(memoryEntriesClass);
          @SuppressWarnings("unchecked")
          List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
          while (entries.size() < phoneIndex) {
            entries.add(ROLODEX_FACTORY.createPhoneAddressBookEntry());
          }
          entries.add(entry);
          items.add(memoryEntries);
          
          phoneNumbersToItem.put(entry.getPhoneNumber(), memoryEntries);
        }
            
      }
    }
    
    return items;
  }

  private void createMemoryEntriesModel() {
    EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
    memoryEntriesClass = EcoreFactory.eINSTANCE.createEClass();
    memoryEntriesClass.setName("MemoryEntries");
    
    entriesAttribute = EcoreFactory.eINSTANCE.createEAttribute();
    entriesAttribute.setName("entries");
    entriesAttribute.setEType(ROLODEX_PACKAGE.getPhoneAddressBookEntry());
    entriesAttribute.setLowerBound(0);
    entriesAttribute.setUpperBound(EStructuralFeature.UNBOUNDED_MULTIPLICITY);
    memoryEntriesClass.getEStructuralFeatures().add(entriesAttribute);
    
    ePackage.getEClassifiers().add(memoryEntriesClass);
    eFactory = ePackage.getEFactoryInstance();
//    eFactory = EcoreFactory.eINSTANCE.createEFactory();
    
//    EAttribute phoneNumberHolderAttribute = EcoreFactory.eINSTANCE.createEAttribute();
//    phoneNumberHolderAttribute.setEType(ROLODEX_PACKAGE.getPhoneNumberHolder());
//    memoryEntriesClass.getEAttributes().add(phoneNumberHolderAttribute);
//    
//    EAttribute phoneNumberAttribute = EcoreFactory.eINSTANCE.createEAttribute();
//    phoneNumberHolderAttribute.setEType(ROLODEX_PACKAGE.getPhoneNumberHolder());
//    memoryEntriesClass.getEAttributes().add(phoneNumberHolderAttribute);
//    
//    nameAttribute.setName("name");
//    nameAttribute.setEType(EcorePackage.eINSTANCE.getEString());
//    personClass.getEAttributes().add(nameAttribute);
//    pack.getEClassifiers().add(personClass);
//    EFactory factory = EcoreFactory.eINSTANCE.createEFactory();
//    EObject person = factory.create(personClass);
//    person.eSet(nameAttribute, "Jonas");
  }
  
}


/**
 * This class provides the descriptor for the PhoneMemories Table.
 */
class PhoneMemoriesTableDescriptor extends EObjectTableDescriptor<EObject> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhoneMemoriesTableDescriptor.class.getName());
//  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  private EAttribute entriesAttribute;
  
  
  public PhoneMemoriesTableDescriptor(CustomizationFx customization, List<Phone> phonesWithMemory, EAttribute entriesAttribute) {
    super("There are no memory entries to show", null, null, null);
    
    this.entriesAttribute = entriesAttribute;
    List<EObjectTableColumnDescriptorAbstract<EObject>> columnDescriptors = new ArrayList<>();
    
    for (int index = 0; index < phonesWithMemory.size(); index++) {
      EObjectTableColumnGroupDescriptor<EObject> phoneMemoryDescriptor = createPhoneMemoryDescriptor(phonesWithMemory, index);
      columnDescriptors.add(phoneMemoryDescriptor);
    }
    
    EObjectTableColumnDescriptorCustom<EObject> nameColumnDescriptor = new EObjectTableColumnDescriptorCustom<EObject>(null, "Name", null, true, true, column -> {
      TableCell<EObject, Object> cell = new TableCell<>() {

        @Override
        protected void updateItem(Object item, boolean empty) {  
          super.updateItem(item, empty);
          if(empty || (item == null)) {
            setText(null);
            setGraphic(null);
          }
          else {
            setText(null);
            setGraphic(null);
            EObject memoryEntries = (EObject) item;
            @SuppressWarnings("unchecked")
            List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
            PhoneNumber phoneNumber = null;
            for (PhoneAddressBookEntry entry: entries) {
              phoneNumber = entry.getPhoneNumber();
              if (phoneNumber != null) {
                break;
              }
            }
            if (phoneNumber != null) {
              setText(getNameForPhoneNumberHolders(phoneNumber.getNumberHolders()));
            } else {
              setText("no entry");
            }
          }
        }
      };

      return cell;
    });
    columnDescriptors.add(nameColumnDescriptor);
    
    EObjectTableColumnDescriptorCustom<EObject> phoneNumberColumnDescriptor = new EObjectTableColumnDescriptorCustom<EObject>(null, "Phone number", null, true, true, column -> {
      TableCell<EObject, Object> cell = new TableCell<>() {

        @Override
        protected void updateItem(Object item, boolean empty) {  
          super.updateItem(item, empty);
          if(empty || (item == null)) {
            setText(null);
            setGraphic(null);
          }
          else {
            setText(null);
            setGraphic(null);
            EObject memoryEntries = (EObject) item;
            @SuppressWarnings("unchecked")
            List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
            PhoneNumber phoneNumber = null;
            for (PhoneAddressBookEntry entry: entries) {
              phoneNumber = entry.getPhoneNumber();
              if (phoneNumber != null) {
                break;
              }
            }
            if (phoneNumber != null) {
              setText(phoneNumber.getPhoneNumber());
            } else {
              setText("no entry");
            }
          }
        }
      };

      return cell;
    });
    columnDescriptors.add(phoneNumberColumnDescriptor);
    
    EObjectTableColumnDescriptorCustom<EObject> descriptionColumnDescriptor = new EObjectTableColumnDescriptorCustom<EObject>(null, "Description", null, true, true, column -> {
      TableCell<EObject, Object> cell = new TableCell<>() {

        @Override
        protected void updateItem(Object item, boolean empty) {  
          super.updateItem(item, empty);
          if(empty || (item == null)) {
            setText(null);
            setGraphic(null);
          }
          else {
            setText(null);
            setGraphic(null);
            EObject memoryEntries = (EObject) item;
            @SuppressWarnings("unchecked")
            List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
            PhoneNumber phoneNumber = null;
            for (PhoneAddressBookEntry entry: entries) {
              phoneNumber = entry.getPhoneNumber();
              if (phoneNumber != null) {
                break;
              }
            }
            if (phoneNumber != null) {
              setText(phoneNumber.getDescription());
            } else {
              setText("no entry");
            }
          }
        }
      };

      return cell;
    });
    columnDescriptors.add(descriptionColumnDescriptor);
    
    EObjectTableColumnDescriptorCustom<EObject> connectionTypeColumnDescriptor = new EObjectTableColumnDescriptorCustom<EObject>(null, "Connection type", null, true, true, column -> {
      TableCell<EObject, Object> cell = new TableCell<>() {

        @Override
        protected void updateItem(Object item, boolean empty) {  
          super.updateItem(item, empty);
          if(empty || (item == null)) {
            setText(null);
            setGraphic(null);
          }
          else {
            setText(null);
            setGraphic(null);
            EObject memoryEntries = (EObject) item;
            @SuppressWarnings("unchecked")
            List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
            PhoneNumber phoneNumber = null;
            for (PhoneAddressBookEntry entry: entries) {
              phoneNumber = entry.getPhoneNumber();
              if (phoneNumber != null) {
                break;
              }
            }
            if (phoneNumber != null) {
              setText(phoneNumber.getConnectionType().getName());
            } else {
              setText("no entry");
            }
          }
        }
      };

      return cell;
    });
    columnDescriptors.add(connectionTypeColumnDescriptor);
    
    setColumnDescriptors(columnDescriptors);
    
  }

  /**
   * Create group column descriptor for a phone.
   * 
   * @param phones list of all phones
   * @param index the index of the phone for which to create the descriptor.
   * @return the created column descriptor.
   */
  EObjectTableColumnGroupDescriptor<EObject> createPhoneMemoryDescriptor(List<Phone> phones, int index) {
    Phone phone = phones.get(index);
    
    EObjectTableColumnDescriptorCustom<EObject> entryNameColumnDescriptor = new EObjectTableColumnDescriptorCustom<EObject>(null, "Name", null, true, true, column -> {
      TableCell<EObject, Object> cell = new TableCell<>() {

        @Override
        protected void updateItem(Object item, boolean empty) {  
          super.updateItem(item, empty);
          if(empty || (item == null)) {
            setText(null);
            setGraphic(null);
          }
          else {
            setText(null);
            setGraphic(null);
            EObject memoryEntries = (EObject) item;
            @SuppressWarnings("unchecked")
            List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
            if (entries.size() > index) {
              PhoneAddressBookEntry entry = entries.get(index);
              setText(entry.getEntryName());
            } else {
              setText("no entry");
            }
          }
        }
      };

      return cell;
    });
    
    EObjectTableColumnDescriptorCustom<EObject> entryTypeColumnDescriptor = new EObjectTableColumnDescriptorCustom<EObject>(null, "Type", null, true, true, column -> {
      TableCell<EObject, Object> cell = new TableCell<>() {

        @Override
        protected void updateItem(Object item, boolean empty) {  
          super.updateItem(item, empty);
          if(empty || (item == null)) {
            setText(null);
            setGraphic(null);
          }
          else {
            setText(null);
            setGraphic(null);
            EObject memoryEntries = (EObject) item;
            @SuppressWarnings("unchecked")
            List<PhoneAddressBookEntry> entries = (List<PhoneAddressBookEntry>) memoryEntries.eGet(entriesAttribute);
            if (entries.size() > index) {
              PhoneAddressBookEntry entry = entries.get(index);
              setText(entry.getEntryType().getLiteral());
            } else {
              setText("no entry");
            }
          }
        }
      };

      return cell;
    });    
    
    
    EObjectTableColumnGroupDescriptor<EObject> phoneMemoryDescriptor = new EObjectTableColumnGroupDescriptor<EObject>(phone.getPhoneType(), entryNameColumnDescriptor, entryTypeColumnDescriptor);

    return phoneMemoryDescriptor;
  }
  
  private String getNameForPhoneNumberHolders(List<PhoneNumberHolder> phoneNumberHolders) {
    List<String> names = new ArrayList<>();
    for (PhoneNumberHolder phoneNumberHolder: phoneNumberHolders) {
      names.add(getNameForPhoneNumberHolder(phoneNumberHolder));
    }
    return StringUtil.stringCollectionToCommaSeparatedStrings(names);
  }

  private String getNameForPhoneNumberHolder(PhoneNumberHolder phoneNumberHolder) {
    String name = null;
    
    if (phoneNumberHolder instanceof Person) {
      name = ((Person) phoneNumberHolder).getName();
    } else if (phoneNumberHolder instanceof Family) {
      Family family = (Family) phoneNumberHolder;
      name = family.getFamilyTitle() + " " + family.getFamilyName();
    } else if (phoneNumberHolder instanceof Institution) {
      name = ((Institution) phoneNumberHolder).getName();
    } else if (phoneNumberHolder instanceof Employee) {
      Employee employee = (Employee) phoneNumberHolder;
      name = employee.getPerson().getName() + " at " + employee.getInstitution().getName();
    }
    
    return name;
  }
}
