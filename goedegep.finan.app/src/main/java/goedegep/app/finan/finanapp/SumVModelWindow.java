package goedegep.app.finan.finanapp;

import goedegep.app.finan.stocksapp.StockDepotVModelPanel;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;

@SuppressWarnings("serial")
public class SumVModelWindow extends AppFrame {
  public static final String        WINDOW_TITLE = "Gecombineerd Effectendepot V Model";

  public SumVModelWindow(Customization customization, StockDepot stockDepot, Share share) {
    super(WINDOW_TITLE, customization);
    setContentPane(new StockDepotVModelPanel(this, stockDepot, share));
    pack();
  }
}