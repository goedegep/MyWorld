package goedegep.rolodex.app.guifx;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectListContainerSpecification;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCheckBox;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.eobjecttable.TableFilterBooleanPredicate;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressHolder;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.emf.EmfUtil;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * This class provides an address book window.
 * <p>
 * The window consists of a table listing all families, persons and institutions (being all {@link AddressHolder}s) with their addresses.<br/>
 * You can filter on any text occurring in the table.
 */
public class AddressBookWindowFx extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(AddressBookWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Address book";
  
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
   * Factory for creating all GUI components
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * Control panel for the {@code addressHoldersTable}.
   */
  private EObjectTableControlPanel eObjectTableControlPanel;
  
  /**
   * The {@link EObjectTable} showing the {@code AddressHolder}s.
   */
  private EObjectTable<AddressHolder> addressHoldersTable;
  
  /**
   * A {@code TableFilterBooleanPredicate} for letting the {@code addressHoldersTable} show archived items or not.
   */
  private TableFilterBooleanPredicate<AddressHolder> showArchivedItemsPredicate = null;
  
  /**
   * A {@code CheckMenuItem} for letting the user select whether archived items shouls be shown or not.
   */
  private CheckMenuItem  showArchivedItems;
  
  /**
   * The {@code List} with all {@code AddressHolder}s.
   */
  private List<AddressHolder> addressesHolders;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public AddressBookWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    componentFactory = customization.getComponentFactoryFx();
    
    createControls();
    
    createGUI();
    
    show();
  }
  
  /**
   * Create the controls.
   */
  private void createControls() {
    createAddressHoldersTable();
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    eObjectTableControlPanel.filterTextProperty().addListener((observable, oldValue, newValue) -> addressHoldersTable.setFilterExpression(newValue, null));    
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The addresses table</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    rootLayout.getChildren().add(createMenuBar());
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(addressHoldersTable);
        
    setScene(new Scene(rootLayout, 700, 700));
  }

  /**
   * Create the menu bar.
   * 
   * @return the created menu bar.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;
    
    // Show menu
    menu = componentFactory.createMenu("Show");
    
    // Show: Show archived items
    showArchivedItems = new CheckMenuItem("Show archived items");
    showArchivedItems.setOnAction((event) -> updateShowArchivedItems());
    menu.getItems().add(showArchivedItems);
    
    menuBar.getMenus().add(menu);

    return menuBar;
  }

  /**
   * Update filtering on 'archived items' based on a new menu selection.
   */
  private void updateShowArchivedItems() {
    LOGGER.info("Show archived items: " + showArchivedItems.isSelected());
    if (showArchivedItems.isSelected()) {
      if (showArchivedItemsPredicate != null) {
        addressHoldersTable.removePredicate(showArchivedItemsPredicate);
      }
    } else {
      showArchivedItemsPredicate = new TableFilterBooleanPredicate<>(false, ROLODEX_PACKAGE.getArchive_Archived());
      addressHoldersTable.addPredicate(showArchivedItemsPredicate);
    }
  }
  
  /**
   * Create the {@code addressHoldersTable} and fill it with all {@code AddressHolder}s.
   */
  private void createAddressHoldersTable() {
    addressHoldersTable = new EObjectTable<AddressHolder>(customization, ROLODEX_PACKAGE.getAddressHolder(), new AddressesBookTableDescriptor(customization));
    
    EObjectListContainerSpecification listSpecification = addressHoldersTable.createObjectListContainer();
    addressesHolders =  EmfUtil.<AddressHolder>getListUnchecked(listSpecification.listContainer(), listSpecification.listReference());
    
    // As we work with copies of the lists, we have to update the addressesHolders list if items are added or deleted to these lists.
    // In the GUI items are only added or removed one by one, so here's no implementation needed for ADD_MANY and REMOVE_MANY.
    Adapter adapter = new EContentAdapter() {
      public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        
        switch (notification.getEventType()) {
        case Notification.ADD:
          // for ADD isTouch() is always false
          LOGGER.severe("Added item");
          // if the notification is from one of the lists add the new item to the addressesHolders list.
          AddressHolder newAddressHolder = null;
          int addIndex = -1;
          if (notification.getFeature().equals(ROLODEX_PACKAGE.getFamilyList_Families())) {
            newAddressHolder = rolodex.getFamilyList().getFamilies().get(notification.getPosition());
            addIndex = notification.getPosition();
          } else if (notification.getFeature().equals(ROLODEX_PACKAGE.getPersonList_Persons())) {
            newAddressHolder = rolodex.getPersonList().getPersons().get(notification.getPosition());
            addIndex = rolodex.getFamilyList().getFamilies().size() + notification.getPosition();
          } else if (notification.getFeature().equals(ROLODEX_PACKAGE.getInstitutionList_Institutions())) {
            newAddressHolder = rolodex.getInstitutionList().getInstitutions().get(notification.getPosition());
            addIndex = rolodex.getFamilyList().getFamilies().size() + rolodex.getPersonList().getPersons().size() + notification.getPosition();
          }
          if (newAddressHolder != null) {
            addressesHolders.add(addIndex, newAddressHolder);
          }
          break;
          
        case Notification.ADD_MANY:
          // No implementation needed
          break;
        
        case Notification.SET:
        case Notification.UNSET:
          // No implementation needed as this is already seen by the table.
          break;
          
        case Notification.REMOVE:
          // for REMOVE isTouch() is always false
          LOGGER.severe("Removed item");
          // if the notification is from one of the lists remove the item from the addressesHolders list.
          addressesHolders.remove(notification.getOldValue());
          break;
          
        case Notification.REMOVING_ADAPTER:
          // This is no change in the data, so no action.
          break;
          
        case Notification.REMOVE_MANY:
          // No implementation needed
          break;
        
        default:
          throw new RuntimeException ("Event type " + notification.getEventType() + " not supported");
        }
      }

    };
    
    addressesHolders.addAll(rolodex.getFamilyList().getFamilies());
    addressesHolders.addAll(rolodex.getPersonList().getPersons());
    addressesHolders.addAll(rolodex.getInstitutionList().getInstitutions());
    addressHoldersTable.setObjects(listSpecification);
    
    rolodex.eAdapters().add(adapter);
  }
    
}


