package goedegep.finan.mortgage.app.guifx;

import java.util.List;

import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;
import goedegep.finan.mortgage.model.MortgageYearlyOverviews;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;

public class MortgageYearlyOverviewsTable extends EObjectTable<MortgageYearlyOverview> {

  
  public MortgageYearlyOverviewsTable(CustomizationFx customization, MortgageYearlyOverviews mortgageYearlyOverviews) {
    super(customization, MortgagePackage.eINSTANCE.getMortgageYearlyOverview(), new MortgageYearlyOverviewsTableDescriptor(), mortgageYearlyOverviews, MortgagePackage.eINSTANCE.getMortgageYearlyOverviews_YearlyOverviews());
  }
  
}

class MortgageYearlyOverviewsTableDescriptor extends EObjectTableDescriptor<MortgageYearlyOverview> {
  private static final MortgagePackage MORTGAGE_PACKAGE = MortgagePackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<MortgageYearlyOverview>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<MortgageYearlyOverview>>of(
      new EObjectTableColumnDescriptorBasic<MortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_Year(), "Year", false, true),
      new EObjectTableColumnDescriptorBasic<MortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_InterestPaid(), "Interest paid", false, true),
      new EObjectTableColumnDescriptorBasic<MortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_Repayment(), "Repayment", false, true),
      new EObjectTableColumnDescriptorBasic<MortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_DebtAtBeginningOfYear(), "Debt at the beginning of the year", 220, false, true, null),
      new EObjectTableColumnDescriptorBasic<MortgageYearlyOverview>(MORTGAGE_PACKAGE.getMortgageYearlyOverview_DebtAtEndOfYear(), "Debt at the end of the year", 200, false, true, null)
  );
  
  MortgageYearlyOverviewsTableDescriptor() {
    super(columnDescriptors, null);
  }
  
}
