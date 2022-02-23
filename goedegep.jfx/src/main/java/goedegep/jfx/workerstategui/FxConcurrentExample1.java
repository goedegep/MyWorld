package goedegep.jfx.workerstategui;

import static javafx.concurrent.Worker.State.READY;
import static javafx.concurrent.Worker.State.RUNNING;

import goedegep.jfx.DefaultCustomizationFx;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FxConcurrentExample1 extends Application
{
    // Create the Buttons
    Button startButton = new Button("Start");
    Button cancelButton = new Button("Cancel");
    Button exitButton = new Button("Exit");

    // Create the task
    PrimeFinderTask task = new PrimeFinderTask();

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage)
    {
        // Create the Event-Handlers for the Buttons
        startButton.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                startTask();
            }
        });

        exitButton.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                stage.close();
            }
        });

        cancelButton.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                task.cancel();
            }
        });

        // Enable/Disable the Start and Cancel buttons
        startButton.disableProperty().bind(task.stateProperty().isNotEqualTo(READY));
        cancelButton.disableProperty().bind(task.stateProperty().isNotEqualTo(RUNNING));

        // Create the GridPane
        WorkerStateMonitorWindow<? extends Object> pane = new WorkerStateMonitorWindow<>(DefaultCustomizationFx.getInstance(), task);
        pane.show();

        // Create the ButtonBox
        HBox buttonBox = new HBox(5, startButton, cancelButton, exitButton);

        // Create the BorderPane
        BorderPane root = new BorderPane();
//        root.setCenter(pane);
        root.setBottom(buttonBox);

        // Set the Style-properties of the BorderPane
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root,500,400);
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("A Prime Number Finder Task");
        // Display the Stage
        stage.show();
    }

    public void startTask()
    {
        // Schedule the task on a background thread
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
}
