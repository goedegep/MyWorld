package goedegep.app.finan.effrek;

import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import goedegep.appgen.controls.ObjectInputCurrencySwing;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppPanel;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.effrek.EffRek;
import goedegep.util.money.PgCurrency;

@SuppressWarnings("serial")
public class EffRekStatusPanel extends AppPanel {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(EffRekStatusPanel.class.getName());

  // Status panel definitions
  private static final int        STATUS_PANEL_COLUMN_1 = 9;
  private static final int        STATUS_PANEL_COLUMN_1_SIZE = 77;
  private static final int        STATUS_PANEL_COLUMN_2 = STATUS_PANEL_COLUMN_1 + STATUS_PANEL_COLUMN_1_SIZE;
  private static final int        STATUS_PANEL_COLUMN_2_SIZE = 86;
  private static final int        STATUS_PANEL_COLUMN_3 = STATUS_PANEL_COLUMN_2 + STATUS_PANEL_COLUMN_2_SIZE;
  private static final int        STATUS_PANEL_COLUMN_3_SIZE = 15;
  private static final int        STATUS_PANEL_COLUMN_4 = STATUS_PANEL_COLUMN_3 + STATUS_PANEL_COLUMN_3_SIZE;
  private static final int        STATUS_PANEL_COLUMN_4_SIZE = 84;
  private static final int        STATUS_PANEL_COLUMN_5 = STATUS_PANEL_COLUMN_4 + STATUS_PANEL_COLUMN_4_SIZE;
  private static final int        STATUS_PANEL_COLUMN_5_SIZE = 86;
  private static final int        STATUS_PANEL_COLUMN_6 = STATUS_PANEL_COLUMN_5 + STATUS_PANEL_COLUMN_5_SIZE;
  private static final int        STATUS_PANEL_COLUMN_6_SIZE = 15;
  private static final int        STATUS_PANEL_COLUMN_7 = STATUS_PANEL_COLUMN_6 + STATUS_PANEL_COLUMN_6_SIZE;
  private static final int        STATUS_PANEL_COLUMN_7_SIZE = 84;
  private static final int        STATUS_PANEL_COLUMN_8 = STATUS_PANEL_COLUMN_7 + STATUS_PANEL_COLUMN_7_SIZE;
  private static final int        STATUS_PANEL_COLUMN_8_SIZE = 86;
  private static final int        STATUS_PANEL_COLUMN_9 = STATUS_PANEL_COLUMN_8 + STATUS_PANEL_COLUMN_8_SIZE;
  private static final int        STATUS_PANEL_COLUMN_9_SIZE = 15;
  private static final int        STATUS_PANEL_COLUMN_10 = STATUS_PANEL_COLUMN_9 + STATUS_PANEL_COLUMN_9_SIZE;
  private static final int        STATUS_PANEL_COLUMN_10_SIZE = 84;
  private static final int        STATUS_PANEL_COLUMN_11 = STATUS_PANEL_COLUMN_10 + STATUS_PANEL_COLUMN_10_SIZE;
  private static final int        TOP_MARGIN = 20;
  private static final int        ROW_DISTANCE = 24;
  
  private static final int        CURRENCY_FIELD_COLUMNS = 7;

  // Items in status (upper) part of center area
  private ObjectInputCurrencySwing textFieldWinst;  // winst veld
  private ObjectInputCurrencySwing textFieldWinst2; // winst veld
  private ObjectInputCurrencySwing textFieldSaldo; // saldo veld
  private ObjectInputCurrencySwing textFieldSaldo2; // saldo veld
  private ObjectInputCurrencySwing textFieldDepotWaarde; // depot waarde veld
  private ObjectInputCurrencySwing textFieldNettoStorting; // netto storting veld
  private ObjectInputCurrencySwing textFieldNettoStorting2; // netto storting veld
  private ObjectInputCurrencySwing textFieldCashFlow; // cash-flow veld
  private ObjectInputCurrencySwing textFieldOverigeWinst; // overige winst veld
  private ObjectInputCurrencySwing textFieldHandelWinst; // handel winst veld
  private ObjectInputCurrencySwing fieldInvestering; // investerings veld
  
  private EffRek effRek;
  private LocalDate statusDate = null; // Date for which rates are shown. If null, today.
  private ComponentFactory componentFactory;

  public EffRekStatusPanel(AppFrame owner, EffRek effRek, LocalDate statusDate) {
    super(owner, new Dimension(600, 110));
    setSize(800, 300);
    this.effRek = effRek;
    this.statusDate = statusDate;
    componentFactory = getTheComponentFactory();
    setLayout(new SpringLayout());
    int vertOffset = TOP_MARGIN;

    /*
     * First row: winst = saldo + depot waarde - netto storting
     */
    // winst label
    JLabel label = componentFactory.createLabel("winst", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_1, vertOffset);

    // Use a bigger font for the + and - signs.
    Font normalFont = label.getFont();         // get the normal font from one of the labels
    Font signFont = new Font(normalFont.getFontName(), Font.BOLD, normalFont.getSize()+2);

    // winst textfield
    textFieldWinst = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    textFieldWinst.setEditable(false);
    addComponentStatusPanel(textFieldWinst, STATUS_PANEL_COLUMN_2, vertOffset);

    // =
    label = createSymbolLabel("=", signFont);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_3, vertOffset);

    // saldo label
    label = componentFactory.createLabel("saldo", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_4, vertOffset);

