package goedegep.finan.investmentinsurances;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

/**
 * This class provides methods that perform calculations on an Investment Insurance.
 */
public class InvestmentInsurancesCalculator {
  private static final Logger LOGGER = Logger.getLogger(InvestmentInsurancesCalculator.class.getName());
  

  
  private static final PgCurrencyFormat CF = new PgCurrencyFormat(11);
  private static final DateTimeFormatter  DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final FixedPointValueFormat  FPVF = new FixedPointValueFormat();
  
  private static final FixedPointValue DEFAULT_TOTAL_EXPENSE_RATIO = new FixedPointValue(80, 100);
  private static final FixedPointValue DEFAULT_EXPECTED_YEARLY_COSTS_INCREASE = new FixedPointValue(250, 100);
  private static final double          DEFAULT_PERCENTAGE_COSTS_FACTOR = 0.005d;
  
  /**
   * Private constructor, as this a utility class.
   */
  private InvestmentInsurancesCalculator() {
  }
  
  /**
   * Check whether the minimal information is available for an example capital.
   * This is the case if there is an example return on investment for at least one participation.
   * @return true if the minimal information is available for an example capital, false otherwise.
   */
  public static boolean isMinimalInformationExampleCapitalAvailable(AnnualStatement annualStatement, ExampleCapitalType exampleCapitalType) {
    boolean informationAvailable = false;
    
    for (Participation participatie: annualStatement.getParticipations()) {
      switch (exampleCapitalType) {
      case NETTO_HISTORISCH:
        if (participatie.isSetExampleReturnOnInvestmentNetHistoric()  ||
            participatie.isSetExampleCapitalNetHistoric()) {
          informationAvailable = true;
        }
        break;

      case NETTO_HISTORISCH_NA_AFSLAG:
        if ((participatie.isSetExampleReturnOnInvestmentNetHistoric()  &&
             participatie.isSetReturnOnInvestmentReductionNetHistoric())  ||
            participatie.isSetExampleCapitalAfterReduction()) {
          informationAvailable = true;
        }
        break;

      case STANDAARD:
        if (participatie.isSetStandardFundReturnOnInvestment()  ||
            participatie.isSetExampleCapitalStandardFundReturnOnInvestment()) {
          informationAvailable = true;
        }
        break;

      case VIER_PROCENT_BRUTO:
        if (participatie.isSetExampleReturnOnInvestmentGross()  ||
            participatie.isSetExampleCapitalGross()) {
          informationAvailable = true;
        }
        break;

      case BRUTO_EIGEN:
        if (participatie.isSetExampleReturnOnInvestmentGrossCompanyOwn()  ||
            participatie.isSetExampleCapitalGrossCompanyOwn()) {
          informationAvailable = true;
        }
        break;

      case PESSIMISTISCH:
        if (participatie.isSetExampleReturnOnInvestmentPessimistic()  ||
            participatie.isSetExampleCapitalPessimistic()) {
          informationAvailable = true;
        }
        break;        
      }
    }
    
    return informationAvailable;
  }
  
