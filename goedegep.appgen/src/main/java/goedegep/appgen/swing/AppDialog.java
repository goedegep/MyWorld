package goedegep.appgen.swing;

import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import goedegep.appgen.ImagePanel;

@SuppressWarnings("serial")
public abstract class AppDialog extends JDialog  {
  private AppFrame   owner;

  /**
   *
   * @param owner the owner (parent?) window of this dialog.
   * @param title the title of this dialog.
   */
  public AppDialog(AppFrame owner, String title) {
    super(owner, title);
    
    this.owner = owner;
    
//    setBackground(getCustomization().getLook().getLabelBackgroundColor());
  }

  public Customization getCustomization() {
    if (owner != null) {
      return owner.getCustomization();
    } else {
      return DefaultCustomization.getInstance();
    }
  }
  
//  public Look getLook() {
//    return owner.getLook();
//  }
  
  // The 'The' in the name is there to avoid name conflict
  public ComponentFactory getTheComponentFactory() {
    if (owner != null) {
      return owner.getTheComponentFactory();
    } else {
      return getCustomization().getComponentFactory();
    }
  }
  
  public AppFrame getOwner() {
    return owner;
  }
  
  public JPanel createImagePanel(Image image) {
    JPanel panel = getTheComponentFactory().createPanel(-1, -1, false);
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
    ImagePanel imagePanel = new ImagePanel(image, true);
    panel.add(imagePanel);
    
    JPanel fillPanel = getTheComponentFactory().createPanel(-1, -1, false);
    panel.add(fillPanel);
    
    return panel;
  }
}
