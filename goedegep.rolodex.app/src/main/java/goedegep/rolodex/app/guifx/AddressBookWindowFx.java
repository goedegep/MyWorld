package goedegep.rolodex.app.guifx;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
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
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.PhoneNumberHolder;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * This class provides an address book window.
 */
public class AddressBookWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(AddressBookWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Address book";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private Rolodex rolodex;
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<AddressHolder> addressHoldersTable;
  private TableFilterBooleanPredicate<AddressHolder> showArchivedItemsPredicate = null;
  private CheckMenuItem  showArchivedItems;
  
  private EList<AddressHolder> addressesHolders;
  
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
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        addressHoldersTable.setFilterExpression(newValue, null);
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
   * <li>The addresses table</li>
   * <li>A panel to add a new address</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    rootLayout.getChildren().add(createMenuBar());
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createAddressHoldersTable());
        
    setScene(new Scene(rootLayout, 700, 700));
  }

  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    MenuItem menuItem;
    Menu menu;
    
    // Print menu
    menu = componentFactory.createMenu("Print");
    
    // Print: Print address list
    menuItem = new MenuItem("Print address list");
    menuItem.setOnAction(e -> printAddressList());
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    

    // Show menu
    menu = componentFactory.createMenu("Show");
    
    // Show: Show archived items
    showArchivedItems = new CheckMenuItem("Show archived items");
    showArchivedItems.setOnAction(new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        updateShowArchivedItems();
      }
    });
    menu.getItems().add(showArchivedItems);
    
    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private Object printAddressList() {
    addressesHolders = new BasicEList<>();
    addressesHolders.addAll(rolodex.getFamilyList().getFamilies());
    addressesHolders.addAll(rolodex.getPersonList().getPersons());
    addressesHolders.addAll(rolodex.getInstitutionList().getInstitutions());
    
    for (AddressHolder addressHolder: addressesHolders) {
      if (addressHolder.getAddress() == null) {
        continue;
      }
      
      if (addressHolder instanceof Person person) {
        if (person.isArchived()) {
          continue;
        }
        System.out.print(person.getName());
      } else if (addressHolder instanceof Family family) {
        if (family.isArchived()) {
          continue;
        }
        System.out.print(family.getFamilyTitle() + " " + family.getFamilyName());
      } else if (addressHolder instanceof Institution institution) {
        System.out.print(institution.getName());
      }
      
      printAddress(addressHolder.getAddress());
      
      if (addressHolder instanceof PhoneNumberHolder phoneNumberHolder) {
        List<PhoneNumber> phoneNumbers = phoneNumberHolder.getPhoneNumbers();
        if (phoneNumbers != null  &&  !phoneNumbers.isEmpty()) {
          printPhoneNumber(phoneNumbers.get(0));
        }
      }
      
      System.out.println();
    }
    return null;
  }
  
  private void printAddress(Address address) {
    if (address == null) {
      System.out.println();
      return;
    }
    
    System.out.print(", " + address.getStreetName() + " " + address.getHouseNumber());
    if (address.getHouseNumberExtension() != null) {
      System.out.print(address.getHouseNumberExtension());
    }
    System.out.print(", " + address.getPostalCode() + "  " + address.getCity().getCityName());
    
  }
  
  private void printPhoneNumber(PhoneNumber phoneNumber) {
    System.out.print(", tel.: " + phoneNumber.getPhoneNumber());
  }

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
   * Create the addressesTable.
   * 
   * @return the created citiesTable
   */
  private EObjectTable<AddressHolder> createAddressHoldersTable() {
    addressesHolders = new BasicEList<>();
    addressesHolders.addAll(rolodex.getFamilyList().getFamilies());
    addressesHolders.addAll(rolodex.getPersonList().getPersons());
    addressesHolders.addAll(rolodex.getInstitutionList().getInstitutions());
    addressHoldersTable = new EObjectTable<AddressHolder>(customization, ROLODEX_PACKAGE.getAddressHolder(), new AddressesBookTableDescriptor(customization), null, addressesHolders);
        
    return addressHoldersTable;
  }
    
}


/**
 * This class provides the descriptor for the AddressBook Table.
 */
class AddressesBookTableDescriptor extends EObjectTableDescriptor<AddressHolder> {
  private static final Logger LOGGER = Logger.getLogger(AddressesBookTableDescriptor.class.getName());
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static Image personImage;
  private static Image familyImage;
  private static Image institutionImage;
  
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
  
  public AddressesBookTableDescriptor(CustomizationFx customization) {
    super("The are no addressess in the Rolodex", null, columnDescriptors, null);
    
    AdminResourcesFx rolodexResources = (AdminResourcesFx) customization.getResources();
    personImage = rolodexResources.getPersonImage();
    familyImage = rolodexResources.getFamilyImage();
    institutionImage = rolodexResources.getInstitutionImage();
  }

}
