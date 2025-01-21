package goedegep.invandprop.app;

import java.io.IOException;

import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.emf.EMFResource;

public class InvoicesAndPropertiesService {
  
  private EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource;
  private InvoicesAndProperties invoicesAndProperties;

  public InvoicesAndPropertiesService(EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource) {
    this.invoicesAndPropertiesResource = invoicesAndPropertiesResource;
    invoicesAndProperties = invoicesAndPropertiesResource.getEObject();
  }

  public EMFResource<InvoicesAndProperties> getInvoicesAndPropertiesResource() {
    return invoicesAndPropertiesResource;
  }

  
  /**
   * Add a new {@code Invoice} to the database.
   * 
   * @param invoice the {@code Invoice} to be added.
   */
  public void addInvoiceToInvoicesAndPropertiesDatabase(Invoice invoice) {
    invoicesAndProperties.getInvoices().getInvoices().add(invoice);
  }
  
  public Result saveInvoicesAndProperties() {
    Result result = new Result();
    
    try {
      invoicesAndPropertiesResource.save();
      result.setResultType(ResultType.OK);
      result.setMessage("Invoices and properties saved to '" + invoicesAndPropertiesResource.getFileName() + "'");
    } catch (IOException e) {        
      result.setResultType(ResultType.FAILED);
      result.setMessage("System error message: "  + e.getMessage());
    }
    
    return result;
  }
}