  public static ExampleCapitalCalculationResult calculateExampleCapital(LocalDate startDate, LocalDate endDate, List<ExampleCapitalCalculationInput> exampleCapitalCalculationInputs,
      PgCurrency fixedCosts, PgCurrency percentageCosts, FixedPointValue expectedYearlyCostsIncrease) {
    LOGGER.fine("=> startDate = " + (startDate != null ? startDate.format(DF) : "null") +
      ", endDate=" + (endDate != null ? endDate.format(DF) : "null") +
      ", fixedCosts=" +  CF.format(fixedCosts) + ", expectedYearlyCostsIncrease=" + FPVF.format(expectedYearlyCostsIncrease));
    ExampleCapitalCalculationResult exampleCapitalCalculationResult = new ExampleCapitalCalculationResult();
    int numberOfExampleCapitals = exampleCapitalCalculationInputs.size();
    
    // Fill the calculated capitals with the starting capitals.
    PgCurrency[] calculatedCapitals = new PgCurrency[numberOfExampleCapitals];
    double[] returnOnInvestmentFactors = new double[numberOfExampleCapitals];
    FixedPointValue[] totalExpenseRatioFactors = new FixedPointValue[numberOfExampleCapitals];
    // The total of the values of all participations is needed to split the costs over the partitions.
    PgCurrency totalCapital = new PgCurrency(0);

    // Check whether required information is available,  and calculate factors for the loop over complete years.
    for (int i = 0; i < numberOfExampleCapitals; i++) {
      ExampleCapitalCalculationInput exampleCapitalCalculationInput = exampleCapitalCalculationInputs.get(i);
      PgCurrency startingValue = exampleCapitalCalculationInput.startingValue;
      FixedPointValue returnOnInvestment = exampleCapitalCalculationInput.returnOnInvestment;
      FixedPointValue totalExpenseRatio = exampleCapitalCalculationInput.totalExpenseRatio;
      LOGGER.fine("startingValue=" + CF.format(startingValue) +
          ", returnOnInvestment=" + FPVF.format(returnOnInvestment) + 
          ", TER=" + totalExpenseRatio);
      if (startingValue == null) {
        LOGGER.fine("no starting value available");
        return exampleCapitalCalculationResult;
      }
      if (returnOnInvestment == null) {
        LOGGER.fine("no return on investment available");
        return exampleCapitalCalculationResult;
      }
      returnOnInvestmentFactors[i] = 1.0 + returnOnInvestment.doubleValue() / 100;
      LOGGER.fine("returnOnInvestmentFactor = " + returnOnInvestmentFactors[i]);
      
      if (totalExpenseRatio == null) {
        LOGGER.fine("no total expense ratios available");
        totalExpenseRatio = DEFAULT_TOTAL_EXPENSE_RATIO;
        exampleCapitalCalculationResult.defaultTotalExpenseRatioUsed = true;
      }
      totalExpenseRatioFactors[i] = (new FixedPointValue(10000, 100)).subtract(totalExpenseRatio);
      LOGGER.fine("totalExpenseRatioFactor=" + FPVF.format(totalExpenseRatioFactors[i]));
      
      calculatedCapitals[i] = startingValue.certifyFactor(100);
      totalCapital = totalCapital.add(calculatedCapitals[i]);
    }
    
    double percentageCostsFactor;
    if (percentageCosts != null) {
      percentageCostsFactor = percentageCosts.getDoubleValue() / totalCapital.getDoubleValue();
    } else {
      percentageCostsFactor = DEFAULT_PERCENTAGE_COSTS_FACTOR;
      exampleCapitalCalculationResult.defaultPercentageCostsUsed = true;
    }
    LOGGER.fine("percentageCostsFactor=" + percentageCostsFactor);
    
    if (expectedYearlyCostsIncrease == null) {
      LOGGER.severe("no expectedYearlyCostsIncrease specified.");
      expectedYearlyCostsIncrease = DEFAULT_EXPECTED_YEARLY_COSTS_INCREASE;
      exampleCapitalCalculationResult.defaultExpectedAnnualCostsIncreaseUsed = true;
    }
    double costsFactor = 1.0 + expectedYearlyCostsIncrease.doubleValue() / 100;
    LOGGER.fine("costsFactor = " + costsFactor);
    
    int fullYears = DateUtil.fullYearsInPeriod(startDate, endDate);
    LOGGER.fine("Number of full years = " + fullYears);
    int year = startDate.getYear();
    
    for (int i = 1; i <= fullYears; i++) {
      year++;
      totalCapital = new PgCurrency(0);
      fixedCosts = fixedCosts.multiply(costsFactor);
      for (int capitalIndex = 0; capitalIndex < numberOfExampleCapitals; capitalIndex++) {
        calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].multiply(totalExpenseRatioFactors[capitalIndex].doubleValue()/100);
        calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].multiply(returnOnInvestmentFactors[capitalIndex]);
        LOGGER.fine("loop, year = " + year + ", calculatedCapital = " + CF.format(calculatedCapitals[capitalIndex]));
        totalCapital = totalCapital.add(calculatedCapitals[capitalIndex]);
      }
      
