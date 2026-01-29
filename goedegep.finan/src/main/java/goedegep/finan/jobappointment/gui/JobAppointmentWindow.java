package goedegep.finan.jobappointment.gui;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import goedegep.finan.jobappointment.logic.JobAppointmentUtil;
import goedegep.finan.jobappointment.model.JobAppointment;
import goedegep.finan.jobappointment.model.JobAppointmentFactory;
import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.logic.FinanRegistry;
import goedegep.finan.svc.FinanService;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlLocalDate;
import goedegep.util.emf.EMFResource;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class JobAppointmentWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(JobAppointmentWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE   = "Job Appointment";
  
  private FinanRegistry finanRegistry;
  private FinanService finanService;
  private JobAppointment jobAppointment;
  private EMFResource<JobAppointment> jobAppointmentResource = null;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param jobAppointmentDepr the job appointment information.
   */
  public JobAppointmentWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    finanRegistry = FinanRegistry.getInstance();
    finanService = FinanService.getInstance();
    
    if (finanRegistry.getJobAppointmentFile() == null) {
      Alert alert = componentFactory.createErrorDialog("There's no filename configured for the file with the job appointment",
          "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application.");
      
      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          finanService.showPropertiesEditor();
        }
      });
      
      return;
    }

    componentFactory = customization.getComponentFactoryFx();
    
    jobAppointmentResource = new EMFResource<>(
        JobAppointmentPackage.eINSTANCE, 
        () -> JobAppointmentFactory.eINSTANCE.createJobAppointment(),
        ".xmi",
        true);
    
    File file = new File(finanRegistry.getDataDirectory(), finanRegistry.getJobAppointmentFile());
    String jobAppointmentFileName = file.getAbsolutePath();
    try {
      jobAppointment = jobAppointmentResource.load(jobAppointmentFileName);
    } catch (IOException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          "The file with the job appointment (" + jobAppointmentFileName + ") doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" you can't do anything in this screen.");
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
          LOGGER.severe("yes, create file");
          jobAppointment = jobAppointmentResource.newEObject();
//          convertJobAppointment(jobAppointmentDepr);
          try {
            jobAppointmentResource.save(jobAppointmentFileName);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        } else {
          LOGGER.severe("no, don't create file");
        }
      });
    }
    
    if (jobAppointment != null) {
      createGUI();
    }
    
    show();
  }

  private void createGUI() {
    
    /*
     * Main pane is a BorderPane.
     * North is the menu bar
     * Center is ...
     * Bottom is ...
     */
    
    BorderPane mainPane = new BorderPane();
        
    // Top: menu bar
    mainPane.setTop(createMenuBar());
    
    // Center: job appointment details
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    Label label = componentFactory.createLabel("Commencement of employment date:");
    gridPane.add(label, 0, 0);
    
    ObjectControlLocalDate objectInputLocalDate = componentFactory.createObjectControlLocalDate(jobAppointment.getCommencementOfEmploymentDate(), 300, true, null);
    gridPane.add(objectInputLocalDate.getControl(), 1, 0);
    
    label = componentFactory.createLabel("Starting salary:");
    gridPane.add(label, 2, 0);
    
    ObjectControlCurrency objectInputCurrency = componentFactory.createObjectControlCurrency(jobAppointment.getStartingSalary(), 300, true, null);
    gridPane.add(objectInputCurrency.getControl(), 3, 0);
    
    mainPane.setCenter(gridPane);
    
    // Salary development chart
    JobAppointmentUtil.addSalaryPayments(jobAppointment, 2002, Calendar.JANUARY);
    SalaryDevelopmentChart salaryDevelopmentChart = new SalaryDevelopmentChart(customization, JobAppointmentUtil.getSalaryPayments(jobAppointment));
    mainPane.setBottom(salaryDevelopmentChart);

    setScene(new Scene(mainPane));
  }

  /**
   * Create the menu bar for this window.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;
    MenuItem menuItem;

    // Salary menu
    menu = new Menu("Salary");

    // Salary: Salary events
    menuItem = componentFactory.createMenuItem("Salary events");
    menuItem.setOnAction(_ -> showSalaryEventsWindow());
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    
    return menuBar;
  }

  /**
   * Show the window with salary changes.
   */
  protected void showSalaryEventsWindow() {
    new SalaryEventsWindow(customization, jobAppointment);
  }
}
