package goedegep.rolodex.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.Operation;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.eobjecttable.TableRowOperationDescriptor;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.rolodex.logic.CountriesComparator;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class provides a window with information on Countries.
 * <p>
 * The top part of the window shows an editable Countries table.<br/>
 * The bottom part shows all details of a Country. The information here can be edited to update the Country information, or to add a new Country.
 */
public class CountriesWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(CountriesWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Countries";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Country> countriesTable;
  private ReferredByPanel referredByPanel;
  private CountryEditPanel countryEditPanel;
  
//  private EList<Country> countries;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public CountriesWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.rolodex = rolodex;
    
    referredByPanel = new ReferredByPanel(customization, "Cities in this country");
    countryEditPanel = new CountryEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        countriesTable.setFilterExpression(newValue, null);
      }
      
    });
    
    countriesTable.addObjectSelectionListener((source, country) -> {
        referredByPanel.setObject(country);
        countryEditPanel.setCountry(country);
    });
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The countries table</li>
   * <li>A panel to add a new country</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createCountriesTable());
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(countryEditPanel.getEditPanel());
    
    setScene(new Scene(rootLayout));
  }
  
  /**
   * Create the countriesTable.
   * 
   * @return the created countriesTable
   */
  private EObjectTable<Country> createCountriesTable() {
    
    countriesTable = new EObjectTable<Country>(customization, ROLODEX_PACKAGE.getCountry(), new CountriesTableDescriptor(), rolodex.getCountryList(), RolodexPackage.eINSTANCE.getCountryList_Countries());
        
    return countriesTable;
  }
    
}


/**
 * This class provides a panel with controls to edit the properties of a Country.<br/>
 * There are buttons for updating the values of a Country, or to add a new Country.
 */
class CountryEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private ComponentFactoryFx componentFactory;
  private Rolodex rolodex;
  private GridPane gridPane;
  private ObjectControlString countryTextField;
  private SimpleObjectProperty<Country> countryProperty = new SimpleObjectProperty<>();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public CountryEditPanel(CustomizationFx customization, Rolodex rolodex) {
    componentFactory = customization.getComponentFactoryFx();
    this.rolodex = rolodex;
    
    // Create the controls.
    countryTextField = componentFactory.createObjectControlString(null, 300, false, "Enter the name of a country to be added to the Rolodex");
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit a country");
    gridPane.add(label, 0, row);
    
    row++;
    
    label = componentFactory.createLabel("Country name:");
    gridPane.add(label, 0, row);
    
    gridPane.add(countryTextField.getControl(), 1, row);
    
    Button addButton = componentFactory.createButton("Add", "Add the country to the Rolodex");
    addButton.setOnAction((e) -> {
      Country newCountry = createCountryFromFields();
      if (newCountry != null) {
        rolodex.getCountryList().getCountries().add(newCountry);
      }
    });
    gridPane.add(addButton, 2, row);
    
    Button updateButton = componentFactory.createButton("Update", "Update the country in the Rolodex");
    countryProperty.addListener(new ChangeListener<Country>() {

      @Override
      public void changed(ObservableValue<? extends Country> observable, Country oldValue, Country newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(countryProperty.getValue() == null);
    
    updateButton.setOnAction((e) -> {
      updateCountryIfControlsAreValid();
    });
    gridPane.add(updateButton, 3, row);
    
  }
  
  /**
   * Get the edit panel.
   * 
   * @return the edit panel.
   */
  public Node getEditPanel() {
    return gridPane;
  }
  
  /**
   * Set the country for which information will be shown and which can be updated.
   * 
   * @param country
   */
  public void setCountry(Country country) {
    countryProperty.setValue(country);
    fillFieldsFromCountry(country);
  }
  
  /**
   * Update the current country, if the controls all have valid values.
   */
  private void updateCountryIfControlsAreValid() {
    // Only update if all controls have valid values.
    if (!countryTextField.isValid()) {
      return;
    }
    
    updateCountryFromFields(countryProperty.get());
  }
  
  /**
   * Fill the countrols with the values of a specific country.
   * 
   * @param country the Country who's values will be applied to the controls.
   */
  private void fillFieldsFromCountry(Country country) {
    countryTextField.setObject(country != null ? country.getCountryName() : null);
  }
  
  /**
   * Create a Country from the values of the controls.
   * <p>
   * The Country will only be created if the controls all have valid values.
   * 
   * @return the newly created Country, or null, if any control has an invalid value.
   */
  private Country createCountryFromFields() {
    // Only update if all controls have valid values.
    if (!countryTextField.isValid()) {
      return null;
    }
    
    Country newCountry = ROLODEX_FACTORY.createCountry();
    
    updateCountryFromFields(newCountry);
    
    return newCountry;
  }
  
  /**
   * Update a Country with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param country The Country object to be updated.
   */
  public boolean updateCountryFromFields(Country country) {
    String countryName = countryTextField.getValue();
    if (!Objects.equals(country.getCountryName(), countryName)) {
      country.setCountryName(countryName);
    }
    
    return true;
  }  
}


/**
 * This class provides the descriptor for the Countries Table.
 */
class CountriesTableDescriptor extends EObjectTableDescriptor<Country> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<Country>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Country>>of(
      new EObjectTableColumnDescriptorBasic<Country>(ROLODEX_PACKAGE.getCountry_CountryName(), "Country", 300, true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Country>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };

  public CountriesTableDescriptor() {
    super("The are no countries in the Rolodex", CountriesComparator.getInstance(), columnDescriptors, rowOperations);
  }
}
