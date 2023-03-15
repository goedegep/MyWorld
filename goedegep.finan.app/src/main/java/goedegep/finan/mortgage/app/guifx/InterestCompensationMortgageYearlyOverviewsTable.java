package goedegep.finan.mortgage.app.guifx;

import java.util.List;

import goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview;
import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;

public class InterestCompensationMortgageYearlyOverviewsTable extends EObjectTable<InterestCompensationMortgageYearlyOverview> {
  
  public InterestCompensationMortgageYearlyOverviewsTable(CustomizationFx customization, List<InterestCompensationMortgageYearlyOverview> mortgageYearlyOverviews) {
    super(customization, MortgagePackage.eINSTANCE.getInterestCompensationMortgageYearlyOverview(), new InterestCompensationMortgageYearlyOverviewsTableDescriptor(), mortgageYearlyOverviews);
  }

}

class InterestCompensationMortgageYearlyOverviewsTableDescriptor extends EObjectTableDescriptor<InterestCompensationMortgageYearlyOverview> {
  private static final MortgagePackage MORTGAGE_PACKAGE = MortgagePackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<InterestCompensationMortgageYearlyOverview>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<InterestCompensationMortgageYearlyOverview>>of(
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_Year(), "Year", false, true),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_InterestPaid(), "Interest paid", false, true),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_Repayment(), "Repayment", false, true),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_DebtAtBeginningOfYear(), "Debt at the beginning of the year", 220, false, true, null),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_DebtAtEndOfYear(), "Debt at the end of the year", 200, false, true, null),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getInterestCompensationMortgageYearlyOverview_CompensationBorrower(), "Compensation rights", 200, false, true, null),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getInterestCompensationMortgageYearlyOverview_CompensationPayment(), "Compensation paid", 200, false, true, null),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getInterestCompensationMortgageYearlyOverview__GetCompensationToBePaid(), "Compensation to be paid", 220, false, true, null),
      new EObjectTableColumnDescriptorBasic<InterestCompensationMortgageYearlyOverview>(MORTGAGE_PACKAGE.getInterestCompensationMortgageYearlyOverview_DecemberPayment(), "December payment", 150, false, true, null)
  );
  
  InterestCompensationMortgageYearlyOverviewsTableDescriptor() {
    super(columnDescriptors, null);
  }
}
