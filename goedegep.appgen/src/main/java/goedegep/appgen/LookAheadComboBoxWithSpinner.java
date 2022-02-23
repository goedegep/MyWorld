package goedegep.appgen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A ComboBox where, apart from selecting from a list, you can type with auto-completion. Extended with a spinner to select the next/previous item.
 */
@SuppressWarnings("serial")
public class LookAheadComboBoxWithSpinner<T> extends JSpinner {
  private final static Logger     LOGGER = Logger.getLogger(LookAheadComboBoxWithSpinner.class.getName()); 

  private LookAheadComboBox<T> lookAheadComboBox;
  private SpinnerModel defaultModel;
  private SpinnerListModel spinnerListModel;
  private List<T> items;
  private boolean ignoreActionFromLookAheadComboBox = false;

  public LookAheadComboBoxWithSpinner(List<T> values, boolean sortItems, int columns, int maxRowCount, String toolTipText) {
    super();

    lookAheadComboBox = new LookAheadComboBox<T>(values, sortItems, columns, maxRowCount, toolTipText);
    
    defaultModel = getModel();
    items = new ArrayList<T>();
    spinnerListModel = new SpinnerListModel();

    if (toolTipText != null) {
      setToolTipText(toolTipText);
      lookAheadComboBox.setToolTipText(toolTipText);
    }

    setValues(values);
    setEditor(lookAheadComboBox);
  
    addChangeListener(new ChangeListener(){

      @Override
      public void stateChanged(ChangeEvent e) {
        LOGGER.severe("=>");
        ignoreActionFromLookAheadComboBox = true;
        lookAheadComboBox.setSelectedItem(getValue());
        ignoreActionFromLookAheadComboBox = false;
        LOGGER.severe("<=");
      }
      
    });
    
    lookAheadComboBox.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        LOGGER.severe("=>");
        if (ignoreActionFromLookAheadComboBox) {
          return;
        }
        
        @SuppressWarnings("unchecked")
        LookAheadComboBox<String> lookAheadComboBox = (LookAheadComboBox<String>) e.getSource();
        setValue(lookAheadComboBox.getSelectedItem());
        LOGGER.severe("<=");
      }
      
    });

    lookAheadComboBox.setBackground(getBackground());
  }

  /**
   * Change the list of values.
   * @param values
   */
  @SuppressWarnings("unchecked")
  public void setValues(T[] values) {
    items.clear();

    if ((values.length != 0)  &&  (values[0] instanceof Comparable)) {
      Object[] sortArray = new Object[values.length];
      int i = 0;
      for (Object value: values) {
        sortArray[i++] = value;
      }
      Arrays.sort(sortArray);
//      spinnerListModel = new SpinnerListModel(sortArray);
//      setModel(spinnerListModel);

      List<T> valuesList = new ArrayList<>();
      for (Object object: sortArray) {
        valuesList.add((T) object);
      }
      items.addAll(valuesList);
      spinnerListModel.setList(items);
      lookAheadComboBox.setValues(valuesList);
    } else {
      List<T> valuesList = new ArrayList<>();
      for (Object value: values) {
        valuesList.add((T) value);
      }
//      spinnerListModel = new SpinnerListModel(valuesList);
//      setModel(spinnerListModel);
      items.addAll(valuesList);
      spinnerListModel.setList(items);
      lookAheadComboBox.setValues(values);
    }
  }

  /**
   * Change the list of values.
   * @param values
   */
  @SuppressWarnings("unchecked")
  public void setValues(List<T> values) {
    LOGGER.info("=> values.size()=" + ((values != null) ? values.size() : "null"));
    items.clear();
    List<T> sortedValues = new ArrayList<>();

    if (values != null) {
      if (!values.isEmpty()  &&  (values.get(0) instanceof Comparable)) {
        Object[] sortArray = new Object[values.size()];
        int i = 0;
        for (T value: values) {
          sortArray[i++] = value;
        }
        Arrays.sort(sortArray);

        for (Object item: sortArray) {
          sortedValues.add((T) item);
        }
        items.addAll(sortedValues);
      } else {
        items.addAll(values);
      }
    }
    
    if (items.size() > 0) {
      spinnerListModel.setList(items);
      setModel(spinnerListModel);
      setValue(items.get(0));
      lookAheadComboBox.setValues(items);
    } else {
      setModel(defaultModel);
      lookAheadComboBox.setValues(values);
    }
    LOGGER.info("<=");
  }

  /**
   * Change the selection.
   */
  @Override
  public void setValue(Object selectedValue) {
    LOGGER.severe("=>");
    if (selectedValue != null) {
      super.setValue(selectedValue);
    }
    ignoreActionFromLookAheadComboBox = true;
    lookAheadComboBox.setSelectedItem(selectedValue);
    ignoreActionFromLookAheadComboBox = false;
    LOGGER.severe("<=");
  }
  
  public void setSelectedIndex(int index) {
    setValue(items.get(index));
  }

  @Override
  public void setBackground(Color color) {
    super.setBackground(color);
    if (lookAheadComboBox != null) {
      lookAheadComboBox.setBackground(color);
    }
  }
  
  public List<T> getItems() {
    return items;
  }
  
  public int getItemCount(){
    if (items == null) {
      return 0;
    } else {
      return items.size();
    }
  }
}


