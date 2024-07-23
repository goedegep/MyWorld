package goedegep.finan.mortgage.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.finan.mortgage.model.FinalPayment;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.PeriodicPayment;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.eobjecttable.SubClassDescriptor;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import javafx.scene.control.TableCell;

public class MortgageEventsTable extends EObjectTable<MortgageEvent> {
  
  public MortgageEventsTable(CustomizationFx customization) {
    super(customization, MortgagePackage.eINSTANCE.getMortgageEvent(), new MortgageEventsTableDescriptor());
  }
  
//  public void setMortgageEvents(List<MortgageEvent> mortgageEvents) {
//    this.setObjects(mortgageEvents);
//  }
}

class MortgageEventsTableDescriptor extends EObjectTableDescriptor<MortgageEvent> {
  private static final PgCurrencyFormat CF = new PgCurrencyFormat();
  private static final MortgagePackage MORTGAGE_PACKAGE = MortgagePackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<MortgageEvent>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<MortgageEvent>>of(
      new EObjectTableColumnDescriptorBasic<MortgageEvent>(MORTGAGE_PACKAGE.getMortgageEvent_Date(), "Date", true, true),
      new EObjectTableColumnDescriptorCustom<MortgageEvent>(null, "Description", 500, true, true, column -> {
        TableCell<MortgageEvent, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              MortgageEvent mortgageEvent = (MortgageEvent) item;
              setText(MortgageEventDescriptions.getMortgageEventDescription(mortgageEvent));
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<MortgageEvent>(null, "New Balance", null, true, true, column -> {
        TableCell<MortgageEvent, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              if (item instanceof PeriodicPayment) {
                PeriodicPayment periodicPayment = (PeriodicPayment) item;
                setText(CF.format(periodicPayment.getBalanceAfterPayment()));
              } else if (item instanceof FinalPayment) {
                setText(CF.format(new PgCurrency(0l)));
             } else {
                setText(null);
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorBasic<MortgageEvent>(MORTGAGE_PACKAGE.getMortgageEvent_NewInterestRate(), "Interest rate", true, true),
      new EObjectTableColumnDescriptorBasic<MortgageEvent>(MORTGAGE_PACKAGE.getMortgageEvent_Comments(), "Remarks", true, true)
  );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<MortgageEvent>> rowOperations = new HashMap<>() {
    {
      put(Operation.NEW_OBJECT_BEFORE, new TableRowOperationDescriptor<>("Insert event before this event"));
      put(Operation.NEW_OBJECT_AFTER, new TableRowOperationDescriptor<>("Insert event after this event"));
      put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete event"));
    }
  };
  
  @SuppressWarnings("serial")
  private static Map<EClass, SubClassDescriptor<? extends EObject>> subClassDescriptors = new HashMap<>() {
    {
      put(MORTGAGE_PACKAGE.getPeriodicPayment(), null);
      put(MORTGAGE_PACKAGE.getNewInterestRate(), null);
      put(MORTGAGE_PACKAGE.getFinalPayment(), null);
    }
  };
  
  
  MortgageEventsTableDescriptor() {
    super(columnDescriptors, null);
    setRowOperations(rowOperations);
    setSubClassDescriptors(subClassDescriptors);
  }
}
