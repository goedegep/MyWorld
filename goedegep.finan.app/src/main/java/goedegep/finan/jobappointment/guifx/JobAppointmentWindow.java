package goedegep.finan.jobappointment.guifx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import goedegep.app.finan.registry.FinanRegistry;
import goedegep.finan.jobappointment.JobAppointmentUtil;
import goedegep.finan.jobappointment.model.JobAppointment;
import goedegep.finan.jobappointment.model.JobAppointmentFactory;
import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.controls.ObjectControlCurrency;
import goedegep.jfx.controls.ObjectControlLocalDate;
import goedegep.properties.app.guifx.PropertiesEditor;
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
  
  private JobAppointment jobAppointment;
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory = null;
  private EMFResource<JobAppointment> jobAppointmentResource = null;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param jobAppointmentDepr the job appointment information.
   */
  public JobAppointmentWindow(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    
    if (FinanRegistry.jobAppointmentFile == null) {
      Alert alert = componentFactory.createErrorDialog("There's no filename configured for the file with the job appointment",
          "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application.");
      
      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showPropertiesEditor();
        }
      });
      
      return;
    }

    componentFactory = customization.getComponentFactoryFx();
    
    jobAppointmentResource = new EMFResource<>(
        JobAppointmentPackage.eINSTANCE, 
        () -> JobAppointmentFactory.eINSTANCE.createJobAppointment(),
        true);
    
    File file = new File(FinanRegistry.dataDirectory, FinanRegistry.jobAppointmentFile);
    String jobAppointmentFileName = file.getAbsolutePath();
    try {
      jobAppointment = jobAppointmentResource.load(jobAppointmentFileName);
    } catch (FileNotFoundException e) {
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
  
  private void showPropertiesEditor() {
    new PropertiesEditor("Finan properties", customization, FinanRegistry.propertyDescriptorsResource, FinanRegistry.customPropertiesFile);
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
    
    ObjectControlLocalDate objectInputLocalDate = componentFactory.createObjectInputLocalDate(jobAppointment.getCommencementOfEmploymentDate(), 300, true, null);
    gridPane.add(objectInputLocalDate, 1, 0);
    
    label = componentFactory.createLabel("Starting salary:");
    gridPane.add(label, 2, 0);
    
    ObjectControlCurrency objectInputCurrency = componentFactory.createObjectInputCurrency(jobAppointment.getStartingSalary(), 300, true, null);
    gridPane.add(objectInputCurrency, 3, 0);
    
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
    menuItem.setOnAction(e -> showSalaryEventsWindow());
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
