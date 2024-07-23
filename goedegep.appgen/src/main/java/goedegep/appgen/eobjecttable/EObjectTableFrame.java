package goedegep.appgen.eobjecttable;


import goedegep.appgen.swing.Customization;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This class is an AppFrame, with an EObjectTable and controls for that table.
 * <p>
 * As an AppFrame is a JFrame, it has a BorderLayout.
 * The EObjectTable is placed at the CENTER and the controls fill the NORTH. This means that a class which extends this class can fill in the SOUTH.
 * <p>
 * The Frame can be used in 2 ways:
 * <ul>
 * <li>
 * First calling the constructor with only the Frame related parameters, and then call init() with the table information.<br/>
 * This is the way to go if the table information isn't known when the Frame has to be constructed.
 * </li>
 * <li>
 * Call the constructor with the Frame related and table related parameters.
 * </li>
 * </ul>
 *
 * @param <T> The type of the Objects in the main table.
 */
@SuppressWarnings("serial")
public class EObjectTableFrame<T extends EObject> extends AppFrame {
  private static final Logger         LOGGER = Logger.getLogger(EObjectTableFrame.class.getName());
  
  private static final int        COL1_ROW1 = 23;
  private static final int        COL2_ROW1 = 70;
  private static final int        COL3_ROW1 = 160;
  private static final int        COL4_ROW1 = 260;
  private static final int        COL5_ROW1 = 410;
  private static final int        COL6_ROW1 = 655;
  private static final int        COL7_ROW1 = 700;
  
  private static final int        COL1_ROW2 = 23;
  private static final int        COL2_ROW2 = 130;
  private static final int        COL3_ROW2 = 380;
  private static final int        COL4_ROW2 = 420;
  
  private static final int        TOP_MARGIN = 12;
  private static final int        ROW_DISTANCE = 30;
  
  private EObjectTableDescriptor<T> objectTableDescriptor;
  private EObjectJTable<T>           eObjectTable;
  
  private JTextField                searchTextField;
  private JComboBox<String>         attributeComboBox;
  private JTextField                filterTextField;
  private JComboBox<String>         filterAttributeComboBox;

  
  /**
   * Constructor with only Frame related parameters.
   * <p>
   * This constructor can be used if the table information isn't known yet.
   * If this constructor is used, later on the {@link #init} method has to be called.
   * 
   * @param windowTitle the window title.
   * @param customization the customization of the window.
   * @param size the size of the window.
   */
  public EObjectTableFrame(String windowTitle, Customization customization, Dimension size) {
    super(windowTitle, customization, size);
  }
  
  /**
   * 
   * @param windowTitle
   * @param customization
   * @param size
   * @param eClass
   * @param objectTableDescriptor
   * @param containingObject
   * @param objects
   * @param objectSubTableDescriptors
   */
  public EObjectTableFrame(String windowTitle, Customization customization, Dimension size,
      EClass eClass, EObjectTableDescriptor<T> objectTableDescriptor,
      EObject containingObject, EList<T> objects, EObjectSubTableDescriptor... objectSubTableDescriptors) {
    this(windowTitle, customization, size);
    init(eClass, objectTableDescriptor, containingObject, objects, objectSubTableDescriptors);
  }
 