    // saldo textfield
    textFieldSaldo = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldSaldo, STATUS_PANEL_COLUMN_5, vertOffset);

    // +
    label = createSymbolLabel("+", signFont);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_6, vertOffset);

    // depot waarde label
    label = componentFactory.createLabel("depot waarde", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_7, vertOffset);

    // depot waarde textfield
    textFieldDepotWaarde = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldDepotWaarde, STATUS_PANEL_COLUMN_8, vertOffset);

    // -
    label = createSymbolLabel("-", signFont);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_9, vertOffset);

    // netto storting label
    label = componentFactory.createLabel("netto storting", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_10, vertOffset);

    // netto storting textfield
    textFieldNettoStorting = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldNettoStorting, STATUS_PANEL_COLUMN_11, vertOffset);


    /*
     *  Second row: cashflow = saldo - netto storting
     */
    vertOffset += ROW_DISTANCE;

    // cash-flow label
    label = componentFactory.createLabel("cash-flow", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_1, vertOffset);

    // cash-flow textfield
    textFieldCashFlow = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldCashFlow, STATUS_PANEL_COLUMN_2, vertOffset);

    // =
    label = createSymbolLabel("=", signFont);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_3, vertOffset);

    // saldo label
    label = componentFactory.createLabel("saldo", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_4, vertOffset);

    // saldo textfield
    textFieldSaldo2 = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldSaldo2, STATUS_PANEL_COLUMN_5, vertOffset);

    // -
    label = createSymbolLabel("-", signFont);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_6, vertOffset);

    // netto storting label
    label = componentFactory.createLabel("netto storting", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_7, vertOffset);

    // investering textfield
    textFieldNettoStorting2 = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldNettoStorting2, STATUS_PANEL_COLUMN_8, vertOffset);

    /*
     * Third row: overige winst = winst - handel winst
     */
    vertOffset += ROW_DISTANCE;

    // overige winst label
    label = componentFactory.createLabel("overige winst", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_1, vertOffset);

    // handel winst textfield
    textFieldOverigeWinst = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldOverigeWinst, STATUS_PANEL_COLUMN_2, vertOffset);

    // =
    label = createSymbolLabel("=", signFont);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_3, vertOffset);

    // winst label
    label = componentFactory.createLabel("winst", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_4, vertOffset);

    // winst textfield
    textFieldWinst2 = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldWinst2, STATUS_PANEL_COLUMN_5, vertOffset);

    // -
    label = createSymbolLabel("-", signFont);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_6, vertOffset);

    // handelwinst label
    label = componentFactory.createLabel("handelwinst", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_7, vertOffset);

    // handel winst textfield
    textFieldHandelWinst = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(textFieldHandelWinst, STATUS_PANEL_COLUMN_8, vertOffset);

    /*
     * Fourth row: Investering
     */
    vertOffset += ROW_DISTANCE;

    // investering label
    label = componentFactory.createLabel("investering", SwingConstants.LEFT);
    addComponentStatusPanel(label, STATUS_PANEL_COLUMN_1, vertOffset);

    // investering textfield
    fieldInvestering = componentFactory.createObjectInputCurrency("", CURRENCY_FIELD_COLUMNS, true, null);
    addComponentStatusPanel(fieldInvestering, STATUS_PANEL_COLUMN_2, vertOffset);
  }

  /**
   * Update the status panel.
   */
  public void update() {
    PgCurrency balance = effRek.getBalance();
    PgCurrency nettoStorting = effRek.getNettoStorting();
    PgCurrency investering = effRek.getVerzamelDepot().getTotalInvestment();
    PgCurrency value = effRek.getVerzamelDepot().getValue(statusDate);
    
    // All information is shown in the same currency, which is the currency of the balance.
    int currencyUnitToUse = balance.getCurrency();    
    nettoStorting = nettoStorting.certifyCurrency(currencyUnitToUse);
    investering = investering.certifyCurrency(currencyUnitToUse);
    value = value.certifyCurrency(currencyUnitToUse);

    PgCurrency profit = balance.add(value).subtract(nettoStorting);
    PgCurrency cashFlow = balance.subtract(nettoStorting);
    
    PgCurrency handelWinst = effRek.getVerzamelDepot().getTotalVModelProfit();

    textFieldSaldo.setObjectValue(balance);
    textFieldSaldo2.setObjectValue(balance);
    textFieldDepotWaarde.setObjectValue(value);
    textFieldNettoStorting.setObjectValue(nettoStorting);
    textFieldNettoStorting2.setObjectValue(nettoStorting);
    textFieldWinst.setObjectValue(profit);
    textFieldWinst2.setObjectValue(profit);
    PgCurrency overigeWinst = null;
    if (handelWinst != null) {
      handelWinst = handelWinst.certifyCurrency(currencyUnitToUse);
      overigeWinst = profit.subtract(handelWinst);
    }
    textFieldOverigeWinst.setObjectValue(overigeWinst);
    textFieldCashFlow.setObjectValue(cashFlow);
    if (handelWinst != null) {
      textFieldHandelWinst.setObjectValue(handelWinst);
    } else {
      textFieldHandelWinst.setObjectValue(null);
    }
    fieldInvestering.setObjectValue(investering);
  }
  
  private void addComponentStatusPanel(JComponent component, int horOffset, int vertOffset) {
    SpringLayout layout = (SpringLayout) getLayout();
    add(component);

    layout.putConstraint(SpringLayout.WEST, component,
        horOffset,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, component,
        vertOffset,
        SpringLayout.NORTH, this);
  }
  
  private JLabel createSymbolLabel(String text, Font font) {
    JLabel label = componentFactory.createLabel(text, SwingConstants.LEFT);
    label.setFont(font);
    
    return label;
    
  }
}
