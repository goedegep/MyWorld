package goedegep.finan.investmentinsurances.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.finan.investmentinsurance.model.InvestmentInsurance;

public class InvestmentInsuranceWrapper {  
  private InvestmentInsurance investmentInsurance;
  private String displayName;
    
  private InvestmentInsuranceWrapper(InvestmentInsurance investmentInsurance, String displayName) {
    this.investmentInsurance = investmentInsurance;
    this.displayName = displayName;
  }
  
  
  public static InvestmentInsuranceWrapper getInsuranceCompanyWrapper(InvestmentInsurance investmentInsurance, List<InvestmentInsuranceWrapper> investmentInsuranceWrappers) {
    for (InvestmentInsuranceWrapper investmentInsuranceWrapper: investmentInsuranceWrappers) {
      if (investmentInsuranceWrapper.investmentInsurance.equals(investmentInsurance)) {
        return investmentInsuranceWrapper;
      }
    }
    
    return null;
  }
  
  public InvestmentInsurance getInvestmentInsurance() {
    return investmentInsurance;
  }

  @Override
  public String toString() {
    return displayName;
  }

  public static List<InvestmentInsuranceWrapper> createInvestmentInsuranceWrapperList(List<InvestmentInsurance> investmentInsurances) {
    List<InvestmentInsuranceWrapper> values = new ArrayList<>();
    
    for (InvestmentInsurance investmentInsurance: investmentInsurances) {
      values.add(new InvestmentInsuranceWrapper(investmentInsurance, investmentInsurance.getInsuranceCompany().getName() + " - " + investmentInsurance.getPolicyNumber()));
    }
    
    return values;
  }
}
