package goedegep.rolodex.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectListContainerSpecification;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
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
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.emf.EmfUtil;
import goedegep.util.string.StringUtil;
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
  
  /**
   * The {@code Rolodex} for which the information is shown
   */
  private Rolodex rolodex;
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization;
  /**
   * Control panel for the {@code phoneMemoriesTable}.
   */
  private EObjectTableControlPanel eObjectTableControlPanel;
  /**
   * The {@link EObjectTable} showing the phone memories.
   */
  private EObjectTable<EObject> phoneMemoriesTable;
  
  /*
   * String constants for the EMF data model for the items shown in the table.
   */
  private EFactory eFactory;
  private EClass phoneMemoryEntryClass;
  private EAttribute[] entryNames;
  private EAttribute[] entryTypes;
  private EAttribute holderName;
  private EReference phoneNumberReference;
  private EAttribute description;
  private EAttribute connectionType;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhoneMemoriesWindow(CustomizationFx customization, Rolodex rolodex) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.rolodex = rolodex;
    
    createControls();
    
    createGUI();
        
    show();
  }
  
  /**
   * Create the controls.
   */
  private void createControls() {
    createPhoneMemoriesTable();
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    eObjectTableControlPanel.filterTextProperty().addListener((observable, oldValue, newValue) -> phoneMemoriesTable.setFilterExpression(newValue, null));    
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
    rootLayout.getChildren().add(phoneMemoriesTable);
        
    setScene(new Scene(rootLayout, 700, 700));
  }
  
  /**
   * Create the table for the phone memories.
   * <p>
   * The items shown in the table are specific for this table, so they are not available in a data model.
   * So a 'memory entries model' is created here (by calling {@link createMemoryEntriesModel}.</br>
   * Next the table is created and filled with information from the phones with a memory.
   */
  private void createPhoneMemoriesTable() {
    List<Phone> phonesWithMemory = getPhonesWithMemory();
    
    /*
     * Each entry in the table is ....
     * Therefore the items in the table are of type {@code EObject}.
     */
    createPhoneMemoriesModel(phonesWithMemory.size());
    phoneMemoriesTable = new EObjectTable<EObject>(customization, phoneMemoryEntryClass, new PhoneMemoriesTableDescriptor(phonesWithMemory));
    EObjectListContainerSpecification listContainerSpecification = phoneMemoriesTable.createObjectListContainer();
    List<EObject> list = EmfUtil.getListUnchecked(listContainerSpecification.listContainer(), listContainerSpecification.listReference());
    fillData(phonesWithMemory, list);

    phoneMemoriesTable.setObjects(listContainerSpecification);
  }

  /**
   * Get a list of phones with a phone number memory.
   * 
   * @return the list of phones in the {@code rolodex} which have a phone number memory (phone address book).
   */
  private List<Phone> getPhonesWithMemory() {
    List<Phone> phonesWithMemory = new ArrayList<>();
    
    for (Phone phone: rolodex.getPhoneList().getPhones()) {
      if (phone.getPhoneAddressBook() != null) {
        phonesWithMemory.add(phone);
      }
    }
    
    return phonesWithMemory;
  }
  
  
  /**
   * Fill ... with the information from the phones with a memory.
   * 
   * @param phonesWithMemory
   */
  private void fillData(List<Phone> phonesWithMemory, List<EObject> items) {
    
    /*
     * Strategy:
     * Add the entries of the first phone.
     * After adding each entry, add the entries of the other phones which are for the same phone number.
     * 
     * Repeat for the second phone, etc.
     */
    
    for (int i = 0; i < phonesWithMemory.size(); i++) {
      List<PhoneAddressBookEntry> phoneBookEntries = phonesWithMemory.get(i).getPhoneAddressBook().getEntries();
      
      
      for (PhoneAddressBookEntry phoneAddressBookEntry: phoneBookEntries) {
        
        // if an entry is added, all entries of the other phones for the same number are also added.
        // so add an entry only if its number isn't in the items yet.
        // if an entry has no number, which is a strange situation, it is still added so that the problem is visible.
        
        boolean addEntry = true;
        PhoneNumber phoneNumber = phoneAddressBookEntry.getPhoneNumber();
        if (phoneNumber != null) {
          for (EObject item: items) {
            if (phoneNumber.equals(item.eGet(phoneNumberReference))) {
              addEntry = false;
              break;
            }
          }
        }
        
        if (!addEntry) {
          continue;
        }
        
        EObject eObject = eFactory.create(phoneMemoryEntryClass);
        
        eObject.eSet(entryNames[i], phoneAddressBookEntry.getEntryName());
        
        eObject.eSet(entryTypes[i], phoneAddressBookEntry.getEntryType());
        
        // add entries of other phones for the same number
        for (int j = i + 1; j < phonesWithMemory.size(); j++) {
          List<PhoneAddressBookEntry> otherPhoneBookEntries = phonesWithMemory.get(j).getPhoneAddressBook().getEntries();
          for (PhoneAddressBookEntry otherPhoneAddressBookEntry: otherPhoneBookEntries) {
            PhoneNumber otherPhoneNumber = otherPhoneAddressBookEntry.getPhoneNumber();
            if (otherPhoneNumber != null  &&  otherPhoneNumber.equals(phoneNumber)) {
              eObject.eSet(entryNames[j], otherPhoneAddressBookEntry.getEntryName());
              
              eObject.eSet(entryTypes[j], otherPhoneAddressBookEntry.getEntryType());
            }
          }
        }
        
        if (phoneNumber != null) {
          eObject.eSet(holderName, getNameForPhoneNumberHolders(phoneNumber.getNumberHolders()));
          eObject.eSet(phoneNumberReference, phoneNumber);
          eObject.eSet(description, phoneNumber.getDescription());
          eObject.eSet(connectionType, phoneNumber.getConnectionType());
        }
        
        items.add(eObject);
      }
    }
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
    
  /**
   * Create an EMF data model for the items shown in the table.
   * <p>
   * The data model is a single EClass with the following structural features:
   * <ul>
   * <li>For each phone there is an {@code entryNameX} and an {@code entyTypeX}. Where X is the index of the phone (starting at 0).</li>
   * <li>{@code holderName} name related to the phone number.</li>
   * <li>{@code phoneNumber} the stored phone number.</li>
   * <li>{@code description} the description of the phone number.</li>
   * <li>{@code connectionType} the connection type of the phone number.</li>
   * </ul>
   */
  private void createPhoneMemoriesModel(int nrOfPhones) {
    entryNames = new EAttribute[nrOfPhones];
    entryTypes = new EAttribute[nrOfPhones];
    
    EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
    
    phoneMemoryEntryClass  = EcoreFactory.eINSTANCE.createEClass();
    phoneMemoryEntryClass.setName("PhoneMemoryEntry");
    
    for (int phoneIndex = 0; phoneIndex < nrOfPhones; phoneIndex++) {
      entryNames[phoneIndex] = EcoreFactory.eINSTANCE.createEAttribute();
      entryNames[phoneIndex].setName("entryName" + phoneIndex);
      entryNames[phoneIndex].setEType(EcorePackage.eINSTANCE.getEString());
      phoneMemoryEntryClass.getEStructuralFeatures().add(entryNames[phoneIndex]);
      
      entryTypes[phoneIndex] = EcoreFactory.eINSTANCE.createEAttribute();
      entryTypes[phoneIndex].setName("entryType" + phoneIndex);
      entryTypes[phoneIndex].setEType(ROLODEX_PACKAGE.getPhoneAddressBookEntryType());
      phoneMemoryEntryClass.getEStructuralFeatures().add(entryTypes[phoneIndex]);
    }
    
    holderName = EcoreFactory.eINSTANCE.createEAttribute();
    holderName.setName("holderName");
    holderName.setEType(EcorePackage.eINSTANCE.getEString());
    phoneMemoryEntryClass.getEStructuralFeatures().add(holderName);
    
    phoneNumberReference = EcoreFactory.eINSTANCE.createEReference();
    phoneNumberReference.setName("phoneNumber");
    phoneNumberReference.setEType(ROLODEX_PACKAGE.getPhoneNumber());
    phoneMemoryEntryClass.getEStructuralFeatures().add(phoneNumberReference);
    
    description = EcoreFactory.eINSTANCE.createEAttribute();
    description.setName("description");
    description.setEType(EcorePackage.eINSTANCE.getEString());
    phoneMemoryEntryClass.getEStructuralFeatures().add(description);
    
    connectionType = EcoreFactory.eINSTANCE.createEAttribute();
    connectionType.setName("connectionType");
    connectionType.setEType(ROLODEX_PACKAGE.getConnectionType());
    phoneMemoryEntryClass.getEStructuralFeatures().add(connectionType);
    
    ePackage.getEClassifiers().add(phoneMemoryEntryClass);
    
    eFactory = ePackage.getEFactoryInstance();
    
  }

  /**
   * This class provides the descriptor for the PhoneMemories Table.
   * <p>
   * The table has the following columns:
   * <ul>
   *   <li>For each phone:<br/>
   *   A header column with the name of the phone and two columns:
   *   <ul>
   *     <li>Name - the name of the entry</li>
   *     <li>Type - the memory entry type (where applicable)</li>
   *   </ul>
   *   </li>
   *   <li>Name - the name related to the phone number</li>
   *   <li>Phone number - the phone number</li>
   *   <li>Description - the description related to the phone number</li>
   *   <li>Connection type - the connection type of the phone number</li>
   * </ul>
   */
  class PhoneMemoriesTableDescriptor extends EObjectTableDescriptor<EObject> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(PhoneMemoriesTableDescriptor.class.getName());

    /**
     * Constructor.
     * 
     * @param customization the GUI customization
     * @param phonesWithMemory the list of phones with a memory (address book)
     */
    public PhoneMemoriesTableDescriptor(List<Phone> phonesWithMemory) {
      super("There are no memory entries to show", null, null, null);

      List<EObjectTableColumnDescriptorAbstract<EObject>> columnDescriptors = new ArrayList<>();


      for (int index = 0; index < phonesWithMemory.size(); index++) {
        EObjectTableColumnGroupDescriptor<EObject> phoneMemoryDescriptor = createPhoneGroupDescriptor(phonesWithMemory, index);
        columnDescriptors.add(phoneMemoryDescriptor);
      }

      // Create a descriptor for the 'Name' column.
      EObjectTableColumnDescriptorBasic<EObject> holderNameColumnDescriptor = new EObjectTableColumnDescriptorBasic<>(holderName, "Name", false, true);
      columnDescriptors.add(holderNameColumnDescriptor);

      // Create a descriptor for the 'Phone number' column.
      EObjectTableColumnDescriptorCustom<EObject> phoneNumberColumnDescriptor = new EObjectTableColumnDescriptorCustom<EObject>(phoneNumberReference, "Phone number", null, true, true, column -> {
        TableCell<EObject, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {  
            super.updateItem(item, empty);
            setGraphic(null);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              PhoneNumber phoneNumber = (PhoneNumber) item;
              setText(phoneNumber.getPhoneNumber());
            }
          }
        };

        return cell;
      });
      columnDescriptors.add(phoneNumberColumnDescriptor);

      // Create a descriptor for the 'Description' column.
      EObjectTableColumnDescriptorBasic<EObject> descriptionColumnDescriptor = new EObjectTableColumnDescriptorBasic<>(description, "Description", false, true);
      columnDescriptors.add(descriptionColumnDescriptor);

      // Create a descriptor for the 'Connection type' column.
      EObjectTableColumnDescriptorBasic<EObject> connectionTypeColumnDescriptor = new EObjectTableColumnDescriptorBasic<>(connectionType, "ConnectionType", false, true);
      columnDescriptors.add(connectionTypeColumnDescriptor);

      setColumnDescriptors(columnDescriptors);

    }

    /**
     * Create a column group descriptor for a phone.
     * 
     * @param phones list of all phones
     * @param index the index of the phone for which to create the descriptor.
     * @return the created column group descriptor.
     */
    EObjectTableColumnGroupDescriptor<EObject> createPhoneGroupDescriptor(List<Phone> phones, int index) {
      Phone phone = phones.get(index);

      // Create a descriptor for the 'Name' column.
      EObjectTableColumnDescriptorBasic<EObject> entryNameColumnDescriptor = new EObjectTableColumnDescriptorBasic<>(entryNames[index], "Name", false, true);

      // Create a descriptor for the 'Name' column.
      EObjectTableColumnDescriptorBasic<EObject> entryTypeColumnDescriptor = new EObjectTableColumnDescriptorBasic<>(entryTypes[index], "Type", false, true);

      EObjectTableColumnGroupDescriptor<EObject> phoneMemoryDescriptor = new EObjectTableColumnGroupDescriptor<EObject>(phone.getPhoneType(), entryNameColumnDescriptor, entryTypeColumnDescriptor);

      return phoneMemoryDescriptor;
    }

  }

}

