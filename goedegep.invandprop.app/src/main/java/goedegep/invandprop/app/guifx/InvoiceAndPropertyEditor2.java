package goedegep.invandprop.app.guifx;

import java.util.Objects;

import goedegep.invandprop.app.InvoicesAndPropertiesService;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanel;
import goedegep.jfx.editor.EditorTemplate;

/**
 * This class provides an editor for an invoice and the related property.
 * <p>
 * This editor is basically two editors in one: an invoice editor and a property editor. This because you often create them together.<br/>
 * It extends the {@code EditorTemplate} for the invoice part. For the property part there are separate methods, all with 'property' in the name.
 */
public class InvoiceAndPropertyEditor2 extends EditorTemplate<InvoiceAndProperty> {
  private static final String WINDOW_TITLE = "New invoice and property";
  
  /**
   * The {@code InvoicesAndPropertiesService}.
   */
  private InvoicesAndPropertiesService invoicesAndPropertiesService;
  
  
  /**
   * Factory method to obtain a new instance of an {@code InvoiceAndPropertyEditor}.
   * 
   * @param customization the GUI customization.
   * @param invoicesAndPropertiesService a service used for adding invoices and properties.
   * @return a newly created {@code InvoiceAndPropertyEditor}.
   */
  public static InvoiceAndPropertyEditor2 newInstance(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    Objects.requireNonNull(invoicesAndPropertiesService, "invoicesAndPropertiesService may not be null");
    
    InvoiceAndPropertyEditor2 invoiceAndPropertyEditor = new InvoiceAndPropertyEditor2(customization, invoicesAndPropertiesService);
    invoiceAndPropertyEditor.performInitialization();
     
    return invoiceAndPropertyEditor;
  }

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param invoicesAndPropertiesService a service used for adding invoices and properties.
   */
  private InvoiceAndPropertyEditor2(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    super(customization, WINDOW_TITLE, invoicesAndPropertiesService::addInvoiceAndPropertyToInvoicesAndPropertiesDatabase);
    
    this.invoicesAndPropertiesService = invoicesAndPropertiesService;
  }

  /**
   * {@inheritDoc}
   * 
   * In this case an InvoiceAndPropertyEditPanel is created and returned.
   */
  @Override
  protected EditPanel<InvoiceAndProperty> getMainEditPanel() {
    InvoiceAndPropertyEditPanel invoiceAndPropertyEditPanel = InvoiceAndPropertyEditPanel.newInstance(customization, invoicesAndPropertiesService);

    return invoiceAndPropertyEditPanel;
  }
}
