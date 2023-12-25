package goedegep.rolodex.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EReference;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressForPeriod;
import goedegep.rolodex.model.AddressHolder;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PreviousAddressesPanel extends VBox {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;

  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  
  private EObjectTable<AddressForPeriod> previousAddressesTable;
  private AddressHolder addressHolder;
  
  public PreviousAddressesPanel(CustomizationFx customization, Rolodex rolodex) {
    this.customization = customization;
    this.rolodex = rolodex;

    componentFactory = customization.getComponentFactoryFx();

    previousAddressesTable = new EObjectTable<AddressForPeriod>(customization, ROLODEX_PACKAGE.getAddressForPeriod(), new PreviousAddressesTableDescriptor(), null, (EReference) null);

    createGUI();
  }
  
  private void createGUI() {
    setSpacing(6.0);
    setPadding(new Insets(12.0));
    
    Label label = componentFactory.createStrongLabel("Previous addresses");
    getChildren().add(label);   
    
    getChildren().add(previousAddressesTable);
    
    HBox addAddressHBox = componentFactory.createHBox(12.0);
    label = componentFactory.createLabel("Add a previous address:");
    addAddressHBox.getChildren().add(label);
    AddressTextField addressTextField = new AddressTextField(customization, rolodex);
    addAddressHBox.getChildren().add(addressTextField.getControl());
    Button addAddressButton = componentFactory.createButton("Add address", "Add the selected address as a previous address");
    addAddressButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        if (addressHolder == null) {
          return;
        }
        
        Address matchingAddress = addressTextField.getMatchingAddress();
        if (matchingAddress != null) {
          AddressForPeriod addressForPeriod = ROLODEX_FACTORY.createAddressForPeriod();
          addressForPeriod.setAddress(matchingAddress);
          addressHolder.getPreviousAddresses().add(addressForPeriod);
        }
      }
      
    });
    addAddressHBox.getChildren().add(addAddressButton);
    getChildren().add(addAddressHBox);
    
  }

  public void setAddressHolder(AddressHolder addressHolder) {
    this.addressHolder = addressHolder;
    previousAddressesTable.setObjects(addressHolder,RolodexPackage.eINSTANCE.getAddressHolder_PreviousAddresses());
  }
}


/**
 * This class provides the descriptor for the AddressForPeriods Table.
 */
class PreviousAddressesTableDescriptor extends EObjectTableDescriptor<AddressForPeriod> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  public static final String ID_COLUMN_ID = "id";
  
  private static List<EObjectTableColumnDescriptorAbstract<AddressForPeriod>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<AddressForPeriod>>of(
      new EObjectTableColumnDescriptorBasic<AddressForPeriod>(ROLODEX_PACKAGE.getAddressForPeriod_Address(), "Address", 300, true, true),
      new EObjectTableColumnDescriptorBasic<AddressForPeriod>(ROLODEX_PACKAGE.getAddressForPeriod_FromDate(), "From", true, true),
      new EObjectTableColumnDescriptorBasic<AddressForPeriod>(ROLODEX_PACKAGE.getAddressForPeriod_UntillDate(), "Until", true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<AddressForPeriod>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public PreviousAddressesTableDescriptor() {
    super("The are no previous addresses", null, columnDescriptors, rowOperations);
        
  }

}
