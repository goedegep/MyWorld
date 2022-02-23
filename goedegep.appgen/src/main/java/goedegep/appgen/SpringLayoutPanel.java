package goedegep.appgen;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class SpringLayoutPanel extends JPanel {
  private SpringLayout layout = new SpringLayout();

  public SpringLayoutPanel() {
    super();
    setLayout(layout);
  }

  public void addComponentUsingOffsets(JComponent component, int vertOffset, int horOffset) {
    add(component);

    layout.putConstraint(SpringLayout.WEST, component, horOffset, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, component, vertOffset, SpringLayout.NORTH, this);
  }
}
