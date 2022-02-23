package goedegep.app.finan.lynxapp;

import goedegep.app.finan.stocksapp.StockDepotVModelPanel;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;

@SuppressWarnings("serial")
public class LynxEffRekVModelWindow extends AppFrame {
  public static final String        WINDOW_TITLE = "Lynx Effectendepot V Model";

  public LynxEffRekVModelWindow(Customization customization, StockDepot stockDepot, Share share) {
    super(WINDOW_TITLE, customization, null);
    setContentPane(new StockDepotVModelPanel(this, stockDepot, share));
    pack();
  }
}