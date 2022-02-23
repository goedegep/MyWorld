
//Title:      Financien
//Version:
//Copyright:  Copyright (c) 1999
//Author:     Peter Goedegebure
//Company:    Solvation
//Description:Postbankfonds Transactions Window
package goedegep.app.finan.postbankapp;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.postbank.pbfonds.PbFonds;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PbFondsTransactionsWindow extends AppFrame {
  
  private PbFonds fonds;

  // Layout: borderLayout,
  //         North is header (JLabel),
  //         Center contains transaction lines (JScrollPane, with JTextArea),
  //         South is statusbar (JLabel)
  private BorderLayout borderLayout = new BorderLayout();

  // Statusbar
  private JLabel statusBar = new JLabel();


  //Construct the frame
  public PbFondsTransactionsWindow(Customization customization, String windowTitle, PbFonds fonds) {
    super(windowTitle, customization, null);
    this.fonds = fonds;

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    // General window initialization
    this.getContentPane().setLayout(borderLayout);      // install the borderLayout
    this.getContentPane().setBackground(Color.blue);
    this.setSize(new Dimension(1000, 408));             // set window size

    getContentPane().add(new PbFondsTransactionsTable(this, fonds), BorderLayout.CENTER);

    // Status bar
    statusBar.setBackground(Color.blue);
    statusBar.setText(" ");   // clear status bar text
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }
}