/**
 * This class provides the descriptor for the AddressBook Table.
 */
class AddressesBookTableDescriptor extends EObjectTableDescriptor<AddressHolder> {
  private static final Logger LOGGER = Logger.getLogger(AddressesBookTableDescriptor.class.getName());
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  /**
   * The {@code Image} for a person.
   */
  private static Image personImage;
  
  /**
   * The {@code Image} for a family.
   */
  private static Image familyImage;
  
  /**
   * The {@code Image} for an institution.
   */
  private static Image institutionImage;
  
  /**
   * The columns descriptors.
   */
  private static List<EObjectTableColumnDescriptorAbstract<AddressHolder>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<AddressHolder>>of(
      new EObjectTableColumnDescriptorCustom<AddressHolder>(null, "Type", null, true, true, column -> {
        TableCell<AddressHolder, Object> cell = new TableCell<>() {

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
              AddressHolder addressHolder = (AddressHolder) item;
              if (addressHolder instanceof Institution) {
                setGraphic(new ImageView(institutionImage));
             } else if (addressHolder instanceof Family) {
                setGraphic(new ImageView(familyImage));
              } else if (addressHolder instanceof Person) {
                setGraphic(new ImageView(personImage));
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<AddressHolder>(null, "Name", null, true, true, column -> {
        TableCell<AddressHolder, Object> cell = new TableCell<>() {

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
              AddressHolder addressHolder = (AddressHolder) item;
              if (addressHolder instanceof Institution) {
                Institution institution = (Institution) addressHolder;
                setText(institution.getName());
             } else if (addressHolder instanceof Family) {
               Family family = (Family) addressHolder;
               setText(family.getFamilyTitle() + " " + family.getFamilyName());
              } else if (addressHolder instanceof Person) {
                Person person = (Person) addressHolder;
                setText(person.getName());
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<AddressHolder>(null, "Street/PO Box", null, true, true, column -> {
        TableCell<AddressHolder, Object> cell = new TableCell<>() {

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
              AddressHolder addressHolder = (AddressHolder) item;
              
              Address address = addressHolder.getAddress();
              if (address == null) {
                if (addressHolder instanceof Person) {
                  Person person = (Person) addressHolder;
                  address = person.retrieveAddress();
                  LOGGER.info("Address taken from family for " + person.getName());
                }
              }

              if (address != null) {
                StringBuffer sb;
                boolean spaceNeeded;

                sb = new StringBuffer();
                spaceNeeded = false;
                if ((address.getCity() != null)  &&
                    (address.getCity().getCountry() != null)  &&
                    address.getCity().getCountry().getCountryName().equals("Frankrijk")) {
                  Integer houseNumber = address.getHouseNumber();
                  if (houseNumber != null) {
                    sb.append(houseNumber.toString());
                    spaceNeeded = true;
                  }
                  String houseNumberExtension = address.getHouseNumberExtension();
                  if (houseNumberExtension != null) {
                    if (spaceNeeded) {
                      sb.append(" ");
                    }
                    sb.append(houseNumberExtension);
                  }
                  String streetName = address.getStreetName();
                  if (streetName != null) {
                    if (spaceNeeded) {
                      sb.append(" ");
                    }
                    sb.append(streetName);
                  }
                } else {
                  String streetName = address.getStreetName();
                  if (streetName != null) {
                    sb.append(streetName);
                    spaceNeeded = true;
                  }
                  Integer houseNumber = address.getHouseNumber();
                  if (houseNumber != null) {
                    if (spaceNeeded) {
                      sb.append(" ");
                    }
                    sb.append(houseNumber.toString());
                  }
                  String houseNumberExtension = address.getHouseNumberExtension();
                  if (houseNumberExtension != null) {
                    if (spaceNeeded) {
                      sb.append(" ");
                    }
                    sb.append(houseNumberExtension);
                  }
                }

                if (address.getPOBox() != null) {
                  if (spaceNeeded) {
                    sb.append(" / ");
                  }
                  sb.append("PO Box " + address.getPOBox());
                }
                setText(sb.toString());
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<AddressHolder>(null, "Postal code", null, true, true, column -> {
        TableCell<AddressHolder, Object> cell = new TableCell<>() {

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
              AddressHolder addressHolder = (AddressHolder) item;
              Address address = addressHolder.getAddress();
              if (address == null) {
                if (addressHolder instanceof Person) {
                  Person person = (Person) addressHolder;
                  address = person.retrieveAddress();
                  LOGGER.info("Address taken from family for " + person.getName());
                }
              }
              
              if (address != null) {
                setText(address.getPostalCode());
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<AddressHolder>(null, "City", null, true, true, column -> {
        TableCell<AddressHolder, Object> cell = new TableCell<>() {

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
              AddressHolder addressHolder = (AddressHolder) item;
              Address address = addressHolder.getAddress();
              if (address == null) {
                if (addressHolder instanceof Person) {
                  Person person = (Person) addressHolder;
                  address = person.retrieveAddress();
                  LOGGER.info("Address taken from family for " + person.getName());
                }
              }
              if (address != null) {
                City city = address.getCity();
                if (city != null) {
                  setText(city.getCityName());
                }
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<AddressHolder>(null, "Country", null, true, true, column -> {
        TableCell<AddressHolder, Object> cell = new TableCell<>() {

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
              AddressHolder addressHolder = (AddressHolder) item;
              Address address = addressHolder.getAddress();
              if (address == null) {
                if (addressHolder instanceof Person) {
                  Person person = (Person) addressHolder;
                  address = person.retrieveAddress();
                  LOGGER.info("Address taken from family for " + person.getName());
                }
              }
              if (address != null) {
                City city = address.getCity();
                if (city != null) {
                  Country country = city.getCountry();
                  if (country != null) {
                    setText(country.getCountryName());
                  }
                }
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCheckBox<AddressHolder>(ROLODEX_PACKAGE.getArchive_Archived(), "Archived", true, true)
      );
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  public AddressesBookTableDescriptor(CustomizationFx customization) {
    super("The are no addressess in the Rolodex", null, columnDescriptors, null);
    
    AdminResourcesFx rolodexResources = (AdminResourcesFx) customization.getResources();
    personImage = rolodexResources.getPersonImage();
    familyImage = rolodexResources.getFamilyImage();
    institutionImage = rolodexResources.getInstitutionImage();
  }

}