  /**
   * Initialize the contents of the Frame.
   * 
   * @param eClass
   * @param objectTableDescriptor
   * @param containingObject
   * @param objects
   * @param objectSubTableDescriptors
   */
  public void init(EClass eClass, EObjectTableDescriptor<T> objectTableDescriptor,
      EObject containingObject, EList<T> objects, EObjectSubTableDescriptor... objectSubTableDescriptors) {
    this.objectTableDescriptor = objectTableDescriptor;
    eObjectTable = new EObjectJTable<T>(this, eClass, objectTableDescriptor, containingObject, objects);
    if (this.objectTableDescriptor == null) {
      this.objectTableDescriptor = eObjectTable.getEObjectTableDescriptor();
    }
    
    if (objectSubTableDescriptors.length > 0) {
      final EObjectSubTableDescriptor eObjectSubTableDescriptor = objectSubTableDescriptors[0];
      final EObjectJTable<? extends EObject> subTable = eObjectSubTableDescriptor.geteObjectTable();
      ObjectSelectionListener<T> objectSelectionListener = new ObjectSelectionListener<T>() {
        @SuppressWarnings("unchecked")
        @Override
        public void objectSelected(Object source, T selectedObject) {
          if (selectedObject != null) {
            LOGGER.info("=> new object selected: " + selectedObject);
            EObject eObject = (EObject) selectedObject;
            
            Object subListObject = eObject;
            for (EStructuralFeature eStructuralFeature: eObjectSubTableDescriptor.geteStructuralFeatures()) {
              subListObject = ((EObject) subListObject).eGet(eStructuralFeature);
              LOGGER.info("subListObject: " + subListObject);
            }
            
            LOGGER.info("subListObject: " + subListObject);
            @SuppressWarnings("rawtypes")
            EList subList = (EList<EObject>) subListObject;
            
            subTable.setObjects(subList);
          } else {
            System.out.println("NotasWindow: nota selected is null");
            subTable.setObjects(null);
          }
        }
      };
      eObjectTable.addObjectSelectionListener(objectSelectionListener);
      JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, eObjectTable, subTable);
      splitPane.setDividerLocation(300);
      getContentPane().add(splitPane, BorderLayout.CENTER);
    } else {
      getContentPane().add(eObjectTable, BorderLayout.CENTER);
    }
    
    if (isSearchAndFilterPanelToBeAdded()) {
      getContentPane().add(createSearchAndFilterPanel(), BorderLayout.NORTH);
    }

