package goedegep.travels.app.guifx;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.PageSizeUnits;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import goedegep.travels.app.logic.VacationToHtmlConverter;
import goedegep.vacations.model.Vacation;
import javafx.concurrent.Task;

/**
 * This class is a Task that generates a PDF document for a given Vacation object.
 * The task generates HTML from the Vacation object and then converts that HTML to a PDF.
 */
public class VacationToPDFTask extends Task<PDDocument> {
  
  /**
   * The in-memory file system used to generate the PDF file.
   */
  private FileSystem imfs;
  
  /**
   * The converter to generate HTML for a Vacation object.
   */
  private VacationToHtmlConverter vacationToHtmlConverter;
  
  /**
   * The Vacation object for which the PDF will be generated.
   */
  private final Vacation vacation;
  
  /**
   * The temporary directory path where the PDF file will be created.
   */
  Path tmpPdfDirPath;
  
  /**
   * The path to the generated PDF file.
   */
  private Path pdfFilePath;

  /**
   * Constructor for the VacationToPDFTask.
   * <p>
   * A Task cannot be reused, therefore we pass the reusable items via the constructor.
   *
   * @param vacationToHtmlConverter The converter to generate HTML from a Vacation object.
   * @param imfs The in-memory file system used to create the PDF file.
   * @param vacation The Vacation object for which the PDF will be generated.
   */
  public VacationToPDFTask(VacationToHtmlConverter vacationToHtmlConverter, FileSystem imfs, Vacation vacation) {
    this.vacationToHtmlConverter = vacationToHtmlConverter;
    this.imfs = imfs;
    this.vacation = vacation;    
  }

  @Override
  protected PDDocument call() throws Exception {
    // Generate HTML for the vacation.
    String htmlText = vacationToHtmlConverter.vacationToHtml(vacation);

    tmpPdfDirPath = imfs.getPath("/tmpPdf");
    try {
      Files.createDirectory(tmpPdfDirPath);
      
      // Generate a PDF file for the HTML String. This can only be written to a file.
      pdfFilePath = tmpPdfDirPath.resolve("Vacation.pdf");
      try (OutputStream os = Files.newOutputStream(pdfFilePath)) {
        PdfRendererBuilder builder = new PdfRendererBuilder()
            .useFastMode()
            .withHtmlContent(htmlText, null)
            .useDefaultPageSize(210, 297, PageSizeUnits.MM)
            .toStream(os);
        builder.run();
        os.close();
      }

      // jimfs only works with files, loadPDF only works with a file, or a byte array.
      // So we first read the PDF file into a byte array and use that to load the document.
      byte[] data = Files.readAllBytes(pdfFilePath);
      PDDocument document = Loader.loadPDF(data);
      
      updateValue(document);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
  
  /**
   * Returns the path to the generated PDF file.
   *
   * @return The path to the PDF file.
   */
  public Path getPDFFilePath() {
    return pdfFilePath;
  }
  
  /**
   * Cleans up the temporary file and directory created during the task.
   * This method should be called when the task is being cancelled.
   */
  public void cleanup() {
    // Clean up the temporary file and directory if the task is cancelled.
    try {
        Files.delete(pdfFilePath);
        Files.delete(tmpPdfDirPath);
    } catch (IOException e) {
      e.printStackTrace();
    }    
  }
  
}
