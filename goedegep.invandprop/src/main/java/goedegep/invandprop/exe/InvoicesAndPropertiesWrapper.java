package goedegep.invandprop.exe;


/*
 * This class is the entyry point for the Invoices and Properties executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class InvoicesAndPropertiesWrapper {
  public static void main(String[] args) {
    
    InvoicesAndPropertiesApplication.main(args);
  }
}
