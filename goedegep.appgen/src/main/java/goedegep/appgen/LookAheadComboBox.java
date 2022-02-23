package goedegep.appgen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import goedegep.util.logging.MyLoggingFormatter;
import goedegep.util.text.ListTextLookAhead;
import goedegep.util.text.TextLookAhead;

/**
 * A ComboBox where, apart from selecting from a list, you can type with auto-completion.
 * 
 * @param <T> The type of the selectable elements.
 */
@SuppressWarnings("serial")
public class LookAheadComboBox<T> extends JComboBox<T> {
  private final static Logger     LOGGER = Logger.getLogger(LookAheadComboBox.class.getName());
  private static final Level      LOG_LEVEL = Level.SEVERE;

  private ListTextLookAhead<T> listTextLookAhead;
  private LookAheadComboBoxEditor lookAheadComboBoxEditor;
  private boolean sortItems;
  
  public LookAheadComboBox(List<T> values, boolean sortItems, int columns, int maxRowCount, String toolTipText) {
    super();
    
    this.sortItems = sortItems;
    listTextLookAhead = new ListTextLookAhead<>();
    
    
    if (maxRowCount != -1) {
      setMaximumRowCount(maxRowCount);
    }
    
    setEditable(true);

    lookAheadComboBoxEditor = new LookAheadComboBoxEditor(this, columns, listTextLookAhead);
    
    if (toolTipText != null) {
      setToolTipText(toolTipText);
      lookAheadComboBoxEditor.setToolTipText(toolTipText);
    }
    
    setValues(values);
    setEditor(lookAheadComboBoxEditor);
    
    Dimension size = new Dimension(columns * 10, 20);
    setMaximumSize(size);
    setMinimumSize(size);
    setPreferredSize(size);
    
    lookAheadComboBoxEditor.setBackground(getBackground());
  }
  
  public LookAheadComboBox(T[] values, boolean sortItems, int columns, int maxRowCount, String toolTipText) {
    super();
    
    this.sortItems = sortItems;
    listTextLookAhead = new ListTextLookAhead<>();
    
    
    if (maxRowCount != -1) {
      setMaximumRowCount(maxRowCount);
    }
    
    setEditable(true);

    lookAheadComboBoxEditor = new LookAheadComboBoxEditor(this, columns, listTextLookAhead);
    
    if (toolTipText != null) {
      setToolTipText(toolTipText);
      lookAheadComboBoxEditor.setToolTipText(toolTipText);
    }
    
    setValues(values);
    setEditor(lookAheadComboBoxEditor);
  }
  
  /**
   * Change both the list of values and the selected value.
   * 
   * @param values
   * @param selectedValue
   */
  public void setValues(List<T> values, Object selectedValue) {
    setValues(values);
    setValue(selectedValue);
  }
  
  /**
   * Change both the list of values and the selected value.
   * 
   * @param values
   * @param selectedValue
   */
  public void setValues(T[] values, Object selectedValue) {
    setValues(values);
    setValue(selectedValue);
  }

  /**
   * Change the list of values.
   * @param values
   */
  @SuppressWarnings("unchecked")
  public void setValues(List<T> values) {
    removeAllItems();

    if (values != null) {
      if (sortItems  &&  !values.isEmpty()  &&  (values.get(0) instanceof Comparable)) {
        Object[] sortArray = new Object[values.size()];
        int i = 0;
        for (T value: values) {
          sortArray[i++] = value;
        }
        Arrays.sort(sortArray);

        List<T> sortedValues = new ArrayList<>();      
        for (Object item: sortArray) {
          sortedValues.add((T) item);
          addItem((T) item);
        }
        listTextLookAhead.setValues(sortedValues);
      } else {
        for (T value: values) {
          addItem(value);
        }
        listTextLookAhead.setValues(values);
      }
    } else {
      listTextLookAhead.setValues(values);
    }
  }

  /**
   * Change the list of values.
   * @param values
   */
  @SuppressWarnings("unchecked")
  public void setValues(T[] values) {
    removeAllItems();
    
    if (sortItems  &&  (values.length != 0)  &&  (values[0] instanceof Comparable)) {
      Object[] sortArray = new Object[values.length];
      int i = 0;
      for (Object value: values) {
        sortArray[i++] = value;
      }
      Arrays.sort(sortArray);
      
      List<T> sortedValues = new ArrayList<>();      
      for (Object item: sortArray) {
        sortedValues.add((T) item);
        addItem((T) item);
      }
      listTextLookAhead.setValues(sortedValues);
    } else {
      for (Object value: values) {
        addItem((T) value);
      }
      listTextLookAhead.setValues(values);
    }
  }

  /**
   * Change the selection.
   */
  public void setValue(Object selectedValue) {
    LOGGER.severe("=>");
    setSelectedItem(selectedValue);
    LOGGER.severe("<=");
  }
  
  @Override
  public void setBackground(Color color) {
    super.setBackground(color);
    if (lookAheadComboBoxEditor != null) {
      lookAheadComboBoxEditor.setBackground(color);
    }
  }
  
