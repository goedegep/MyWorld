package goedegep.travels.app.guifx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.editor.controls.EditorControlFileSelecter;
import goedegep.travels.app.logic.VacationToHtmlConverter;
import goedegep.travels.app.logic.VacationToHtmlConverterSetting;
import goedegep.travels.app.logic.VacationsUtils;
import goedegep.travels.model.Vacation;
import goedegep.util.file.FileUtils;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.print.JobSettings;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

/**
 * This class provides a window to print a <code>Vacation</code>.
 * <p>
 * On the left, the window shows the document representation of the vacation.<br/>
 * The right side provides controls:
 * <ul>
 * <li>converter settings, to control which information is show in the docuement and how it is formatted</li>
 * <li>printing controls, to print the document.</li>
 * <li>save as PDF file, to save the document to a file.</li>
 * <li>page controls, to navigate through the pages of the document.</li>
 * </ul>
 * 
 * At the bottom, a status label is shown that confirms that a PDF file is created, or that it has failed.<br/>
 */
public class PrintWindow extends JfxStage {
  
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PrintWindow.class.getName());
  private static final String WINDOW_TITLE = "Print a vacation or save it as a PDF file";
  
  /**
   * Texts for the HTML output types radio buttons.
   */
  private final static String HTML_STANDARD = "Standard HTML";
  private final static String HTML_EMBED = "HTML with embedded images";
  private final static String HTML_ZIP = "Zip file with HTML and related files";
 
  /**
   * The in-memory file system used to by the VacationToPDFTask.
   * We create this in this class and pass it on to the VacationToPDFTask, so that it can be reused.
   */
  FileSystem imfs = null;
  
  /**
   * The converter to generate HTML for a Vacation object.
   * We create this in this class and pass it on to the VacationToPDFTask, so that it can be reused.
   */
  private VacationToHtmlConverter vacationToHtmlConverter;
  
  /**
   * The settings for the VacationToHtmlConverter.
   * These settings can be changed in this window.
   */
  private Set<VacationToHtmlConverterSetting> settings = null;
  
  /**
   * Index of the page currently shown in the preview.
   */
  private int currentPageIndex = 0;
  
  /**
   * The PDF document to be printed.
   */
  private PDDocument document;
  
  /**
   * The PDF renderer for the document.
   * This is used to render the pages of the document to images (which can then be printed).
   */
  private PDFRenderer renderer;
  
  /**
   * The ImageView that shows the preview of the PDF document.
   * This is updated when the PDF document is generated or when the user navigates through the pages.
   */
  private ImageView previewImageView;
  
  /**
   * The PrinterJob used to print the document.
   * This is created when the user opens the print dialog.
   */
  private PrinterJob printerJob;
  
  /**
   * The thread that runs the VacationToPDFTask.
   */
  private Thread pdfRendererThread = null;
  
  /**
   * The task that generates the PDF document from the Vacation object.
   * This task runs in a separate thread (the pdfRendererThread) to avoid blocking the GUI.
   */
  private VacationToPDFTask vacationToPDFTask;
  
  /**
   * An image that is shown while the PDF document is being (re)generated.
   * This image is a placeholder that indicates that an update is in progress.
   */
  private Image updatingImage = null;

  /**
   * The <code>Vacation</code> to be printed.
   * This is passed to the constructor.
   */
  private Vacation vacation;
  
  /**
   * The label that shows the status of the PDF generation task.
   */
  private Label statusLabel = null;

  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param vacation the <code>Vacation</code> to be printed
   * @throws Exception if something goes wrong
   */
  public PrintWindow(CustomizationFx customization, Vacation vacation) throws Exception {
    super(customization, WINDOW_TITLE);
    
    this.vacation = vacation;
    
    settings = VacationToHtmlConverterSetting.getDefaultSettings();  // Start with the default settings.
    
    // Create the in-memory file system for the PDF generation task.
    imfs = Jimfs.newFileSystem(Configuration.unix());
    
    // Create the VacationToHtmlConverter for the PDF generation task.
    vacationToHtmlConverter = new VacationToHtmlConverter(settings);
    
    createUpdatingImage();
    createGui();
    
    show();
    
    runVacationToPDFTask();
  }

  /**
   * Create an image that indicates that the PDF document is being updated.
   * This image is shown while the PDF document is being (re)generated.
   */
  private void createUpdatingImage() {
    int width = 595;
    int height = 841;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = bufferedImage.createGraphics();

    // Fill background
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(0, 0, width, height);

    // Set font and color
    g2d.setColor(Color.GRAY);
    Font font = new Font("Arial", Font.BOLD, 32);
    g2d.setFont(font);

    // Center the text
    FontMetrics fm = g2d.getFontMetrics();
    String text = "Update in progress";
    int textWidth = fm.stringWidth(text);
    int textHeight = fm.getHeight();
    int x = (width - textWidth) / 2;
    int y = (height - textHeight) / 2 + fm.getAscent();

    g2d.drawString(text, x, y);
    g2d.dispose();

    // Convert to JavaFX Image
    updatingImage = SwingFXUtils.toFXImage(bufferedImage, null);
  }

  /**
   * Create the GUI
   * <p>
   * Left side is PDF page preview.
   * Right side is settings and control panel.
   * - converter settings
   * 
   * - print dialog button
   * - print button
   * - page control buttons (first, previous, next, last)
   * 
   * - save as PDF
   * 
   * Bottom is status label.
   */
  private void createGui() {
    BorderPane mainPane = new BorderPane();

    // Center (is left): PDF page preview
    previewImageView = new ImageView(updatingImage);
    VBox previewBox = componentFactory.createVBox(new Insets(16, 16, 16, 16));
    previewBox.getChildren().add(previewImageView);
    mainPane.setCenter(previewBox);

    VBox controlPanel = componentFactory.createVBox(16, 16);

    // Right: settings and control panel
    controlPanel.getChildren().addAll(
        createSettingsPanel(),
        createPrintingPanel(),
        createSaveAsPDFFilePanel(),
        createSaveAsHTMLPanel(),
        createPageControlsPanel()
        );

    mainPane.setRight(controlPanel);
    
    // Bottom: status label
    HBox statusBox = componentFactory.createHBox(16, 16);
    statusLabel = componentFactory.createLabel("");
    statusBox.getChildren().add(statusLabel);
    mainPane.setBottom(statusBox);

    setScene(new Scene(mainPane));
  }

  /**
   * Create the settings panel.
   * <p>
   * This panel contains the settings for the VacationToHtmlConverter.
   * The user can change these settings and the PDF generation task will be restarted to apply the new settings.
   * 
   * @return the settings panel
   */
  private Node createSettingsPanel() {
    TitledPane titledPane = new TitledPane();
    titledPane.setText("Settings for the PDF generation");
    
    Label label;
    VacationToHtmlConverterSetting setting;
    int row = 0;

    GridPane settingsPanel = componentFactory.createGridPane(16, 16);
    
    setting = VacationToHtmlConverterSetting.SHOW_LOCATION_COORDINATES;
    label = componentFactory.createLabel(setting.getDisplayName());
    CheckBox settingCheckBox = componentFactory.createCheckBox(null, settings.contains(VacationToHtmlConverterSetting.SHOW_LOCATION_COORDINATES));
    settingsPanel.addRow(row, label, settingCheckBox);
    settingCheckBox.setOnAction(_ -> {
      if (settingCheckBox.isSelected()) {
        if (!settings.contains(VacationToHtmlConverterSetting.SHOW_LOCATION_COORDINATES)) {
          settings.add(VacationToHtmlConverterSetting.SHOW_LOCATION_COORDINATES);
        }
      } else {
        settings.remove(VacationToHtmlConverterSetting.SHOW_LOCATION_COORDINATES);
      }
      runVacationToPDFTask(); // Restart the PDF generation task to apply the new setting
    });

    titledPane.setContent(settingsPanel);
    
    return titledPane;
  }
  
  /**
   * Create the printing panel.
   * <p>
   * This panel contains buttons to print the document or show the print dialog.
   * 
   * @return the printing panel
   */
  private Node createPrintingPanel() {
    TitledPane titledPane = new TitledPane();
    titledPane.setText("Printing");
    
    VBox controlPanel = componentFactory.createVBox(16);
    
    Button printDialogButton = componentFactory.createButton("print dialog", "press to show the print dialog");
    printDialogButton.setOnAction(_ -> showPrintDialog());
    
    Button printButton = componentFactory.createButton("print", "press to print the document shown on the left");
    printButton.setOnAction(_ -> printDocument(null));
    
    controlPanel.getChildren().addAll(printDialogButton, printButton);
    titledPane.setContent(controlPanel);
    
    return titledPane;
  }
  
  /**
   * Create the "Save as PDF file" panel.
   * <p>
   * This panel allows the user to save the PDF document to a file.
   * The user can select the file path and click the "Save" button to save the document.
   *
   * @return the "Save as PDF file" panel
   */
  private Node createSaveAsPDFFilePanel() {
    TitledPane titledPane = new TitledPane();
    titledPane.setText("Save as PDF file");
    
    VBox controlPanel = componentFactory.createVBox(16);
    
    EditorControlFileSelecter editorControlFileSelecter = componentFactory.createEditorControlFileSelecter(400, "path to save the PDF file to",
        "change path", "click to save to a different location", "Select file to save to", true);
    editorControlFileSelecter.setOpenOrSaveDialog(true);
    String vacationFolder = VacationsUtils.getTravelFilesFolder(vacation);
    if (vacationFolder != null) {
      Path vacationFolderPath = Paths.get(vacationFolder);
      if (Files.exists(vacationFolderPath) && Files.isDirectory(vacationFolderPath)) {
        String vacationId = vacation.getId();
        if (vacationId != null  &&  !vacationId.isEmpty()) {
          Path pdfFilePath = Paths.get(vacationFolder, vacationId + ".pdf");
          editorControlFileSelecter.setObject(pdfFilePath.toFile());
        }
      }
    }
    
    Button saveButton = componentFactory.createButton("Save", "press to save the document shown on the left as a PDF file");
    saveButton.setOnAction(_ -> saveDocumentAsPDFFile(editorControlFileSelecter.getValue()));
    
    controlPanel.getChildren().addAll(
        new Label("Output file:"),
        editorControlFileSelecter.getControl(),
        editorControlFileSelecter.getFileChooserButton(),
        saveButton
        );
    
    titledPane.setContent(controlPanel);
    
    return titledPane;
  }
  
  /**
   * Create the "Save as HTML" panel.
   * <p>
   * This panel allows the user to save the vacation information in HTML format.
   * The user can select type or HTML output, the file path and click the "Save" button to save the HTML.
   *
   * @return the "Save as HTML file" panel
   */
  private Node createSaveAsHTMLPanel() {
    TitledPane titledPane = new TitledPane();
    titledPane.setText("Save as HTML");
    
    VBox controlPanel = componentFactory.createVBox(16);
    
    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton standardHTMLRadioButton = new RadioButton("Standard HTML");
    standardHTMLRadioButton.setToggleGroup(toggleGroup);
    
    RadioButton htmlWithEmbeddedImagesRadioButton = new RadioButton("HTML with embedded images");
    htmlWithEmbeddedImagesRadioButton.setToggleGroup(toggleGroup);
    
    RadioButton htmlZipFileRadioButton = new RadioButton("Zip file with HTML and related files");
    htmlZipFileRadioButton.setToggleGroup(toggleGroup);
    
    standardHTMLRadioButton.setSelected(true);
    controlPanel.getChildren().addAll(
        new Label("Type of HTML output:"),
        standardHTMLRadioButton,
        htmlWithEmbeddedImagesRadioButton,
        htmlZipFileRadioButton
        );
    
    EditorControlFileSelecter editorControlFileSelecter = componentFactory.createEditorControlFileSelecter(400, "path to save the HTML (zip) file to",
        "change path", "click to save to a different location", "Select file to save to", true);
    editorControlFileSelecter.setOpenOrSaveDialog(true);
    String vacationFolder = VacationsUtils.getTravelFilesFolder(vacation);
    if (vacationFolder != null) {
      Path vacationFolderPath = Paths.get(vacationFolder);
      if (Files.exists(vacationFolderPath) && Files.isDirectory(vacationFolderPath)) {
        String vacationId = vacation.getId();
        if (vacationId != null  &&  !vacationId.isEmpty()) {
          Path pdfFilePath = Paths.get(vacationFolder, vacationId + ".html");
          editorControlFileSelecter.setObject(pdfFilePath.toFile());
        }
      }
    }
    
    Button saveButton = componentFactory.createButton("Save", "press to save the document shown on the left as an HTML (zip) file");
    saveButton.setOnAction(_ -> {
      Toggle selectedToggle = toggleGroup.getSelectedToggle();
      if (selectedToggle instanceof RadioButton selectedRadioButton) {
        String selectedText = selectedRadioButton.getText();
        saveDocumentAsHTML(selectedText, editorControlFileSelecter.getValue());
      }
      });
    
    controlPanel.getChildren().addAll(
        new Label("Output file:"),
        editorControlFileSelecter.getControl(),
        editorControlFileSelecter.getFileChooserButton(),
        saveButton
        );
    
    titledPane.setContent(controlPanel);
    
    return titledPane;
  }
  
  /**
   * Create the page controls panel.
   * <p>
   * This panel contains buttons to navigate through the pages of the PDF document.
   * The user can go to the first, previous, next, or last page of the document.
   *
   * @return the page controls panel
   */
  private Node createPageControlsPanel() {
    TitledPane titledPane = new TitledPane();
    titledPane.setText("Preview page controls");

    HBox pageControlsBox = componentFactory.createHBox(16);
    Button firstPageButton = componentFactory.createButton("<<", "go to the first page of the document");
    firstPageButton.setOnAction(_ -> {
      currentPageIndex = 0;
      displayPage();
    });
    Button previousPageButton = componentFactory.createButton("<", "go to the previous page of the document");
    previousPageButton.setOnAction(_ -> {
      if (currentPageIndex > 0) {
        currentPageIndex--;
        displayPage();
      }
    });
    Button nextPageButton = componentFactory.createButton(">", "go to the next page of the document");
    nextPageButton.setOnAction(_ -> {
      if (currentPageIndex < document.getNumberOfPages() - 1) {
        currentPageIndex++;
        displayPage();
      }
    });
    Button lastPageButton = componentFactory.createButton(">>", "go to the last page of the document");
    lastPageButton.setOnAction(_ -> {
      currentPageIndex = document.getNumberOfPages() - 1;
      displayPage();
    });
    pageControlsBox.getChildren().addAll(firstPageButton, previousPageButton, nextPageButton, lastPageButton);
    
    titledPane.setContent(pageControlsBox);
    
    return titledPane;
  }

  /*
   * Display the current page in the preview image view.
   */
  private void displayPage() {
    BufferedImage pageImage;
    try {
      pageImage = renderer.renderImage(currentPageIndex);
      Image image = SwingFXUtils.toFXImage(pageImage, null);
      previewImageView.setImage(image);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Show the systems print dialog.
   * <p>
   * If the user confirms the dialog, the document is printed.
   */
  private void showPrintDialog() {
    getPrinterJob();
    
    if (printerJob.showPrintDialog(this)) {
      JobSettings jobSettings = printerJob.getJobSettings();
      Object pageRangesObject = jobSettings.pageRangesProperty().getValue();
      if (pageRangesObject instanceof PageRange[] pageRanges) {
        printDocument(pageRanges);
      } else {
        printDocument(null);
      }
    }
  }
  
  /**
   * Print (page ranges of) the document.
   *
   * @param pageRanges The page ranges to print. If null, the whole document is printed.
   */
  private void printDocument(PageRange[] pageRanges) {
   
    if (pageRanges == null) {
      pageRanges = new PageRange[] {new PageRange(1, document.getNumberOfPages())}; // By default, print the whole document.
    }
    
    getPrinterJob();


    try {
      for (PageRange pageRange: pageRanges) {
        for (int pageNr = pageRange.getStartPage(); pageNr <= pageRange.getEndPage() && pageNr <= document.getNumberOfPages(); pageNr++) {
          BufferedImage pageImage = renderer.renderImage(pageNr - 1); // pageNr is 1-based, but PDFRenderer uses 0-based index
          Image image = SwingFXUtils.toFXImage(pageImage, null);
          ImageView imageView = new ImageView(image);
          Scale scale = new Scale(0.85, 0.85);
          imageView.getTransforms().add(scale);
          printerJob.printPage(imageView);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    printerJob.endJob();
    printerJob = null; // Reset the printer job to allow for a new print job later. 
  }

  /**
   * Save the PDF document to a file.
   * <p>
   * This method is called when the user clicks the "Save" button in the "Save as PDF file" panel.
   *
   * @param file The file to save the PDF document to.
   */
  private void saveDocumentAsPDFFile(File file) {
    Path source = vacationToPDFTask.getPDFFilePath();
    Path target = file.toPath();
    try {
      Files.copy(source, target);
      statusLabel.setText("Saved PDF file to: " + target.toAbsolutePath());
    } catch (IOException e) {
      statusLabel.setText("Failed to save PDF file. System message: " + e);
    }
  }
  
  /**
   * Save the vacation information as an HTML file.
   * <p>
   * This method is called when the user clicks the "Save" button in the "Save as HTML" panel.
   *
   * @param file The file to save the HTML document to.
   */
  private void saveDocumentAsHTML(String htmlType, File file) {
    switch (htmlType) {
    case HTML_STANDARD -> {
      String htmlString = vacationToHtmlConverter.vacationToHtml(vacation);
      file = secureExtension(file, ".html");
      try(PrintWriter out = new PrintWriter(file)) {
        out.println(htmlString);
        out.close();
        statusLabel.setText("Vacation exported to " + file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    case HTML_EMBED -> {
      String htmlString = vacationToHtmlConverter.vacationToHtmlWithEmbedImages(vacation);
      file = secureExtension(file, ".html");
      try(PrintWriter out = new PrintWriter(file)) {
        out.println(htmlString);
        out.close();
        statusLabel.setText("Vacation exported to " + file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      }
    case HTML_ZIP -> {
      file = secureExtension(file, ".zip");
      vacationToHtmlConverter.vacationToHtmlZipFile(vacation, file.toPath());
    }
    default -> {
      return;
    }
    }

    statusLabel.setText("Saved HTML file to: " + file.getAbsolutePath());
  }
  
  /** 
   * Ensure that the file has the required extension.
   * <p>
   * If the file does not have the required extension, a new file is created with the same name but with the required extension.
   *
   * @param file The file to check.
   * @param requiredExtension The required file extension (without the dot).
   * @return The original file if it has the required extension, or a new file with the required extension.
   */
  private File secureExtension(File file, String requiredExtension) {
    String extension = FileUtils.getFileExtension(file);
    if (requiredExtension.equals(extension)) {
      return file;
    }
    
    return new File(FileUtils.getFileNameWithoutExtension(file) + "." + requiredExtension);
  }
  
  /**
   * If the PrinterJob is not yet created, it is created here.
   */
  private void getPrinterJob() {
    if (printerJob == null) {
      printerJob = PrinterJob.createPrinterJob();
    }
  }
  
  
  /**
   * Create a VacationToPDFTask
   * <p>
   * When the task is ready, it will call the handleNewDocumentReceived method to display the PDF document.
   */
  private void runVacationToPDFTask() {
    previewImageView.setImage(updatingImage);
    cleanupVacationToPDFTask();
    
    /*
     * Create a RenderPDFTask and handle information provided by this task.
     */
    vacationToPDFTask = new VacationToPDFTask(vacationToHtmlConverter, imfs, vacation);
    
    // Handle results - fill the preview window with the created document.
    ChangeListener<PDDocument> listener = (_, _, newDoc) -> {
      handleNewDocumentReceived(newDoc);
    };
    vacationToPDFTask.valueProperty().addListener(listener);

    pdfRendererThread = new Thread(vacationToPDFTask);
    pdfRendererThread.setDaemon(true);
    pdfRendererThread.start();
  }
  
  /**
   * Handle a newly created PDF document.
   * <p>
   * This method is called by VacationToPDFTask upon completion.
   * It sets the document and renderer, and displays the first page.
   *
   * @param document The new PDF document to be displayed.
   */
  private void handleNewDocumentReceived(PDDocument document) {
    if (document == null) {
      return;
    }

    this.document = document;
    renderer = new PDFRenderer(document);
    
    // If the current page index is out of bounds, set it to the last page.
    if (currentPageIndex >= document.getNumberOfPages()) {
      currentPageIndex = document.getNumberOfPages() - 1;
    }

    displayPage();
  }
  
  /**
   * Cleanup the VacationToPDFTask and the PDF renderer thread.
   * <p>
   * This method is called when a new task is started.<br/>
   * The VacationToPDFTask keeps the PDF file in the in-memory file system, so we just copy it for saving it to a file.
   */
  private void cleanupVacationToPDFTask() {
    if (vacationToPDFTask != null) {
      vacationToPDFTask.cleanup();
      vacationToPDFTask.cancel();
      vacationToPDFTask = null;
    }
    if (pdfRendererThread != null) {
      pdfRendererThread.interrupt();
      pdfRendererThread = null;
    }    
  }
  
}