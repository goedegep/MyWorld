package goedegep.finan.jobappointment.gui;

import java.util.logging.Logger;

import goedegep.finan.jobappointment.model.JobAppointment;
import goedegep.finan.jobappointment.model.JobAppointmentPackage;
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
  private JobAppointment jobAppointment;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private SalaryEventsTable salaryEventsTable;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param salaryEvents the salary events to show.
   */
  public SalaryEventsWindow(CustomizationFx customization, JobAppointment jobAppointment) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.jobAppointment = jobAppointment;

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
    salaryEventsTable = new SalaryEventsTable(customization, jobAppointment, JobAppointmentPackage.eINSTANCE.getJobAppointment_Salaryevents());
    mainPane.setCenter(salaryEventsTable);
    
    setScene(new Scene(mainPane));
  }
}
