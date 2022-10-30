package goedegep.appgen.swing;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import goedegep.resources.ImageSize;

@SuppressWarnings("serial")
public class OptionDialog extends AppDialog implements ActionListener {
  private JButton       okButton;
  private ButtonGroup   buttonGroup;
  
  public OptionDialog(AppFrame owner, String title, Image image, String message, String[] options, String selectedOption) {
    super(owner, title);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);

    ComponentFactory componentFactory;
    if (owner != null) {
      componentFactory = getTheComponentFactory();
    } else {
      componentFactory = DefaultCustomization.getInstance().getComponentFactory();
    }

    JPanel mainPanel = componentFactory.createPanel(-1, -1, false);
    if (image == null) {
      image = DefaultCustomization.getInstance().getResources().getApplicationImage(ImageSize.SIZE_1);
    }
    JPanel panel = createImagePanel(image);  // "Icon" at Top Left (West)
    mainPanel.add(panel, BorderLayout.WEST);

    /*
     *  Selection panel
     */
    panel = componentFactory.createPanel(-1, -1, false);
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    
    // text part
    JTextArea textArea = componentFactory.createTextArea(message, true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    textArea.setBorder(new EmptyBorder(20, 20, 20, 20));
    panel.add(scrollPane);
    
    // selection radio buttons
    JPanel buttonPanel = componentFactory.createPanel(-1, -1, false);
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
    
    buttonGroup = new ButtonGroup();
//    for (String option: options.keySet()) {
    for (String option: options) {
      boolean selected;
      
      if (option.equals(selectedOption)) {
        selected = true;
      } else {
        selected = false;
      }
      
      JRadioButton radioButton = componentFactory.createRadioButton(option, selected);
      radioButton.setActionCommand(option);
      buttonGroup.add(radioButton);
      buttonPanel.add(radioButton);
    }
    panel.add(buttonPanel);
    mainPanel.add(panel, BorderLayout.CENTER);
    

    // "OK" Button
    panel = componentFactory.createPanel(40, 35, false);
    panel.setBorder(new EmptyBorder(5, 100, 5, 100));
    okButton = componentFactory.createButton("OK", "verlaat dit window");
    okButton.addActionListener(this);
    panel.add(okButton, BorderLayout.CENTER);
    mainPanel.add(panel, BorderLayout.SOUTH);
    
    Dimension windowSize = new Dimension(500, 300);
    this.setMinimumSize(windowSize);

    getContentPane().add(mainPanel, null);
    
    setModal(true);
    pack();
  }
  
  public String getSelectedOption() {
    return buttonGroup.getSelection().getActionCommand();
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == okButton) {
      dispose();
    }
  }
}
