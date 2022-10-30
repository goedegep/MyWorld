package goedegep.appgen.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;

import goedegep.appgen.MessageDialogType;
import goedegep.appgen.WindowUtil;
import goedegep.resources.ImageSize;

@SuppressWarnings("serial")
public class AppFrame extends JFrame implements Pageable, Printable {
  private static final Logger         LOGGER = Logger.getLogger(AppFrame.class.getName());
  
  private Customization       customization = null;
  private ComponentFactory    componentFactory = null;

  /**
   *
   * @param owner the owner (parent?) window of this Frame.
   * @param title the title of this Frame.
   */
  public AppFrame(String title, Customization customization, Dimension size) {
    super(title);
    
    if (customization == null) {
      throw new IllegalArgumentException("parameter customization is null, which isn't allowed");
    }
    this.customization = customization;
    componentFactory = customization.getComponentFactory();
    if (componentFactory == null) {
      throw new IllegalArgumentException("parameter customization has no ComponentFactory");
    }
    
    setIconImage(customization.getResources().getApplicationImage(ImageSize.SIZE_0));
    if (size != null) {
      setSize(size);
    }
  }
  
  public AppFrame(String title, Customization customization) {
    this(title, customization, null);
  }
  
  public Customization getCustomization() {
    return customization;
  }
  
//  public Look getLook() {
//    return customization.getLook();
//  }
  
  public AppResources getResources() {
    return customization.getResources();
  }
  
  // The 'The' in the name is there to avoid name conflict
  public ComponentFactory getTheComponentFactory() {
    return componentFactory;
  }
  
  public void showMessageDialog(MessageDialogType messageDialogType, String message) {
    showMessageDialog(messageDialogType, message, null);
  }
  
  public void showMessageDialog(MessageDialogType messageDialogType, String message, Image image) {
    JDialog dlg;
    dlg = MessageDialog.createMessageDialog(this, messageDialogType, image, message);
    WindowUtil.showDialogCenteredOnParent(this, dlg);
  }

  
  protected void print() {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPageable(this);
    boolean doPrint = job.printDialog();
    if (doPrint) {
      try {
        job.print();
      } catch (PrinterException e) {
        showMessageDialog(MessageDialogType.ERROR, "Het afdrukken is mislukt, melding: " + e.getMessage());
      }
    }
  }

  @Override
  public int print(Graphics g, PageFormat format, int page_index) throws PrinterException {
    LOGGER.fine("=> page_index: " + page_index);
    Container comp = getContentPane();

    if (page_index > 0) {
      LOGGER.severe("Beyond our single page: " + page_index);
      throw new PrinterException("Illegal page_index");
    }

    Graphics2D g2 = (Graphics2D) g;
    g2.translate(format.getImageableX(), format.getImageableY());   // Take care of page margins.
    
    // Scale to fit on the page
    double formatWidth =  format.getImageableWidth();
    double compWidth = comp.getSize().width;
    double horScale = formatWidth / compWidth;
    double formatHeight =  format.getImageableHeight();
    double compHeight = comp.getSize().height;
    double vertScale = formatHeight / compHeight;
    double scale = Math.min(horScale, vertScale);
    g2.scale(scale, scale);

    comp.printAll(g2);

    return Printable.PAGE_EXISTS;
  }

  @Override
  public int getNumberOfPages() {
    return 1;
  }

  @Override
  public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
    PageFormat pageFormat = new PageFormat();
    Container comp = getContentPane();
    if (comp.getSize().width > comp.getSize().height) {
      pageFormat.setOrientation(PageFormat.LANDSCAPE);
    } else {
      pageFormat.setOrientation(PageFormat.PORTRAIT);
    }
    Paper paper = new Paper();
    double margin = 20;
    paper.setImageableArea(margin, margin, paper.getWidth() - 2 * margin, paper.getHeight() - 2 * margin);
    
    pageFormat.setPaper(paper);
    
    return pageFormat;
  }

  @Override
  public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
    return this;
  }
}
