package goedegep.app.finan.finanapp;

import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.MessageDialogType;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;

@SuppressWarnings("serial")
public class CustomizationExampleFrame extends AppFrame {
  public CustomizationExampleFrame(Window owner, String customizationName, Customization customization) {
    super("Uiterlijk voorbeeld scherm: " + customizationName, customization);
    createContents();
    pack();
  }

  private void createContents() {
    ComponentFactory componentFactory = getCustomization().getComponentFactory();
    Container contentPane = getContentPane();
    
    JButton button = componentFactory.createButton("Toon waarschuwing", "Toon waarschuwings popup");
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        showMessageDialog(MessageDialogType.WARNING, "Dit is een waarschuwing met zomaar wat tekst");
        
      }
      
    });
    contentPane.add(button);
    
    
    // TODO Auto-generated method stub
    
  }
}
