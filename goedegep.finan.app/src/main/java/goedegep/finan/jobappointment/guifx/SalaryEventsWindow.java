package goedegep.finan.jobappointment.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.jobappointment.model.SalaryEvent;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class SalaryEventsWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(SalaryEventsWindow.class.getName());

  private static final String WINDOW_TITLE   = "Job Appointment";
  
  private CustomizationFx customization;
  private List<SalaryEvent> salaryEvents;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private SalaryEventsTable salaryEventsTable;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param salaryEvents the salary events to show.
   */
  public SalaryEventsWindow(CustomizationFx customization, List<SalaryEvent> salaryEvents) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.salaryEvents = salaryEvents;

    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        salaryEventsTable.setFilterExpression(newValue, null);
      }
      
    });
    
    show();
  }

  /**
   * Create the GUI.
   */
  private void createGUI() {
    
    /*
     * Main pane is a BorderPane.
     * North is for the table controls.
     * Center is the salary events table
     */
    
    BorderPane mainPane = new BorderPane();
        
    // Top: menu bar
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    mainPane.setTop(eObjectTableControlPanel);
    
    // Center: salary events table
    salaryEventsTable = new SalaryEventsTable(customization, salaryEvents);
    mainPane.setCenter(salaryEventsTable);
    
    setScene(new Scene(mainPane));
  }
}
