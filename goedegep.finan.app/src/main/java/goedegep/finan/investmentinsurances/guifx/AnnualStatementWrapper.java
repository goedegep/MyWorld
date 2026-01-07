package goedegep.finan.investmentinsurances.guifx;

import java.util.List;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.util.datetime.FlexDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AnnualStatementWrapper {
  private static final FlexDateFormat FDF = new FlexDateFormat();

  private AnnualStatement annualStatement;
  private String displayName;
    
  private AnnualStatementWrapper(AnnualStatement annualStatement, String displayName) {
    this.annualStatement = annualStatement;
    this.displayName = displayName;
  }
  
  
  public static AnnualStatementWrapper getAnnualStatementWrapper(AnnualStatement annualStatement, List<AnnualStatementWrapper> annualStatementWrappers) {
    for (AnnualStatementWrapper annualStatementWrapper: annualStatementWrappers) {
      if (annualStatementWrapper.annualStatement.equals(annualStatement)) {
        return annualStatementWrapper;
      }
    }
    
    return null;
  }
  
  public AnnualStatement getAnnualStatement() {
    return annualStatement;
  }

  @Override
  public String toString() {
    return displayName;
  }

  public static ObservableList<AnnualStatementWrapper> createAnnualStatementWrapperList(List<AnnualStatement> annualStatements) {
    ObservableList<AnnualStatementWrapper> values = FXCollections.observableArrayList();
    
    for (AnnualStatement annualStatement: annualStatements) {
      values.add(new AnnualStatementWrapper(annualStatement, FDF.format(annualStatement.getDate())));
    }
    
    return values;
  }
}