  public static void main(String[] args) {
    logSetup();
    
    String[] options = {"Rood", "Groen", "Geel", "Blauw", "Oranje"};
    List<String> optionsList = new ArrayList<>();
    for (String option: options) {
      optionsList.add(option);
    }
    
    JFrame frame = new JFrame("Combobox demo");
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
    JLabel normalJComboBoxLabel = new JLabel("Normal JComboBox", SwingConstants.LEFT);
    panel.add(normalJComboBoxLabel);

    JComboBox<String> comboBox = new JComboBox<>(options);
    panel.add(comboBox);
    
    panel.add(new JLabel("    "));
    
    JLabel lookAheadJComboBoxLabel = new JLabel("LookAheadComboBox", SwingConstants.LEFT);
    panel.add(lookAheadJComboBoxLabel);

    LookAheadComboBox<String> lookAheadComboBox = new LookAheadComboBox<>(optionsList, true, 40, 3, "begin maar te typen");
    panel.add(lookAheadComboBox);
    
    panel.add(new JLabel("    "));
    
    JLabel lookAheadJComboBoxWithSpinnerLabel = new JLabel("LookAheadComboBoxWithSpinner", SwingConstants.LEFT);
    panel.add(lookAheadJComboBoxWithSpinnerLabel);

    LookAheadComboBoxWithSpinner<String> lookAheadComboBoxWithSpinner = new LookAheadComboBoxWithSpinner<>(optionsList, true, 40, 3, "begin maar te typen");
    panel.add(lookAheadComboBoxWithSpinner);
    
    panel.add(new JLabel("    "));
    
    JLabel pgLookAheadTextFieldLabel = new JLabel("LookAheadTextField", SwingConstants.LEFT);
    panel.add(pgLookAheadTextFieldLabel);

    TextLookAhead textLookAhead = new ListTextLookAhead<>(options);
    LookAheadTextField lookAheadTextField = new LookAheadTextField(6, textLookAhead);
    lookAheadTextField.setText("hallo");
    lookAheadTextField.setToolTipText("begin maar te typen");    
    panel.add(lookAheadTextField);
    
    panel.add(new JLabel("    "));
    
    JLabel selectieLabel = new JLabel("Huidige selectie", SwingConstants.LEFT);
    panel.add(selectieLabel);

    JTextField huidigeSelectie = new JTextField("nog niets gekozen.");
    panel.add(huidigeSelectie);
    
    JLabel focusLabel = new JLabel("Focus info", SwingConstants.LEFT);
    panel.add(focusLabel);
    
    JTextField focusText = new JTextField("");
    panel.add(focusText);
    
    frame.getContentPane().add(panel);
    
    comboBox.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        LOGGER.severe("comboBox actionPerformed");
        huidigeSelectie.setText("Normal JComboBox: " + (String) comboBox.getSelectedItem());
      }
      
    });
    
    comboBox.addFocusListener(new FocusListener(){

      @Override
      public void focusGained(FocusEvent e) {
        LOGGER.severe("comboBox gained focus");
        focusText.setText(focusText.getText() + " [comboBox gained focus]"); 
      }

      @Override
      public void focusLost(FocusEvent e) {
        LOGGER.severe("comboBox lost focus");
        focusText.setText(focusText.getText() + " [comboBox lost focus]"); 
      }
      
    });
    
    lookAheadComboBox.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBox<String> lookAheadComboBox = (LookAheadComboBox<String>) e.getSource();
        LOGGER.severe("lookAheadComboBox actionPerformed");
        huidigeSelectie.setText("LookAheadComboBox: " + (String) lookAheadComboBox.getSelectedItem());
      }
      
    });
    
    lookAheadComboBox.addFocusListener(new FocusListener(){

      @Override
      public void focusGained(FocusEvent e) {
        LOGGER.severe("lookAheadComboBox gained focus");
        focusText.setText(focusText.getText() + " [lookAheadComboBox gained focus]"); 
      }

      @Override
      public void focusLost(FocusEvent e) {
        LOGGER.severe("lookAheadComboBox lost focus");
        focusText.setText(focusText.getText() + " [lookAheadComboBox lost focus]"); 
      }
      
    });

    lookAheadComboBoxWithSpinner.addChangeListener(new ChangeListener(){

      @Override
      public void stateChanged(ChangeEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBoxWithSpinner<String> lookAheadComboBoxWithSpinner = (LookAheadComboBoxWithSpinner<String>) e.getSource();
        LOGGER.severe("lookAheadComboBoxWithSpinner stateChanged");
        huidigeSelectie.setText("LookAheadComboBoxWithSpinner: " + (String) lookAheadComboBoxWithSpinner.getValue());
      }
      
    });
    
    lookAheadComboBoxWithSpinner.addFocusListener(new FocusListener(){

      @Override
      public void focusGained(FocusEvent e) {
        LOGGER.severe("lookAheadComboBoxWithSpinner gained focus");
        focusText.setText(focusText.getText() + " [lookAheadComboBoxWithSpinner gained focus]"); 
      }

      @Override
      public void focusLost(FocusEvent e) {
        LOGGER.severe("lookAheadComboBoxWithSpinner lost focus");
        focusText.setText(focusText.getText() + " [lookAheadComboBoxWithSpinner lost focus]"); 
      }
      
    });

    lookAheadTextField.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        LookAheadTextField lookAheadTextField = (LookAheadTextField) e.getSource();
        LOGGER.severe("lookAheadTextField actionPerformed");
        huidigeSelectie.setText("LookAheadTextField: " + lookAheadTextField.getText());
      }
      
    });
    
    lookAheadTextField.addFocusListener(new FocusListener(){

      @Override
      public void focusGained(FocusEvent e) {
        LOGGER.severe("lookAheadTextField gained focus");
        focusText.setText(focusText.getText() + " [lookAheadTextField gained focus]"); 
      }

      @Override
      public void focusLost(FocusEvent e) {
        LOGGER.severe("lookAheadTextField lost focus");
        focusText.setText(focusText.getText() + " [lookAheadTextField lost focus]"); 
      }
      
    });
   
    frame.pack();
    frame.setVisible(true);
  }

  private static void logSetup() {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(LOG_LEVEL);

    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(LOG_LEVEL);
  }
}
