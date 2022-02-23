package goedegep.app.finan.effrek;

/**
 * This enum defines the types of information that can be shown in a {@link EffectenReportPanel}.
 * <p>
 * For each value, the following information is defined:
 * <ul>
 * <li>
 * effectenReportTableHeader<br/>
 * A title that can be used for a securities report table.
 * </li>
 * </ul>
 */
public enum EffectenReportPanelType {
  /**
   * A month report
   */
  MONTH(
      "Effecten die op het eind van de maand in bezit waren.",
      "Transacties deze maand"
      ),
  /**
   * A quarter report
   */
  QUARTER(
      "Effecten die op het eind van het kwartaal in bezit waren.",
      "Transacties dit kwartaal"
      ),
  /**
   * A tax (year) report
   */
  TAX(
      "Effecten die op het eind van het jaar in bezit waren.",
      "Transacties dit jaar"
      );
  
  public String getTransactionsTableHeader() {
        return transactionsTableHeader;
      }

  private String effectenReportTableHeader;
  private String transactionsTableHeader;

  private EffectenReportPanelType(String effectenReportTableHeader, String transactionsTableHeader) {
    this.effectenReportTableHeader = effectenReportTableHeader;
    this.transactionsTableHeader = transactionsTableHeader;
  }

  public String getEffectenReportTableHeader() {
    return effectenReportTableHeader;
  }
  

}
