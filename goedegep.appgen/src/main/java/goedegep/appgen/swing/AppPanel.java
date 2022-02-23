package goedegep.appgen.swing;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AppPanel extends JPanel {
  private AppFrame                owner;
  
  public AppPanel(AppFrame owner, int width, int height) {
    this(owner, new Dimension(width, height));
  }
  
  public AppPanel(AppFrame owner, Dimension dimension) {
    this.owner = owner;
    
//    setBackground(getCustomization().getLook().getPanelBackgroundColor());
    if (dimension != null) {
      setMinimumSize(dimension);
      setPreferredSize(dimension);
    }
  }

  public Customization getCustomization() {
    return owner.getCustomization();
  }
  
//  public Look getLook() {
//    return owner.getLook();
//  }
  
  // The 'The' in the name is there to avoid name conflict
  public ComponentFactory getTheComponentFactory() {
    return owner.getTheComponentFactory();
  }
 
  public AppFrame getOwner() {
    return owner;
  }
}