    pack();
    setVisible(true);
  }
  
  
  public EObjectJTable<T> getEObjectTable() {
    return eObjectTable;
  }
  
  public boolean isSearchAndFilterPanelToBeAdded() {
    return true;
  }
  
  @Override
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      eObjectTable.destruct();
      LOGGER.info("eObjectTable destructed.");
    }
  }
  
  /**
   * Layout
   * "ga naar: " text in attribuut/all vorige volgende
   * "filter: " text in attribuut/all filter filter uit
   * attribuut sorteer sorteren uit
   * (search/filter/sort text  in column/all)
   * 
   * @return
   */
  private JPanel createSearchAndFilterPanel() {
    JPanel       panel = getCustomization().getComponentFactory().createPanel(500, 80, false);
    SpringLayout layout = new SpringLayout();
    panel.setLayout(layout);
    
    addSearchComponents(panel, layout);
    addFilterComponents(panel, layout);
    
    return panel;
  }
  
  private void addSearchComponents(JPanel panel, SpringLayout layout) {
    ComponentFactory componentFactory = getTheComponentFactory();

    // "Zoek:"
    JLabel label = componentFactory.createLabel("Zoek:", SwingConstants.LEFT);
    panel.add(label);
    layout.putConstraint(SpringLayout.WEST, label,
        COL1_ROW1,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label,
        TOP_MARGIN,
        SpringLayout.NORTH, panel);

    // 'Vorige' button
    JButton button = componentFactory.createButton("vorige", "zoek achteruit");
    panel.add(button);
    layout.putConstraint(SpringLayout.WEST, button,
        COL2_ROW1,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, button,
        0,
        SpringLayout.VERTICAL_CENTER, label);
    button.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent arg0) {
        eObjectTable.findObjectBackward(searchTextField.getText(), getSearchEStructuralFeature(), isSearchCompleteObject());
      }

    });

    // 'Volgende' button
    button = componentFactory.createButton("volgende", "zoek vooruit");
    panel.add(button);
    layout.putConstraint(SpringLayout.WEST, button,
        COL3_ROW1,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, button,
        0,
        SpringLayout.VERTICAL_CENTER, label);
    button.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent arg0) {
        performForwardSearch(false);
      }

    });


    // "voorkomen van de tekst:"
    label = componentFactory.createLabel("voorkomen van de tekst:", SwingConstants.LEFT);
    panel.add(label);
    layout.putConstraint(SpringLayout.WEST, label,
        COL4_ROW1,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label,
        TOP_MARGIN,
        SpringLayout.NORTH, panel);

    // Tekstveld voor te zoeken tekst.
    searchTextField = componentFactory.createTextField(20, "typ text om te zoeken");
    panel.add(searchTextField);
    layout.putConstraint(SpringLayout.WEST, searchTextField,
        COL5_ROW1,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, searchTextField,
        0,
        SpringLayout.VERTICAL_CENTER, label);
    searchTextField.getDocument().addDocumentListener(new DocumentListener() {
          public void changedUpdate(DocumentEvent e) {
              LOGGER.info("DocumentUpdate: changedUpdate");
              performForwardSearch(true);
          }
          public void insertUpdate(DocumentEvent e) {
              LOGGER.info("DocumentUpdate: insertUpdate");
              performForwardSearch(true);
          }
          public void removeUpdate(DocumentEvent e) {
              LOGGER.info("DocumentUpdate: removeUpdate");
              performForwardSearch(true);
          }
        });

    label = componentFactory.createLabel("in", SwingConstants.LEFT);
    panel.add(label);
    layout.putConstraint(SpringLayout.WEST, label,
        COL6_ROW1,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label,
        TOP_MARGIN,
        SpringLayout.NORTH, panel);

    attributeComboBox = componentFactory.createComboBox(20, "kies een of 'alle' attributen waarop je wilt zoeken");
    attributeComboBox.addItem("volledig object");
    attributeComboBox.addItem("alle kolommen");
    
    EObjectTableColumnDescriptor[] columnDescriptors = objectTableDescriptor.getColumnDescriptors();
    for (int columnIndex = 0; columnIndex < columnDescriptors.length; columnIndex++) {
      LOGGER.info("columnIndex = " + columnIndex);
      EObjectTableColumnDescriptor columnDescriptor = columnDescriptors[columnIndex];
      if (eObjectTable.isSearchableColumn(columnIndex)) {
        LOGGER.info("Adding item for column: " + columnDescriptor.getColumnName());
        attributeComboBox.addItem(columnDescriptor.getColumnName());
      }
    }
    attributeComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        performForwardSearch(true);
      }
      
    });

    panel.add(attributeComboBox);
    layout.putConstraint(SpringLayout.WEST, attributeComboBox,
        COL7_ROW1,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, attributeComboBox,
        0,
        SpringLayout.VERTICAL_CENTER, label);
  }
  
  
  protected void performForwardSearch(boolean forceStartFromBeginning) {
    String searchText = searchTextField.getText();
    if (!searchText.isEmpty()) {
      eObjectTable.findObjectForward(searchText, forceStartFromBeginning, getSearchEStructuralFeature(), isSearchCompleteObject());
    }
  }

  private EStructuralFeature getSearchEStructuralFeature() {
    int selectedItemIndex = attributeComboBox.getSelectedIndex();
    
    if (selectedItemIndex <= 1) {
      LOGGER.info("getSearchEStructuralFeature => null");
      return null;
    }
    
    String selectedItem = (String) attributeComboBox.getSelectedItem();
    LOGGER.info("getSearchEStructuralFeature : column name = " + selectedItem);
    EStructuralFeature structuralFeature = eObjectTable.getEStructuralFeatureForColumnName(selectedItem);
    if (structuralFeature == null) {
      LOGGER.info("getSearchEStructuralFeature => null");
    } else {
      LOGGER.info("getSearchEStructuralFeature => " + structuralFeature.getName());
    }
    return structuralFeature;
  }
  
  private boolean isSearchCompleteObject() {
    int selectedItemIndex = attributeComboBox.getSelectedIndex();
    
    if (selectedItemIndex == 0) {
      return true;
    } else {
      return false;
    }
  }
  
  private void addFilterComponents(JPanel panel, SpringLayout layout) {
    ComponentFactory componentFactory = getTheComponentFactory();
    int verticalOffset = TOP_MARGIN + ROW_DISTANCE;
    
    // Label: "Filter op de tekst:"
    JLabel label = componentFactory.createLabel("Filter op de tekst:", SwingConstants.LEFT);
    panel.add(label);
    layout.putConstraint(SpringLayout.WEST, label,
        COL1_ROW2,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label,
        verticalOffset,
        SpringLayout.NORTH, panel);

    // JTextField: voor tekst om op te filteren
    // A DocumentListener is used to change the filter on each text change.
    filterTextField = componentFactory.createTextField(20, "type tekst om op te filteren");
    filterTextField.getDocument().addDocumentListener(
      new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
          //Plain text components do not fire these events
        }
        public void insertUpdate(DocumentEvent e) {
          setFilter();
        }
        public void removeUpdate(DocumentEvent e) {
          setFilter();
        }
      });

    
    panel.add(filterTextField);
    layout.putConstraint(SpringLayout.WEST, filterTextField,
        COL2_ROW2,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, filterTextField,
        0,
        SpringLayout.VERTICAL_CENTER, label);
    
    // Label: "in:"
    label = componentFactory.createLabel("in", SwingConstants.LEFT);
    panel.add(label);
    layout.putConstraint(SpringLayout.WEST, label,
        COL3_ROW2,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label,
        verticalOffset,
        SpringLayout.NORTH, panel);
    
    // JComboBox: to select a column, or all columns.
    filterAttributeComboBox = componentFactory.createComboBox(20, "kies een of 'alle' attributen waarop je wilt zoeken, filteren of sorteren");
    filterAttributeComboBox.addItem("alle kolommen");

    EObjectTableColumnDescriptor[] columnDescriptors = objectTableDescriptor.getColumnDescriptors();
    for (int columnIndex = 0; columnIndex < columnDescriptors.length; columnIndex++) {
      LOGGER.info("columnIndex = " + columnIndex);
      EObjectTableColumnDescriptor columnDescriptor = columnDescriptors[columnIndex];
      if (eObjectTable.isSearchableColumn(columnIndex)) {
        LOGGER.info("Adding item for column: " + columnDescriptor.getColumnName());
        filterAttributeComboBox.addItem(columnDescriptor.getColumnName());
      }
    }
    filterAttributeComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        setFilter();
      }
      
    });
    
    panel.add(filterAttributeComboBox);
    layout.putConstraint(SpringLayout.WEST, filterAttributeComboBox,
        COL4_ROW2,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, filterAttributeComboBox,
        0,
        SpringLayout.VERTICAL_CENTER, label);
  }

  
  private void setFilter() {
    LOGGER.info("Setting filter, text = " + filterTextField.getText() + ", feature = " + getFilterEStructuralFeature());
    eObjectTable.setFilterExpression(filterTextField.getText(), getFilterEStructuralFeature());
  }


  private EStructuralFeature getFilterEStructuralFeature() {
    LOGGER.info("=>");
    
    int selectedItemIndex = filterAttributeComboBox.getSelectedIndex();
    
    if (selectedItemIndex <= 0) {
      LOGGER.info("<= null");
      return null;
    }
    
    String selectedItem = (String) filterAttributeComboBox.getSelectedItem();
    LOGGER.info("column name = " + selectedItem);
    EStructuralFeature structuralFeature = eObjectTable.getEStructuralFeatureForColumnName(selectedItem);
    if (structuralFeature == null) {
      LOGGER.info("=> null");
    } else {
      LOGGER.info("=> " + structuralFeature.getName());
    }
    return structuralFeature;
  }
}
