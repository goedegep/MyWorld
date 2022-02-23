package goedegep.vacations.app.guifx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.vacations.app.logic.VacationToHtmlConverter;
import goedegep.vacations.model.Vacation;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

/**
 * This class provides a window to print a <code>Vacation</code>.
 * <p>
 * On the left, the window shows the document representation of the vacation.<br/>
 * The right side provides controls:
 * 
 * 
 */
public class PrintWindow extends JfxStage {
  /*
   * Strategy:
   * When the class is initialized, an in-memory files system is created.
   * In the constructor an HTML String is created for the vacation, for which then a PDF file is generated in the in-memory file system.
   * 
   */
  private static final Logger LOGGER = Logger.getLogger(VacationsWindow.class.getName());
  private static final String WINDOW_TITLE = "Print a vacation";

  private static Path tmpPdfDirPath;
  
  private CustomizationFx customization;
//  private Label status = new Label();
  private POIIcons poiIcons;
  private VacationToHtmlConverter vacationToHtmlConverter;
  private Path pdfFilePath;
  private int currentPageIndex = 0;
  private PDDocument document;
  private PDFRenderer renderer;
  private ImageView pageView;
  private PrinterJob printerJob;

  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param vacation the <code>Vacation</code> to be printed
   * @throws Exception if something goes wrong
   */
  public PrintWindow(CustomizationFx customization, Vacation vacation) throws Exception {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    
    // Generate HTML for the vacation.
    poiIcons = new POIIcons("POIIconResourceInfo.xmi");
    vacationToHtmlConverter = new VacationToHtmlConverter(poiIcons);
    String htmlText = vacationToHtmlConverter.vacationToHtml(vacation);
    LOGGER.severe(htmlText);
        
    // Generate a PDF file for the HTML String
    pdfFilePath = tmpPdfDirPath.resolve("Vacation.pdf");
    OutputStream os = Files.newOutputStream(pdfFilePath);
    PdfRendererBuilder builder = new PdfRendererBuilder();
    builder.useFastMode();
    builder.withHtmlContent(htmlText, null);
    builder.toStream(os);
    builder.run();
    os.close();

    createGui();
    
    show();
  }
  
  static {
    // Create an in-memory file system
    FileSystem imfs = Jimfs.newFileSystem(Configuration.unix());
    tmpPdfDirPath = imfs.getPath("/tmpPdf");
    try {
      Files.createDirectory(tmpPdfDirPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Create the GUI
   * <p>
   * Left side is PDF page preview.
   * Right side is control panel.
   * - print button
   */
  private void createGui() {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    BorderPane mainPane = new BorderPane();
        
    // Center (is left): PDF page preview
    try {
      InputStream input = Files.newInputStream(pdfFilePath);
      document = PDDocument.load(input);
      input.close();
      renderer = new PDFRenderer(document);
      pageView = new ImageView();
      mainPane.setCenter(pageView);
      displayPage();
    } catch (IOException e) {
      e.printStackTrace();
      Label label = componentFactory.createLabel("Document could not be created");
      mainPane.setCenter(label);
    }
    
    // Right: control panel
    VBox controlPanel = componentFactory.createVBox(16);
    
    Button printdialogButton = componentFactory.createButton("print dialog", "press to show the print dialog");
    printdialogButton.setOnAction((e) -> showPrintDialog());
    Button printButton = componentFactory.createButton("print", "press to print the document shown on the left");
    printButton.setOnAction(this::printDocument);
    controlPanel.getChildren().addAll(printdialogButton, printButton);
    
    HBox pageControlBox = componentFactory.createHBox(16);
    Button firstPageButton = componentFactory.createButton("<<", "go to the first page of the document");
    firstPageButton.setOnAction((e) -> {
      currentPageIndex = 0;
      displayPage();
    });
    Button previousPageButton = componentFactory.createButton("<", "go to the previous page of the document");
    previousPageButton.setOnAction((e) -> {
      if (currentPageIndex > 0) {
        currentPageIndex--;
        displayPage();
      }
    });
    Button nextPageButton = componentFactory.createButton(">", "go to the next page of the document");
    nextPageButton.setOnAction((e) -> {
      if (currentPageIndex < document.getNumberOfPages() - 1) {
        currentPageIndex++;
        displayPage();
      }
    });
    Button lastPageButton = componentFactory.createButton(">>", "go to the last page of the document");
    lastPageButton.setOnAction((e) -> {
      currentPageIndex = document.getNumberOfPages() - 1;
      displayPage();
    });
    pageControlBox.getChildren().addAll(firstPageButton, previousPageButton, nextPageButton, lastPageButton);
    controlPanel.getChildren().add(pageControlBox);
    
    mainPane.setRight(controlPanel);
    
    setScene(new Scene(mainPane, 800, 900));
  }
  
  /*
   * Display the current page.
   */
  private void displayPage() {
    BufferedImage pageImage;
    try {
      pageImage = renderer.renderImage(currentPageIndex);
      Image image = SwingFXUtils.toFXImage(pageImage, null);
      pageView.setImage(image);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void showPrintDialog() {
    PrinterJob printerJob = getPrinterJob();
    
    if (printerJob.showPrintDialog(this)) {
      printDocument(null);
    }
  }
  
  private void printDocument(ActionEvent actionEvent) {
    PrinterJob printerJob = getPrinterJob();    

    try {
      for (int index = 0; index < document.getNumberOfPages(); index++) {
        BufferedImage pageImage = renderer.renderImage(index);
        Image image = SwingFXUtils.toFXImage(pageImage, null);
        ImageView imageView = new ImageView(image);
        Scale scale = new Scale(0.85, 0.85);
        imageView.getTransforms().add(scale);
        printerJob.printPage(imageView);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private PrinterJob getPrinterJob() {
    if (printerJob == null) {
      printerJob = PrinterJob.createPrinterJob();
    }
    
    return printerJob;
  }
}
