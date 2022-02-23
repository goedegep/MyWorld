
//Title:      Financien
//Version:    
//Copyright:  Copyright (c) 1999
//Author:     Peter Goedegebure
//Company:    Solvation
//Description:  StockDepot Window
package goedegep.app.finan.stocksapp;

//import java.awt.*;
//import java.util.Iterator;

import javax.swing.*;
//import goedegep.finan.stocks.StockDepot;
//import goedegep.finan.stocks.StockPosition;

public class StockDepotWindow extends JFrame {
  private static final long          serialVersionUID = 1L;
//  
//  String                      _windowTitle;
//  StockDepot                  _depot;
//
//  // Statusbar
//  JLabel statusBar = new JLabel();
//
//  // Layout: borderLayout, center is JScrollPane, with JTextArea
//  BorderLayout borderLayout = new BorderLayout();
//  JScrollPane  scrollPane   = new JScrollPane();
//  JTextArea    textArea     = new JTextArea();
//
//  //TitledBorder scrollPaneBorder;
//
//  //Construct the frame
//  public StockDepotWindow(String windowTitle, StockDepot depot) {
//    _windowTitle = windowTitle;
//    _depot = depot;
//
//    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
//    try {
//      jbInit();
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  //Component initialization
//  private void jbInit() throws Exception  {
//    // General window initialization
//    //scrollPaneBorder = new TitledBorder("");
//    this.getContentPane().setLayout(borderLayout);      // install the borderLayout
//    this.getContentPane().setBackground(Color.blue);
//    this.setSize(new Dimension(900, 400));              // set window size
//    this.setTitle(_windowTitle);                        // set window title
//    statusBar.setText(" ");                             // clear status bar text
//
//    // Text area
//    textArea.setLineWrap(true);
//    textArea.setWrapStyleWord(true);
//    textArea.setBackground(Color.blue);
//    textArea.setText(" ");
//    textArea.setForeground(Color.white);
//    textArea.setEditable(false);
//    scrollPane.getViewport().setBackground(Color.blue);
//    statusBar.setBackground(Color.blue);
//    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
//    //scrollPane.setBorder(scrollPaneBorder);
//    scrollPane.getViewport().add(textArea, null);
//
//    // Status bar
//    statusBar.setText(" ");
//    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
//
//    showPosition();
//  }
//
//
//  // List the transactions
//  void showPosition() {
//    String        output = new String();
//    boolean       first = true;
//
//    statusBar.setText(" ");
//
//    if (_depot != null) {
////      int nrOfPositions = _depot.numberOfPositions();
//      Iterator<StockPosition> iterator = _depot.iterator();
//      while (iterator.hasNext()) {
////      for (int currentPosition = 1; currentPosition <= nrOfPositions; currentPosition++) {
////        position = _depot.getPosition(currentPosition);
//        StockPosition position = iterator.next();
//        output = position.toString();
//
//        if (first) {
//          first = false;
//          textArea.setText(output);
//        } else {
//          textArea.append("\n" + output);
//        }
//
//        output = position.getHistoryAsString();
//        textArea.append("\n" + output);
//      }
//    } else {
//      statusBar.setText("Geen depot opgegeven");
//    }
//  }
}