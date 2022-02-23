package goedegep.appgen.swing;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;

@SuppressWarnings("serial")
public class BestandReferentieDialog extends AppDialog {
  private static final String       EMPTY_TITLE = "<voer een titel in>";
  private static final TypesFactory typesFactory = TypesFactory.eINSTANCE;
  
  
  private static final int        FIRST_COLUMN = 23;
  private static final int        SECOND_COLUMN = 90;
  private static final int        THIRD_COLUMN = 400;
  private static final int        TOP_MARGIN = 12;
  private static final int        ROW_DISTANCE = 24;
  
  private ComponentFactory   componentFactory;
  private FileReference  bestandReferentie;
  private File               baseDir;          // als een absoluut pad met baseDir begint, wordt het als een relatief pad opgeslagen.
  private URL                url;              // De bestandreferentie is deze url,
  private File               file;             // of deze file. De andere waarde is altijd null.
  private String             title;
  
  private JTextField         fileNameField;
  private JTextField         titleField;
  private final JFileChooser fileChooser = new JFileChooser();
  private JButton            okButton;
  private JLabel             statusBar = getTheComponentFactory().createLabel("", SwingConstants.LEFT);

  public BestandReferentieDialog(AppFrame owner, String windowTitle, FileReference bestandReferentie, String baseDir) {
    super(owner, windowTitle);
    
    this.bestandReferentie = bestandReferentie;
    componentFactory = getTheComponentFactory();
    
    // als er geen baseDir opgegeven is, wordt de 'current directory' als baseDir genomen.
    if (baseDir != null) {
      this.baseDir = new File(baseDir);
    } else {
      this.baseDir = new File(System.getProperty("user.dir"));
    }
    
    if (bestandReferentie.getFile() != null) {
      try {
        url = new URL(bestandReferentie.getFile());
      } catch (MalformedURLException e) {
        url = null;
        file = new File(bestandReferentie.getFile());
      }
    } else {
      url = null;
      file = null;
    }
    
    if (bestandReferentie.getTitle() != null) {
      title = bestandReferentie.getTitle();
    } else {
      title = EMPTY_TITLE;
    }
    
    init();
  }

  private void init() {
    setSize(new Dimension(800, 400));
    JPanel contentPane = (JPanel) getContentPane();

    /*
     * Main layout:
     * NORTH = textArea with help text
     * CENTER = centerPanel with
     *   NORTH = fields
     *   SOUTH = panel with 'OK' and 'annuleren' buttons.
     * SOUTH = status bar.
     */

    //
    // Main layout: NORTH
    //     Help text.
    JPanel ta = componentFactory.createHelpTextArea(
        "Een <b>Bestand Referentie</b> bestaat uit een bestandsnaam en een optionele titel." +
        " De titel is een korte omschrijving van het bestand.<br/>" +
        "Als er geen titel opgegeven is, wordt de bestandnaam getoond, anders de titel."
    );
    contentPane.add(ta, BorderLayout.NORTH);

    // Main layout: CENTER
    JPanel centerPanel = componentFactory.createPanel(-1, -1, false);
    centerPanel.add(createInputFieldsPanel(), BorderLayout.NORTH);    
    centerPanel.add(createButtonPanel(), BorderLayout.SOUTH);
    contentPane.add(centerPanel, BorderLayout.CENTER);


    // Main layout: SOUTH
    //     status panel
    JPanel statusPanel = componentFactory.createPanel(500, 22, true);
    statusPanel.add(statusBar, BorderLayout.CENTER);
//    statusPanel.setBackground(getLook().getTextFieldBackGroundColor());
    contentPane.add(statusPanel, BorderLayout.SOUTH);
    
    handleFileInfoChanged();
    
    this.pack();
  }
  
