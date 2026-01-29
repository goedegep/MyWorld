package goedegep.rolodex.logic;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.PageSizeUnits;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import goedegep.jfx.CustomizationFx;
import goedegep.rolodex.model.Rolodex;

public class AddressBookExporter {
  private CustomizationFx customization;
  private Rolodex rolodex;
  
  public AddressBookExporter(CustomizationFx customization, Rolodex rolodex) {
    this.customization = customization;
    this.rolodex = rolodex;
  }
  
  public void exportAddressBookToPdf(Path path) {
    AddressBookToHtmlConverter addressBookToHtmlConverter = new AddressBookToHtmlConverter(customization, rolodex);
    String htmlText = addressBookToHtmlConverter.addressBookToHtml();
    
    try(OutputStream os = Files.newOutputStream(path)) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.useFastMode();
      builder.useDefaultPageSize(300, 300, PageSizeUnits.MM);
      builder.withHtmlContent(htmlText, null);
      builder.toStream(os);
      builder.run();
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
