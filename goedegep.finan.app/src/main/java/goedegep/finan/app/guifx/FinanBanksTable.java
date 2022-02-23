package goedegep.finan.app.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.app.finan.gen.FinanBank;
import goedegep.finan.basic.SumAccount;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FinanBanksTable extends TableView<SumAccount> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(FinanBanksTable.class.getName());

  public FinanBanksTable(List<FinanBank> banks) {
    TableColumn<SumAccount, Object> tableColumn = new TableColumn<>("Bank");
    getColumns().add(tableColumn);
    tableColumn = new TableColumn<>("Saldo");
    getColumns().add(tableColumn);
    tableColumn = new TableColumn<>("Waarde");
    getColumns().add(tableColumn);
    tableColumn = new TableColumn<>("Totaal");
    getColumns().add(tableColumn);
  }
}
