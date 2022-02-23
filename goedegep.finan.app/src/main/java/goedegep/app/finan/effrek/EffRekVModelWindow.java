package goedegep.app.finan.effrek;

import goedegep.app.finan.stocksapp.StockDepotVModelPanel;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;

@SuppressWarnings("serial")
public class EffRekVModelWindow extends AppFrame {
  
  public EffRekVModelWindow(String title, Customization customization, StockDepot stockDepot, Share share) {
    super(title, customization);
    setContentPane(new StockDepotVModelPanel(this, stockDepot, share));
    pack();
  }
}
