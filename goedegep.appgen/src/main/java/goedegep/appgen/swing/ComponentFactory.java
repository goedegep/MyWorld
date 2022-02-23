package goedegep.appgen.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.LookAheadComboBoxWithSpinner;
import goedegep.appgen.LookAheadTextField;
import goedegep.appgen.PgButton;
import goedegep.appgen.PgFormattedTextField;
import goedegep.appgen.PgJFileChooser;
import goedegep.appgen.PgTableHeaderRenderer;
import goedegep.appgen.SpringLayoutPanel;
import goedegep.appgen.controls.ObjectInputCurrencySwing;
import goedegep.util.text.TextLookAhead;

public class ComponentFactory {

  public ComponentFactory() {
  }

  public JPanel createPanel(int width, int height, boolean border) {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    if (border) {
      panel.setBorder(BorderFactory.createEtchedBorder());
    }
    if (width >= 0  &&  height >= 0) {
      Dimension dim = new Dimension(width, height);
      panel.setMinimumSize(dim);
      panel.setPreferredSize(dim);
    }

    return panel;
  }

  public SpringLayoutPanel createSpringLayoutPanel(int width, int height, boolean border) {
    SpringLayoutPanel panel = new SpringLayoutPanel();
    if (border) {
      panel.setBorder(BorderFactory.createEtchedBorder());
    }
    if (width >= 0  &&  height >= 0) {
      Dimension dim = new Dimension(width, height);
      panel.setMinimumSize(dim);
      panel.setPreferredSize(dim);
    }

    return panel;
  }

  /**
   * Create a JLabel.
   * @param text the label text.
   * @param horizontalAlignment the text alignment of the label.
   *                            One of the following constants defined in SwingConstants: LEFT, CENTER (the default for image-only labels), RIGHT,
   *                            LEADING (the default for text-only labels) or TRAILING.
   * @return
   */
  public JLabel createLabel(String text, int horizontalAlignment) {
    JLabel label = new JLabel(text);
    label.setHorizontalAlignment(horizontalAlignment);

    return label;
  }

  public JPanel createLabelPanel(String text, int horizontalAlignment, int width, int height) {
    JLabel label = new JLabel(text);
    label.setHorizontalAlignment(horizontalAlignment);

    JPanel panel = createPanel(width, height, true);
    panel.add(label, BorderLayout.CENTER);
    panel.add(label);

    return panel;
  }

  public <T> LookAheadComboBoxWithSpinner<T> createLookAheadComboBoxWithSpinner(List<T> items, boolean sortItems, int columns, int maxRowCount, String toolTipText) {
    LookAheadComboBoxWithSpinner<T> comboBox = new LookAheadComboBoxWithSpinner<>(items, sortItems, columns, maxRowCount, toolTipText);
    return comboBox;
  }

  public <T> LookAheadComboBox<T> createLookAheadComboBox(List<T> items, boolean sortItems, int columns, int maxRowCount, String toolTipText) {
    LookAheadComboBox<T> comboBox = new LookAheadComboBox<>(items, sortItems, columns, maxRowCount, toolTipText);
    return comboBox;
  }

  public <T> LookAheadComboBox<T> createLookAheadComboBox(T[] items, boolean sortItems, int columns, int maxRowCount, String toolTipText) {
    LookAheadComboBox<T> comboBox = new LookAheadComboBox<>(items, sortItems, columns, maxRowCount, toolTipText);
    return comboBox;
  }

  public <E> JComboBox<E> createComboBox(E[] items, int columns, String toolTipText) {
    JComboBox<E> comboBox = null;
    if (items != null) {
      comboBox = new JComboBox<E>(items);
    } else {
      comboBox = new JComboBox<E>();
    }
    if (columns != -1) {
      comboBox.setMaximumRowCount(columns);
    }
    if (toolTipText != null) {
      comboBox.setToolTipText(toolTipText);
    }

    return comboBox;
  }

  @SuppressWarnings("unchecked")
  public <E> JComboBox<E> createComboBox(List<E> items, int columns, String toolTipText) {
    return createComboBox((E[]) items.toArray(), columns, toolTipText);
  }

  public <E> JComboBox<E> createComboBox(int columns, String toolTipText) {
    return createComboBox((E[]) null, columns, toolTipText);
  }

  public JButton createButton(String text, String toolTip) {
    return createButton(text, (Icon) null, toolTip);
  }

  public JButton createButton(String text, Image image, String toolTip) {
    return createButton(text, new ImageIcon(image), toolTip);
  }

