package goedegep.finan.mortgage.app.guifx;

import java.util.List;

import goedegep.finan.mortgage.model.CompensationPayment;
import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;

public class CompensationPaymentsTable extends EObjectTable<CompensationPayment> {

  public CompensationPaymentsTable(CustomizationFx customization) {
    super(customization, MortgagePackage.eINSTANCE.getCompensationPayment(), new CompensationPaymentsTableDescriptor(), null, null);
  }

}

class CompensationPaymentsTableDescriptor extends EObjectTableDescriptor<CompensationPayment> {
  private static final MortgagePackage MORTGAGE_PACKAGE = MortgagePackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<CompensationPayment>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<CompensationPayment>>of(
      new EObjectTableColumnDescriptorBasic<CompensationPayment>(MORTGAGE_PACKAGE.getCompensationPayment_Date(), "Date", 100, false, true),
      new EObjectTableColumnDescriptorBasic<CompensationPayment>(MORTGAGE_PACKAGE.getCompensationPayment_Amount(), "Amount", 100, false, true),
      new EObjectTableColumnDescriptorBasic<CompensationPayment>(MORTGAGE_PACKAGE.getCompensationPayment_Description(), "Description", 400, false, true)
  );
  
 
  CompensationPaymentsTableDescriptor() {
    super(columnDescriptors, null);
  }
}
