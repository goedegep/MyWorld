package goedegep.appgen.swing;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import goedegep.appgen.MessageDialogType;
import goedegep.resources.ImageSize;

@SuppressWarnings("serial")
public class MessageDialog extends AppDialog implements ActionListener {
  
  private JButton       okButton;

  public static MessageDialog createMessageDialog(AppFrame owner, MessageDialogType messageDialogType, Image image, String message) {
    if (image == null) {
      switch (messageDialogType) {
      case ERROR:
        image = owner.getCustomization().getResources().getErrorImage(ImageSize.SIZE_3);
        break;
        
      case WARNING:
        image = owner.getCustomization().getResources().getAttentionImage(ImageSize.SIZE_3);
        break;
      }
    }
    
    return new MessageDialog(owner,  messageDialogType.getTitle(), image, message);
  }
  
  public MessageDialog(AppFrame owner, String title, Image image, String message) {
    super(owner, title);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);

    ComponentFactory componentFactory = getTheComponentFactory();
    
    JPanel mainPanel = componentFactory.createPanel(-1, -1, false);
    JPanel panel = createImagePanel(image);

    mainPanel.add(panel, BorderLayout.WEST);

    // text part
    JTextArea textArea = componentFactory.createTextArea(message, true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    textArea.setBorder(new EmptyBorder(20, 20, 20, 20));
    mainPanel.add(scrollPane, BorderLayout.CENTER);

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



  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == okButton) {
      dispose();
    }
  }
}
