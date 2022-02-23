package goedegep.jfx.workerstategui;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.beans.binding.When;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


/**
 * This class provides a window to monitor the progress of a {@link Worker}.
 * <p>
 * Although this is a GUI class, it can also directly be used (mainly for debugging) by logic classes.
 * 
 * The window has the following layout:<br/>
 * <pre>
 * Title:      &lt;the title property set via updateTitle()&gt;
 * Message:    &lt;the message set via updateMessage()&gt;
 * Running:    &lt;indication of whether the worker is running or not&gt;
 * State:      &lt;the state of the worker&gt;
 * Total Work: &lt;the total amount of work to be done&gt;
 * Work Done:  &lt;the amount of work done so far&gt;
 * Progress:   &lt;a progress bar&gt;
 * Value:      &lt;the last value reported via updateValue()&gt;
 * Exception:  &lt;any Exception thrown by the worker&gt;
 * </pre>
 *
 * @param <T> the value provided by the Worker.
 */
public class WorkerStateMonitorWindow<T> extends JfxStage
{
  private static final Logger LOGGER = Logger.getLogger(WorkerStateMonitorWindow.class.getName());
  private static final String WINDOW_TITLE = "Worker State Monitor";

  // The GUI controls
  private final Label title = new Label("");
  private final Label message = new Label("");
  private final Label running = new Label("");
  private final Label state = new Label("");
  private final Label totalWork = new Label("");
  private final Label workDone = new Label("");
  private final Label progress = new Label("");
  private final TextArea value = new TextArea("");
  private final TextArea exception = new TextArea("");
  private final ProgressBar progressBar = new ProgressBar();

  /**
   * Constructor
   * <p>
   * With this constructor, no worker is specified yet. So this has to be done later by
   * calling bindToWorker().
   * 
   * @param customization the GUI customization. This parameter may be null, in which case the default customization is used.
   */
  public WorkerStateMonitorWindow(CustomizationFx customization)
  {
    this(customization, null);
  }

  /**
   * Constructor.
   * 
   * @param customization the GUI customization. This parameter may be null, in which case the default customization is used.
   * @param worker the worker for which information is shown.
   */
  public WorkerStateMonitorWindow(CustomizationFx customization, Worker<T> worker)
  {
    super(WINDOW_TITLE, customization);
    
    GridPane monitorPanel = createMonitorPanel();
    setScene(new Scene(monitorPanel, 800, 500));
    
    if (worker != null) {
      bindToWorker(worker);
    }
  }
  
  /**
   * Create the panel with all information.
   * 
   * @return the monitor panel.
   */
  private GridPane createMonitorPanel()
  {
    value.setPrefColumnCount(20);
    value.setPrefRowCount(3);
    exception.setPrefColumnCount(20);
    exception.setPrefRowCount(3);

    GridPane monitorPanel = new GridPane();
    monitorPanel.setHgap(5);
    monitorPanel.setVgap(5);

    monitorPanel.addRow(0, new Label("Title:"), title);
    monitorPanel.addRow(1, new Label("Message:"), message);
    monitorPanel.addRow(2, new Label("Running:"), running);
    monitorPanel.addRow(3, new Label("State:"), state);
    monitorPanel.addRow(4, new Label("Total Work:"), totalWork);
    monitorPanel.addRow(5, new Label("Work Done:"), workDone);
    monitorPanel.addRow(6, new Label("Progress:"), new HBox(2, progressBar, progress));
    monitorPanel.addRow(7, new Label("Value:"), value);
    monitorPanel.addRow(8, new Label("Exception:"), exception);
    
    return monitorPanel;
  }

  /**
   * This method binds this monitor window to a worker.
   * 
   * @param worker the worker for which information is shown.
   */
  public void bindToWorker(final Worker<T> worker)
  {
    LOGGER.info("=>");
    
    // Bind Labels to the properties of the worker
    title.textProperty().bind(worker.titleProperty());
    message.textProperty().bind(worker.messageProperty());
    running.textProperty().bind(worker.runningProperty().asString());
    state.textProperty().bind(worker.stateProperty().asString());

    totalWork.textProperty().bind(
        new When(worker.totalWorkProperty().isEqualTo(-1)).then("Unknown")
        .otherwise(worker.totalWorkProperty().asString()));

    workDone.textProperty().bind(
        new When(worker.workDoneProperty().isEqualTo(-1)).then("Unknown")
        .otherwise(worker.workDoneProperty().asString()));

    progress.textProperty().bind(
        new When(worker.progressProperty().isEqualTo(-1)).then("Unknown")
        .otherwise(worker.progressProperty().multiply(100.0).asString("%.2f%%")));

    progressBar.progressProperty().bind(worker.progressProperty());
    value.textProperty().bind(worker.valueProperty().asString());

    worker.exceptionProperty().addListener(new ChangeListener<Throwable>()
    {
      public void changed(ObservableValue<? extends Throwable> prop,
          final Throwable oldValue, final Throwable newValue)
      {
        if (newValue != null)
        {
          exception.setText(newValue.getMessage());
        }
        else
        {
          exception.setText("");
        }
      }
    });
    
    LOGGER.info("<=");
  }
}