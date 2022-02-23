package goedegep.appgen.controls;

import java.awt.Color;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

@SuppressWarnings("serial")
public class ObjectInputCurrencySwing extends JTextField implements ObjectInput<PgCurrency> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectInputCurrencySwing.class.getName());
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  
  private boolean isOptional;
  private Integer minimumFactor = null;
  private Integer maximumFactor = null;
//  private boolean addValueInEurosIfApplicable = false;
  
  public ObjectInputCurrencySwing(String text, int columns, boolean isOptional, String toolTipText) {
    super(text, columns);
    
    if (toolTipText != null) {
      setToolTipText(toolTipText);
    }

    getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
        updateErrorHighlight();
      }
      public void removeUpdate(DocumentEvent e) {
        updateErrorHighlight();
      }
      public void insertUpdate(DocumentEvent e) {
        updateErrorHighlight();
      }

      public void updateErrorHighlight() {
        if (isEnteredDataValid(null)) {
          setForeground(Color.BLACK);
        } else {
          setForeground(Color.RED);
        }
      }
    });
  }
  
  public void setValidFactorRange(int minimumFactor, int maximumFactor) {
    this.minimumFactor = minimumFactor;
    this.maximumFactor = maximumFactor;
  }
  
  @Override
  public boolean isOptional() {
    return isOptional;
  }

  @Override
  public boolean isFilledIn() {
    return (getText().length() != 0);
  }

  @Override
  public boolean isValid(StringBuilder buf) {
    
    if (isOptional()  &&  !isFilledIn()) {
      return true;
    }
        
    boolean returnValue = true;
    
    try {
      CF.parse(getText());
    } catch (ParseException e) {
      LOGGER.info("Geldbedrag PARSE EXCEPTION ");
      returnValue = false;
    }
    
    if (returnValue == true) {
      returnValue = isEnteredDataValid(buf);
    }
    
    return returnValue;
  }

  @Override
  public PgCurrency getObjectValue() {
    try {
      return CF.parse(getText());
    } catch (ParseException e) {
      LOGGER.severe("Geldbedrag PARSE EXCEPTION ");
      return null;
    }
  }
  
  /**
   * Check whether there is valid data entered.
   * <p>
   * This is the case if the text is not empty, can be parsed to a PgCurrency
   * and, if applicable, is within the required range.
   * 
   * @param errorMessageBuffer A StringBuilder to append a possible error message to. This parameter may be null.
   * @return true if there is valid data entered, false otherwise.
   */
  private boolean isEnteredDataValid(StringBuilder errorMessageBuffer) {
    boolean valueIsValid = !getText().isEmpty();

    if (valueIsValid) {
      try {
        PgCurrency currency = CF.parse(getText());
        if ((minimumFactor != null)  &&
            (currency.getFactor() < minimumFactor)) {
          valueIsValid = false;
          if (errorMessageBuffer != null) {
            errorMessageBuffer.append("te weinig cijfers achter de comma");
          }
        } else if ((maximumFactor != null)  &&
            (currency.getFactor() > maximumFactor)) {
          valueIsValid = false;
          if (errorMessageBuffer != null) {
            errorMessageBuffer.append("te veel cijfers achter de comma");
          }
        }
      } catch (ParseException e) {
        valueIsValid = false;
        if (errorMessageBuffer != null) {
          errorMessageBuffer.append(e.getMessage());
        }
      }
    }

    return valueIsValid;
  }

  @Override
  public void setObjectValue(PgCurrency objectValue) {
//    String valueInEurosText = "";
//    
//    if (addValueInEurosIfApplicable  &&  (objectValue.getCurrency() == PgCurrency.GUILDER)) {
//      PgCurrency valueInEuros = objectValue.changeCurrency(PgCurrency.EURO);
//      valueInEurosText = "   " + CF.format(valueInEuros);
//    }
//      
//    setText(CF.format(objectValue) + valueInEurosText);
    setText(CF.format(objectValue));
  }

//  public void setAddValueInEurosIfApplicable() {
//    addValueInEurosIfApplicable = true;
//  }
}
