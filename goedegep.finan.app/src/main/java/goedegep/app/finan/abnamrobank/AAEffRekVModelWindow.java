package goedegep.app.finan.abnamrobank;

import goedegep.app.finan.stocksapp.StockDepotVModelPanel;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;

@SuppressWarnings("serial")
public class AAEffRekVModelWindow extends AppFrame {
  public static final String        WINDOW_TITLE = "ABN AMRO Effectendepot V Model";

  public AAEffRekVModelWindow(Customization customization, StockDepot stockDepot, Share share) {
    super(WINDOW_TITLE, customization, null);
    setContentPane(new StockDepotVModelPanel(this, stockDepot, share));
    pack();
  }
}