  public JButton createButton(String text, Icon icon, String toolTip) {
    JButton button = new PgButton();
    if (text != null) {
      button.setText(text);
    }
    if (icon != null) {
      button.setIcon(icon);
    }
    if (toolTip != null) {
      button.setToolTipText(toolTip);
    }

    return button;
  }

  public JRadioButton createRadioButton(String text, boolean selected) {
    JRadioButton button = new JRadioButton(text, selected);

    return button;
  }

  public JCheckBox createCheckBox(String text, boolean selected) {
    JCheckBox checkBox = new JCheckBox(text, selected);

    return checkBox;
  }

  public <T> JList<T> createList(String toolTip) {
    JList<T> list = new JList<>();
    if (toolTip != null) {
      list.setToolTipText(toolTip);
    }

    return list;
  }

  public JScrollPane createListPane(JList<? extends Object> list, int width, int height) {
    JScrollPane pane = new JScrollPane(list);
    Dimension paneDim = new Dimension(width, height);
    pane.setMinimumSize(paneDim);
    pane.setPreferredSize(paneDim);

    return pane;
  }

  public PgFormattedTextField createPgFormattedTextField(String text, int columns, PgFormattedTextField.FormatSpec formatSpec,  String toolTipText) {
    PgFormattedTextField textField = null;

    textField = new PgFormattedTextField(text, columns, formatSpec);

    if (toolTipText != null) {
      textField.setToolTipText(toolTipText);
    }

    return textField;
  }



  public JTextField createTextField(String text, int columns, String toolTipText) {
    JTextField textField = null;

    if (columns != -1) {
      textField = new JTextField(columns);
    } else {
      textField = new JTextField();
    }

    if (text != null) {
      textField.setText(text);
    }
    if (toolTipText != null) {
      textField.setToolTipText(toolTipText);
    }

    return textField;
  }

  public JTextField createTextField(int columns, String toolTipText) {
    return createTextField(null, columns, toolTipText);
  }

  public LookAheadTextField createPgLookAheadTextField(String text, int columns, TextLookAhead lookAhead, String toolTipText) {
    LookAheadTextField textField = new LookAheadTextField(columns, lookAhead);

    if (text != null) {
      textField.setText(text);
    }
    if (toolTipText != null) {
      textField.setToolTipText(toolTipText);
    }

    return textField;
  }

  public JTextArea createTextArea(String text, boolean border) {
    JTextArea textArea = new JTextArea(text);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    if (border) {
      textArea.setBorder(BorderFactory.createEtchedBorder());
    }

    return textArea;
  }
  
  public JPanel createHelpTextArea(String text) {
      JPanel panel = createPanel(-1, -1, false);
      
      text = "<html><u>Uitleg</u><br/>" + text + "</html>";
      JEditorPane editorPane = new JEditorPane("text/html", text);
      editorPane.setEditable(false);
      editorPane.setMargin(new Insets(3, 6, 3, 6));
      
      JScrollPane editorScrollPane = new JScrollPane(editorPane);
      editorScrollPane.setPreferredSize(new Dimension(250, 80));
      editorScrollPane.setMinimumSize(new Dimension(10, 10));     
      panel.add(editorScrollPane);
      
      return panel;
  }
  
  public JEditorPane createEditorPane(String type, String text) {
    JEditorPane editorPane = new JEditorPane(type, text);
    
    return editorPane;
  }
  
  public TableCellRenderer createTableHeaderRenderer() {
    PgTableHeaderRenderer hr = new PgTableHeaderRenderer(SwingConstants.LEFT, SwingConstants.CENTER);
    hr.setBorder(new BevelBorder(BevelBorder.RAISED));
    
    return hr;
  }

  
  public JTable createTable(TableModel model, int width, int height) {
    JTable      table = new JTable(model);
    
    table.setPreferredScrollableViewportSize(new Dimension(width, height));
    table.setFillsViewportHeight(true);
    
    TableCellRenderer hr = createTableHeaderRenderer();
    
    if (model != null) {
      for (int i = 0; i < model.getColumnCount(); i++) {
        TableColumn column  = table.getColumnModel().getColumn(i);
        column.setHeaderRenderer(hr);
      }
    }

    return table;
  }
  
  public PgJFileChooser createFileChooser() {
    PgJFileChooser fileChooser = new PgJFileChooser();
    
    return fileChooser;
  }
  
  public ObjectInputCurrencySwing createObjectInputCurrency(String text, int columns, boolean isOptional, String toolTipText) {
    ObjectInputCurrencySwing objectInputCurrency = new ObjectInputCurrencySwing(text, columns, isOptional, toolTipText);
    
    return objectInputCurrency;
  }
}