      LOGGER.fine("loop, year = " + year + ", yearly fixed costs = " + CF.format(fixedCosts));
      for (int capitalIndex = 0; capitalIndex < numberOfExampleCapitals; capitalIndex++) {
        PgCurrency percentageCostsThisCapital = null;
        double costsApplicationFactor = calculatedCapitals[capitalIndex].getDoubleValue() / totalCapital.getDoubleValue();
        PgCurrency fixedCostsForThisCapital = fixedCosts.multiply(costsApplicationFactor);
        calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].subtract(fixedCostsForThisCapital);
        percentageCostsThisCapital = calculatedCapitals[capitalIndex].multiply(percentageCostsFactor);
        LOGGER.fine("loop, year = " + year + ", yearly percentage costs = " + CF.format(percentageCostsThisCapital));
        calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].subtract(percentageCostsThisCapital);
      }
    }
    
    double yearFraction = DateUtil.getYearFraction(startDate, endDate);
    LOGGER.fine("year fraction = " + yearFraction);
    totalCapital = new PgCurrency(0);
    for (int capitalIndex = 0; capitalIndex < numberOfExampleCapitals; capitalIndex++) {
      ExampleCapitalCalculationInput exampleCapitalCalculationInput = exampleCapitalCalculationInputs.get(capitalIndex);
      FixedPointValue totalExpenseRatio = exampleCapitalCalculationInput.totalExpenseRatio;
      if (totalExpenseRatio == null) {
        totalExpenseRatio = DEFAULT_TOTAL_EXPENSE_RATIO;
        exampleCapitalCalculationResult.defaultTotalExpenseRatioUsed = true;
      }
      FixedPointValue totalExpenseRatioFactor = (new FixedPointValue(10000, 100)).subtract(totalExpenseRatio.multiply(yearFraction));
      calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].multiply(totalExpenseRatioFactor.doubleValue() / 100);
      FixedPointValue returnOnInvestment = exampleCapitalCalculationInput.returnOnInvestment;
      calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].multiply(1.0 + returnOnInvestment.doubleValue() * yearFraction / 100);
      totalCapital = totalCapital.add(calculatedCapitals[capitalIndex]);
      LOGGER.fine("end value before costs = " + CF.format(calculatedCapitals[capitalIndex]));
    }
    fixedCosts = fixedCosts.multiply(1.0 + expectedYearlyCostsIncrease.doubleValue() * yearFraction / 100).multiply(yearFraction);
    LOGGER.fine("yearly fixed costs = " + CF.format(fixedCosts));
    for (int capitalIndex = 0; capitalIndex < numberOfExampleCapitals; capitalIndex++) {
      Double costsApplicationFactor = null;
      if (!totalCapital.isZero()) {
        costsApplicationFactor = calculatedCapitals[capitalIndex].getDoubleValue() / totalCapital.getDoubleValue();
        LOGGER.fine("capital=" + CF.format(calculatedCapitals[capitalIndex]) + ", total=" + CF.format(totalCapital) + ", costsApplicationFactor=" + costsApplicationFactor);
        PgCurrency costsForThisCapital = fixedCosts.multiply(costsApplicationFactor);
        calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].subtract(costsForThisCapital);
      }
      LOGGER.fine("end value after fixed costs = " + CF.format(calculatedCapitals[capitalIndex]));
      PgCurrency percentageCostsThisCapital = calculatedCapitals[capitalIndex].multiply(percentageCostsFactor);
      calculatedCapitals[capitalIndex] = calculatedCapitals[capitalIndex].subtract(percentageCostsThisCapital);
      LOGGER.fine("example capital = " + CF.format(calculatedCapitals[capitalIndex]));
    }
    
    exampleCapitalCalculationResult.calculatedExampleCapitals = calculatedCapitals;
    
    return exampleCapitalCalculationResult;
  }

}
