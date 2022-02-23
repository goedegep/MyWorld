package goedegep.util.xtree.demo;


import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import goedegep.util.xtree.mutable.MutableXTree;

@SuppressWarnings("serial")
public class XTreeDialog extends JFrame {
  private MutableXTree         xTree = null;


  //Construct the frame
  public XTreeDialog(MutableXTree tree) {
    this("XTree Content", tree);
  }

  public XTreeDialog(String windowTitle, MutableXTree xTree) {
    super(windowTitle);
    this.xTree = xTree;

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void init() throws Exception  {
    Container contentPane = getContentPane();

    // General window initialization
    contentPane.setLayout(new BorderLayout());
    setSize(new Dimension(500, 600));              // set window size
  
    // Data Tree area
    // Create the scroll pane and add the tree to it.

    JXTree dataTree = new JXTree();
    dataTree.setRoot(xTree);

    JScrollPane treeView = new JScrollPane(dataTree);
    Dimension treeSize = new Dimension(600, 800);
    treeView.setMinimumSize(treeSize);
    treeView.setPreferredSize(treeSize);
    contentPane.add(treeView, BorderLayout.NORTH);

    // Buttons
    JPanel buttonPanel = new JPanel();
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancel();
      }
    });
    buttonPanel.add(cancelButton, BorderLayout.EAST);
    contentPane.add(buttonPanel, BorderLayout.CENTER);
  }

  private void cancel() {
    this.dispose();
  }
}
