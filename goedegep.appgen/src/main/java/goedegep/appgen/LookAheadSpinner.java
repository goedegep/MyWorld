package goedegep.appgen;

import goedegep.util.text.ListTextLookAhead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;

/**
 * Usage information.
 * The toString() method of the values is used as text. So either the original class itself
 * should have a usable toString() implementation, or a wrapper class can be used.
 *
 */

@SuppressWarnings("serial")
public class LookAheadSpinner<T extends Comparable<? super T>> extends JSpinner {
  private ListTextLookAhead<T> listTextLookAhead;
  private SpinnerEditor     spinnerEditor;
  private SpinnerListModel  spinnerListModel;

  public LookAheadSpinner(List<T > values, int columns, String toolTipText) {
    super();
        
    List<T> myValues = new ArrayList<T>(values);  // Make a copy of the list, as we should not change the original list.
    Collections.sort(myValues);
    
    spinnerListModel = new SpinnerListModel(myValues);
    setModel(spinnerListModel);
    listTextLookAhead = new ListTextLookAhead<T>(myValues);

    spinnerEditor = new SpinnerEditor(this, columns, listTextLookAhead);
    setEditor(spinnerEditor);
    
    if (toolTipText != null) {
      setToolTipText(toolTipText);
    }
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
   * Change the list of values.
   * @param values
   */
  public void setValues(List<T> values) {
    List<T> myValues = new ArrayList<T>(values);   // Make a copy of the list, as we should not change the original list.
    Collections.sort(myValues);
    
    spinnerListModel.setList(myValues);
    listTextLookAhead.setValues(myValues);
  }

  /**
   * Change the selection.
   */
  public void setValue(Object selectedValue) {
    getModel().setValue(selectedValue);
    spinnerEditor.stateChanged(new ChangeEvent(this));
  }
}