  private JPanel createInputFieldsPanel() {
    JPanel panel = componentFactory.createPanel(800, 200, true);
    SpringLayout layout = new SpringLayout();
    panel.setLayout(layout);
    int verticalOffset = TOP_MARGIN;
    
    // Line 1, column 1: "Bestand label"
    JLabel label = componentFactory.createLabel("Bestand:", SwingConstants.LEFT);
    panel.add(label);
    layout.putConstraint(SpringLayout.WEST, label,
        FIRST_COLUMN,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label,
        verticalOffset,
        SpringLayout.NORTH, panel);

    // Line 1, column 2: "Bestandnaam textfield"
    fileNameField = componentFactory.createTextField(20, "vul een bestandnaam in");
    updateFileNameField();
    fileNameField.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
//        System.out.println("changedUpdate.");
        handleFileNameFieldChanged();
      }
      
      public void removeUpdate(DocumentEvent e) {
//        System.out.println("removeUpdate.");
        handleFileNameFieldChanged();
      }
      
      public void insertUpdate(DocumentEvent e) {
//        System.out.println("insertUpdate.");
        handleFileNameFieldChanged();
      }
      
    });
    panel.add(fileNameField);
    layout.putConstraint(SpringLayout.WEST, fileNameField,
        SECOND_COLUMN,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, fileNameField,
        verticalOffset,
        SpringLayout.NORTH, panel);
    
    // Line 1, column 3: Browse button
    updateFileChooser();
    JButton button = componentFactory.createButton("Bladeren", "Bestand kiezer openen");
    button.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        launchFileChooser();
      }
    });
    panel.add(button);
    layout.putConstraint(SpringLayout.WEST, button,
        THIRD_COLUMN,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, button,
        verticalOffset - 3,
        SpringLayout.NORTH, panel);
    
    verticalOffset += ROW_DISTANCE;
    
    // Line 2, column 1: Titel label
    label = componentFactory.createLabel("Titel:", SwingConstants.LEFT);
    panel.add(label);
    layout.putConstraint(SpringLayout.WEST, label,
        FIRST_COLUMN,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label,
        verticalOffset,
        SpringLayout.NORTH, panel);

    // Line 2, column 1: Titel textfield
    titleField = componentFactory.createTextField(20, "voer korte omschrijving in");
    if (title != null) {
      titleField.setText(title);
    }
    panel.add(titleField);
    layout.putConstraint(SpringLayout.WEST, titleField,
        SECOND_COLUMN,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, titleField,
        verticalOffset,
        SpringLayout.NORTH, panel);
    
    return panel;
  }
  
  private JPanel createButtonPanel() {
    JPanel buttonPanel;
    JPanel panel = componentFactory.createPanel(-1, -1, false);

    // Create OK button.
    buttonPanel = componentFactory.createPanel(-1, -1, false);
    okButton = componentFactory.createButton("OK", "Nieuwe waarde gebruiken");
    okButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        ok();        
      }
      
    });

    buttonPanel.add(okButton);
    getRootPane().setDefaultButton(okButton);
    panel.add(buttonPanel, BorderLayout.WEST);

    // Create Cancel button.
    buttonPanel = componentFactory.createPanel(-1, -1, false);
    JButton cancelButton = componentFactory.createButton("Annuleren", "Niets wijzigen");
    cancelButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        cancel();
      }
      
    });
    buttonPanel.add(cancelButton);
    panel.add(buttonPanel, BorderLayout.EAST);

    return panel;
  }
  
  private void updateFileNameField() {
    if (url != null) {
      fileNameField.setText(url.toExternalForm());
    } else if (file != null) {
      fileNameField.setText(getFileNameToStore(file.getAbsolutePath()));
    }
  }
  
  private void updateFileChooser() {
    if (file != null) {
      fileChooser.setSelectedFile(file);
    } else {
      fileChooser.setCurrentDirectory(baseDir);
    }
  }

  private void launchFileChooser() {
    //   System.out.println("launchFileChooser =>");
    int returnVal = fileChooser.showOpenDialog(this);
    //    System.out.println("fileChooser returned: " + returnVal);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      url = null;
      file = fileChooser.getSelectedFile();
      //        System.out.println("fileChooser APPROVE_OPTION, file: " + file.getPath() + ", " + file.getAbsolutePath());

      handleFileInfoChanged();

      if (okButton.isEnabled()) {
        updateFileNameField();
      }
    }

    //    System.out.println("launchFileChooser <=");
  }
  
  private void handleFileNameFieldChanged() {
//    System.out.println("handleFileNameFieldChanged.");
    try {
      url = new URL(fileNameField.getText());
      System.out.println("url OK: " + url.toExternalForm());
    } catch (MalformedURLException e) {
      System.out.println("url not OK");
      File checkFile = new File(fileNameField.getText());
      if (checkFile.isAbsolute()) {
//        System.out.println("handleFileNameFieldChanged: absolute path");
        file = new File(fileNameField.getText());
      } else {
//        System.out.println("handleFileNameFieldChanged: relative path");
        file = new File(baseDir, fileNameField.getText());
      }
    }   
    
    handleFileInfoChanged();
    
    if (okButton.isEnabled()) {
      updateFileChooser();
    }
  }

  private void handleFileInfoChanged() {
//    System.out.println("handleFileInfoChanged =>");
    if (url != null) {
      statusBar.setText("huidige URL: " + url.toExternalForm());
      okButton.setEnabled(true);
    } else if ((file != null)  &&  file.exists()) {
      if (file.canRead()) {
//        System.out.println("handleFileInfoChanged => Existing, readable file");
        statusBar.setText("huidig pad: " + getFileNameToStore(file.getAbsolutePath()));
        okButton.setEnabled(true);
      } else {
//        System.out.println("handleFileInfoChanged => Existing, non-readable file");
        statusBar.setText("Het gekozen bestand is niet leesbaar");
        okButton.setEnabled(false);
      }
    } else {
//      System.out.println("handleFileInfoChanged => non-Existing file");
      statusBar.setText("Geen bestaand bestand gekozen");
      okButton.setEnabled(false);
    }
//    System.out.println("handleFileInfoChanged <=");
  }

  private String getFileNameToStore(String fileName) {
    if ((fileName.length() > baseDir.getAbsolutePath().length() + 1)  &&
        fileName.startsWith(baseDir.getAbsolutePath())  &&
        (fileName.charAt(baseDir.getAbsolutePath().length()) == '\\')) {
      fileName = fileName.substring(baseDir.getAbsolutePath().length() + 1);
    }
    
    return fileName;
  }

  private void cancel() {
    this.dispose();
  }

  private void ok() {
    if (url != null) {
      bestandReferentie.setFile(url.toExternalForm());
    } else {
      bestandReferentie.setFile(getFileNameToStore(file.getPath()));
    }
    if (titleField.getText().equals(EMPTY_TITLE)) {
      bestandReferentie.setTitle(null);
    } else {
      bestandReferentie.setTitle(titleField.getText());
    }
    this.dispose();
  }

  public static void main(String[] args) {
    AppFrame parent = new AppFrame("Parent frame", DefaultCustomization.getInstance(), null);
    parent.setPreferredSize(new Dimension(600, 400));
    parent.setLocation(200, 150);
    parent.pack();
    parent.setVisible(true);
    FileReference bestandReferentie = typesFactory.createFileReference();
    bestandReferentie.setFile("D:\\Peter\\ApplicationData\\Finan Test\\Pictures\\TV Philips 28ml8976.jpg");
    BestandReferentieDialog dlg = new BestandReferentieDialog(parent, "Bestand Referentie", bestandReferentie, null);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = parent.getSize();
    Point loc = parent.getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.setVisible(true);
    System.out.println("Bestand referentie: " + bestandReferentie.getFile() + ", " + bestandReferentie.getTitle());
  }
}
