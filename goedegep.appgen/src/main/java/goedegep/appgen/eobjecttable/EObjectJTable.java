package goedegep.appgen.eobjecttable;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.appgen.EEnumEditorDescriptor;
import goedegep.appgen.ImageSize;
import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.appgen.TextBasedCellRenderer;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppGenAbstractTable;
import goedegep.appgen.swing.MenuFactory;
import goedegep.appgen.swing.OptionDialog;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;

/**
 * A {@link JScrollPane} containing a table to display a list of {@link EObject}s (Eclipse Modeling Framework Objects).
 * Each object is shown in a row of the table.
 * <p>
 * The layout and behaviour of the table can be partly or fully specified by an {@link EObjectTableDescriptor}. But this descriptor
 * can also be omitted, in which case a default table is created, where all information is derived from the EObjects.<br>
 * This class implements the {@link ObjectSelector} interface. The selected object is the object related to the selected row
 * (or the row of which any field is selected) of the table.
 * 
 *
 * @param <T> the type of the objects in the table.
 */
@SuppressWarnings("serial")
public class EObjectJTable<T extends EObject> extends AppGenAbstractTable implements ObjectSelector<T> {
  private static final Logger LOGGER = Logger.getLogger(EObjectJTable.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  // default sizes of the scroll pane
  /**
   * Default width of the scroll pane containing the table.
   */
  private static final int WIDTH = 600;
  /**
   * Default height of the scroll pane containing the table.
   */
  private static final int HEIGHT = 400;
  
  // Options for an option pane, which is used when there are references to an object to be deleted.
  private static final String CONTINUE_OPTION = "Referenties verwijderen en object verwijderen";
  private static final String CANCEL_OPTION = "Annuleren (niets verwijderen)";
  private static final String DEFAULT_NO_BELEGGINGSVERZEKERINGEN_OPTION = CONTINUE_OPTION;
  private static final String[] REFERENCES_OPTIONS = {
      CONTINUE_OPTION,
      CANCEL_OPTION
    };
    
  private AppFrame owner;   // The AppFrame which contains this table.
  private EObjectTableColumnDescriptor[] columnDescriptors;
  @SuppressWarnings("rawtypes")
  private Map<TableRowOperation, TableRowOperationDescriptor> rowOperations = null;
  private List<ObjectSelectionListener<T>> objectSelectionListeners = new ArrayList<ObjectSelectionListener<T>>();  // Listeners to which object is selected.
  private EList<T> objects;   // The objects shown in the table.
  private EObjectTableModel<T> tableModel;
  private TableRowSorter<? extends EObjectTableModel<T>> sorter;
  private Adapter          adapter;
  private List<Adapter>    objectsAdapters;
  private EClass eClass;
  private EFactory eFactory;  // used to create new objects.
  private EObjectTableDescriptor<T> objectTableDescriptor = null;

  /**
   * Constructor.
   * <p>
   * The properties of the table will be as follows:
   * <ul>
   * <li>
   * size of the table<br>
   * The size of the table can be specified by setting field <code>dimension</code> in the <code>objectTableDescriptor</code>. The default dimensions are
   * {@value #WIDTH} x {@value #HEIGHT} (width x height).
   * </li>
   * <li>
   * TableModel<br>
   * The class of the TableModel can be specified via the <code>tableModelClass</code> in the <code>objectTableDescriptor</code>. The default value is {@link EObjectTableModel}.
   * <li>
   * columns<br>
   * The columns can be specified by the <code>columnDescriptors</code> in the <code>objectTableDescriptor</code>. In this case the table will have the specified columns, in the given order.
   * If the <code>columnName</code> isn't specified, it is set to the name of the  <code>structuralFeature</code>.<br>
   * By default there will be a column for each structural feature of the <code>eClass</code>, where:
   *         <ul>
   *         <li>
   *         the column name is the name of the <code>structuralFeature</code>
   *         </li>
   *         <li>
   *         the <code>longValue</code> is set to <code>null</code>.
   *         </li>
   *         <li>
   *         each column is editable.
   *         </li>
   *         </ul>

   * </li>
   * </ul>
   * 
   * @param owner                 the {@link AppFrame} which contains this table (mandatory).
   * @param eClass                the {@link EClass} of the objects listed in the table (mandatory).
   * @param objectTableDescriptor full specification of the table. If <code>null</code>, default values are used, where most information is derived from the <b>eClass</b>.
   * @param containingObject
   * @param objects               the objects to be listed in the table.
   */
  public EObjectJTable(AppFrame owner, EClass eClass, EObjectTableDescriptor<T> objectTableDescriptor,
      EObject containingObject, EList<T> objects) {
    super(owner);
    
    this.owner = owner;
    this.objects = objects;
    this.eClass = eClass;
    EPackage ePackage = eClass.getEPackage();
    eFactory = ePackage.getEFactoryInstance();
    
    // Set the dimensions of the ScrollPane
    Dimension dimension;
    if ((objectTableDescriptor != null)  &&  (objectTableDescriptor.getDimension() != null)) {
      dimension = objectTableDescriptor.getDimension();
    } else {
      dimension = new Dimension(WIDTH, HEIGHT);
    }
    setDimensions(dimension);
        
    // Create the table.
    init();

    JTable table = getTable();
    
    // Get or create the column descriptors.
    if ((objectTableDescriptor != null)  &&  objectTableDescriptor.getColumnDescriptors() != null) {
      columnDescriptors = objectTableDescriptor.getColumnDescriptors();
    } else {
      columnDescriptors = createDefaultColumnDescriptors();
    }
    
    // Fill in missing column names, using the name of the structural feature.
    for (EObjectTableColumnDescriptor columnDescriptor: columnDescriptors) {
      if (columnDescriptor.getColumnName() == null) {
        columnDescriptor.setColumnName(columnDescriptor.geteStructuralFeature().getName());
      }
    }
    
    // Get the TableModel class and construct the table model.
    Class<? extends EObjectTableModel<T>> tableModelClass = null;
    if (objectTableDescriptor != null) {
      tableModelClass = objectTableDescriptor.getTableModelClass();
    }
    
    if (tableModelClass != null) {
      try {
        Constructor<? extends EObjectTableModel<T>> constructor = tableModelClass.getConstructor(EObjectJTable.class, columnDescriptors.getClass(), List.class);
        tableModel = (EObjectTableModel<T>) constructor.newInstance(this, columnDescriptors, objects);
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    } else {
      tableModel = new EObjectTableModel<T>(this, columnDescriptors, objects);
    }
    
    table.setModel(tableModel);
    
    initObjectSelectionListening();
    
    // Install adapter on the objects to update the table on changes.
    if (containingObject != null) {
      LOGGER.info("EObjectTable: Installing adapter");
      adapter = new AdapterImpl() {
        public void notifyChanged(Notification notification) {
          LOGGER.info("EObjectTable: Notfication received from the data model. Data model has changed!!!");
          LOGGER.info("EObjectTable: eventType: " + notification.getEventType());
          LOGGER.info("EObjectTable: FeatureID: " + notification.getFeatureID(null));
          LOGGER.info("EObjectTable: Position: " + notification.getPosition());
          LOGGER.info("EObjectTable: Feature(): " + notification.getFeature());
          LOGGER.info("EObjectTable: Notifier: " + notification.getNotifier().getClass().getName());
          LOGGER.info("EObjectTable: isTouch: " + notification.isTouch());
          if (!notification.isTouch()) {
            LOGGER.info("EObjectTable: Updating data");
            updateData();
          }
        }

      };
      objectsAdapters = containingObject.eAdapters();
      objectsAdapters.add(adapter);      
    }
    
    // Install the right data renderers and editors.
    Map<Class<?>, TableCellRenderer> classSpecificCellRenderers = null;
    if (objectTableDescriptor != null  &&
        objectTableDescriptor.getClassSpecificCellRenderers() != null) {
     classSpecificCellRenderers = objectTableDescriptor.getClassSpecificCellRenderers();
      for (Class<?> c: classSpecificCellRenderers.keySet()) {
        table.setDefaultRenderer(c, classSpecificCellRenderers.get(c));
      }
    }
    Map<Class<?>, TableCellEditor> classSpecificCellEditors = null;
    if (objectTableDescriptor != null  &&
        objectTableDescriptor.getClassSpecificCellEditors() != null) {
      classSpecificCellEditors = objectTableDescriptor.getClassSpecificCellEditors();
      for (Class<?> c: classSpecificCellEditors.keySet()) {
        table.setDefaultEditor(c, classSpecificCellEditors.get(c));
      }
    }
    Map<Class<?>, EEnumEditorDescriptor> enumSpecificCellEditorValues = null;
    if (objectTableDescriptor != null  &&
        objectTableDescriptor.getEnumSpecificCellEditorValues() != null) {
      enumSpecificCellEditorValues = objectTableDescriptor.getEnumSpecificCellEditorValues();
      for (Class<?> c: enumSpecificCellEditorValues.keySet()) {
        JComboBox<Object> comboBox = getCustomization().getComponentFactory().createComboBox(10, null);
        EEnumEditorDescriptor enumEditorDescriptor = enumSpecificCellEditorValues.get(c);
        for (Object enumValue: enumEditorDescriptor.getValues()) {
          comboBox.addItem(enumValue);
        }
        if (enumEditorDescriptor.getNoValueText() != null) {
          comboBox.addItem(enumEditorDescriptor.getNoValueText());
        }
        DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
        table.setDefaultEditor(c, cellEditor);
      }
    }
    int columnIndex = 0;
    for (EObjectTableColumnDescriptor columnDescriptor: columnDescriptors) {
      TableCellRenderer renderer = columnDescriptor.getRenderer();
      if (renderer != null) {
        TableColumn tableColumn = table.getColumnModel().getColumn(columnIndex);
        tableColumn.setCellRenderer(renderer);
      }
      
      TableCellEditor editor = columnDescriptor.getEditor();
      if (editor != null) {
        TableColumn tableColumn = table.getColumnModel().getColumn(columnIndex);
        tableColumn.setCellEditor(editor);
      }
      
      columnIndex++;
    }

    initColumnSizes(table);

    setViewportView(table);
    
    boolean sortingEnabled = false;
    List<SortKey> sortKeys = null;
    if ((objectTableDescriptor == null)  ||
        (objectTableDescriptor.getSortingDescriptor() == null)  ||
        objectTableDescriptor.getSortingDescriptor().isSortingEnabled()) {
      sortingEnabled = true;
      sorter = new TableRowSorter<EObjectTableModel<T>>(tableModel);
      
      if (objectTableDescriptor.getSortingDescriptor() != null) {
        sortKeys = objectTableDescriptor.getSortingDescriptor().getSortKeys();
        if (sortKeys != null) {
          ArrayList<RowSorter.SortKey> list = new ArrayList<>();
          for (SortKey sortKey: sortKeys) {
            list.add(sortKey);
          }
          sorter.setSortKeys(list);
        }
      }
      table.setRowSorter(sorter);
      
    }
    EObjectTableSortingDescriptor sortingDescriptor = new EObjectTableSortingDescriptor(sortingEnabled, sortKeys);
    
    if ((objectTableDescriptor != null)  &&
        (objectTableDescriptor.getRowOperations() != null)) {
      rowOperations = objectTableDescriptor.getRowOperations();
      installPopupMenu(table);
    }
    
    this.objectTableDescriptor = new EObjectTableDescriptor<T>(dimension, columnDescriptors, sortingDescriptor,
        classSpecificCellRenderers, classSpecificCellEditors, enumSpecificCellEditorValues,
        rowOperations, tableModelClass);
    
    if (containingObject != null) {
      containingObject.eAdapters().add(new Adapter(){

        @Override
        public void notifyChanged(Notification notification) {
          LOGGER.info("notifyChanged <=>");
          updateData();
        }

        @Override
        public Notifier getTarget() {
          LOGGER.severe("getTarget <=>");
          return null;
        }

        @Override
        public void setTarget(Notifier newTarget) {
          LOGGER.fine("setTarget <=>");
        }

        @Override
        public boolean isAdapterForType(Object type) {
          LOGGER.fine("isAdapterForType <=>");

          return false;
        }

      });
    }
  }
  
  /**
   * Create default column descriptors.
   * <p/>
   * A column descriptor is created for each structural feature (obtained via <code>eClass.getEAllStructuralFeatures()</code>.</br>
   * 
   * @return an array of {@link EObjectTableColumnDescriptor}, where for each column descriptor the following information is filled in:
   *         <ul>
   *         <li>
   *         eStructuralFeature the <code>EStructuralFeature</code>
   *         </li>
   *         <li>
   *         <code>columnName</code> the name of the <code>structuralFeature</code> (obtained by <code>getName()</code>).
   *         </li>
   *         <li>
   *         <code>longValue</code> set to <code>null</code>.
   *         </li>
   *         <li>
   *         <code>isEditable</code> set to <code>true</code>.
   *         </li>
   *         </ul>
   */
  private EObjectTableColumnDescriptor[] createDefaultColumnDescriptors() {
    List<EStructuralFeature> allStructuralFeatures = eClass.getEAllStructuralFeatures();
    EObjectTableColumnDescriptor[] columnDescriptors = new EObjectTableColumnDescriptor[allStructuralFeatures.size()];
    
    int index = 0;
    for (EStructuralFeature structuralFeature: allStructuralFeatures) {
      EObjectTableColumnDescriptor columnDescriptor = new EObjectTableColumnDescriptor(structuralFeature, structuralFeature.getName(), null, true);
      columnDescriptors[index++] = columnDescriptor;
    }
    
    return columnDescriptors;
  }
  
  /**
   * Get the column index of the column for a specific EStructuralFeature.
   * <p/>
   * The requested column is looked up by going through the list of columnDescriptors.
   * 
   * @param eStructuralFeature the EStructuralFeature for which the column index is requested.
   * @return the column index of the column for the <b>eStructuralFeature</b>. 
   */
  private int getColumnIndexForEStructuralFeature(EStructuralFeature eStructuralFeature) {
    for (int columnIndex = 0; columnIndex < columnDescriptors.length; columnIndex++) {
      EObjectTableColumnDescriptor columnDescriptor = columnDescriptors[columnIndex];
      if (columnDescriptor.geteStructuralFeature().equals(eStructuralFeature)) {
        return columnIndex;
      }
    }
    
    return -1;
  }

  public void setFilterExpression(String regex, EStructuralFeature eStructuralFeature) {
    @SuppressWarnings("unchecked")
    TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>) getTable().getRowSorter();
    if (regex.isEmpty()) {
      rowSorter.setRowFilter(null);
    } else {
      // If current expression doesn't parse, don't update.
      RowFilter<TableModel, Object> rowFilter = null;
      try {
        if (eStructuralFeature != null) {
          LOGGER.info("eStructuralFeature = " + eStructuralFeature.getName());
          LOGGER.info("index = " + getColumnIndexForEStructuralFeature(eStructuralFeature));
          rowFilter = RowFilter.regexFilter("(?i)" + regex, getColumnIndexForEStructuralFeature(eStructuralFeature)); // Prepend the 'text' with "(?i)" to search with 'ignore case'.
        } else {
          rowFilter = RowFilter.regexFilter("(?i)" + regex); // Prepend the 'text' with "(?i)" to search with 'ignore case'.
        }
        rowSorter.setRowFilter(rowFilter);    
      } catch (java.util.regex.PatternSyntaxException e) {
        e.printStackTrace();
      }
    }
  }

  protected JPopupMenu createTablePopupMenu(final int x, final int y) {
    JMenuItem       menuItem;
    JPopupMenu      popup = null;
    final int       row = getTable().rowAtPoint(new Point(x, y));
    final int       column = getTable().columnAtPoint(new Point(x, y));

    popup = new JPopupMenu();

    @SuppressWarnings("rawtypes")
    TableRowOperationDescriptor tableRowOperationDescriptor;

    if (row != -1) {
      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.OPEN);
      if (tableRowOperationDescriptor != null) {
        MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            openObject(row);
          }
        });
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.NEW_OBJECT_BEFORE);
      if (tableRowOperationDescriptor != null) {
        MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            createNewObject(row, true);
          }
        });
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.NEW_OBJECT_AFTER);
      if (tableRowOperationDescriptor != null) {
        MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            createNewObject(row, false);
          }
        });
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.DELETE_OBJECT);
      if (tableRowOperationDescriptor != null) {
        MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            deleteObject(row);
          }
        });
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.MOVE_OBJECT_UP);
      if (tableRowOperationDescriptor != null) {
        menuItem = MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            moveObject(row, true);
          }
        });
        if (row == 0) {
          menuItem.setEnabled(false);
        }
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.MOVE_OBJECT_DOWN);
      if (tableRowOperationDescriptor != null) {
        menuItem = MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            moveObject(row, false);
          }
        });
        if (row == getTable().getRowCount() - 1) {
          menuItem.setEnabled(false);
        }
      }
      
      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.ATTRIBUTE_EDITOR);
      if (tableRowOperationDescriptor != null) {
        MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            attribuutEditor(row, column);
          }
        });

      }
    } else {
      Object o = rowOperations.get(TableRowOperation.NEW_OBJECT);
      LOGGER.info("o: " + o);
      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.NEW_OBJECT);
      if (tableRowOperationDescriptor != null) {
        MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
          public void actionPerformed(ActionEvent e) {
            createNewObject(row, false);
          }
        });
      }
    }

    return popup;
  }

  public void setObjects(EList<T> objects) {
    this.objects = objects;
    tableModel.setObjects(objects);
    tableModel.fireTableDataChanged();
    if (getTable().getRowCount() > 0) {
      getTable().changeSelection(0, 1, false, false);
    } else {
      getTable().changeSelection(-1, 1, false, false);
    }
    getOwner().repaint();
  }
   
  private void updateData() {
    Integer firstChangedRowIndex = tableModel.fillData();
    tableModel.fireTableDataChanged();
    if (firstChangedRowIndex != null) {
      getTable().changeSelection(getTable().convertRowIndexToView(firstChangedRowIndex), 1, false, false);
    }
    getOwner().repaint();
  }
  
  public void destruct() {
    if (objectsAdapters != null) {
      objectsAdapters.remove(adapter);
    }
  }
  
  @SuppressWarnings("unchecked")
  public T createItem() {
    return (T) eFactory.create(eClass);
  }
  
  public T getObject(int row) {
    return objects.get(row);
  }

  public void openObject(int row) {
    T object = objects.get(row);
    
    openObject(object);
  }

  /**
   * Override this method to open an object.
   */
  public void openObject(T object) {
  }

  public void createNewObject(int row, boolean before) {
    T item = createItem();
    if (before) {
      objects.add(row, item);
    } else {
      // insert before the next element, if it exists
      int nextRow = row + 1;
      if (nextRow < objects.size()) {
        objects.add(nextRow, item);
      } else {
        objects.add(item);
      }
    }
    
    tableModel.fillData();
    tableModel.fireTableDataChanged();
    
    findObject(item);
    getOwner().repaint();
  }

  private void moveObject(int row, boolean up) {
    int newPosition;
    if (up) {
      newPosition = row - 1;
    } else {
      newPosition = row + 1;
    }
    T object = objects.get(row);
    objects.move(newPosition, row);
    
    AbstractTableModel model = (AbstractTableModel) getTable().getModel();
    if (up) {
      model.fireTableRowsUpdated(newPosition, row);
    } else {
      model.fireTableRowsUpdated(row, newPosition);
    }
    findObject(object);
    
    getOwner().repaint();
  }
  
  public void deleteObject(int row) {
    JTable table = getTable();
    int modelIndex = table.convertRowIndexToModel(row);
    
    @SuppressWarnings("unchecked")
    EObjectTableModel<T> model = (EObjectTableModel<T>) getTable().getModel();
    T objectToRemove = model.getRowObject(modelIndex);
    LOGGER.severe("=> row=" + row);
    LOGGER.severe("Object to remove=" + objectToRemove.toString());
    
    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(objectToRemove, objectToRemove.eResource().getResourceSet());
    if (settings.size() != 0) {
      StringBuffer buf = new StringBuffer();
      buf.append("Er zijn ");
      buf.append(settings.size());
      buf.append(" referenties naar dit object.");
      buf.append(NEW_LINE);
      System.out.println("Er zijn " + settings.size() + " referenties naar dit object.");

      for (Object object: settings) {
        EStructuralFeature.Setting setting = (EStructuralFeature.Setting) object;
        EStructuralFeature feature = setting.getEStructuralFeature();
        String inOfAls = " als ";
        if (feature.isMany()) {
          inOfAls = " in ";
        }
        EObject referringObject = setting.getEObject();
        EObject container = referringObject.eContainer();
        buf.append("* in ");
        buf.append(container.toString());
        buf.append(" in ");
        buf.append(referringObject.toString());
        buf.append(inOfAls);
        buf.append(setting.getEStructuralFeature().getName());
        buf.append(NEW_LINE);
      }
      
      Image image = owner.getCustomization().getResources().getAttentionImage(ImageSize.SIZE_3);
      OptionDialog optionDialog = new OptionDialog(
          owner,
          "Hoe verder?",
          image,
          buf.toString(),
          REFERENCES_OPTIONS,
          DEFAULT_NO_BELEGGINGSVERZEKERINGEN_OPTION);
      WindowUtil.showDialogCenteredOnParent(owner, optionDialog);
      String selectedOption = optionDialog.getSelectedOption();
      
      LOGGER.info("Selection = " + selectedOption);
      
      switch (selectedOption) {
      case CONTINUE_OPTION:
        break;

      case CANCEL_OPTION:
        return;
      }
    }
    
    for (Object object: settings) {
      EStructuralFeature.Setting setting = (EStructuralFeature.Setting) object;
      EStructuralFeature feature = setting.getEStructuralFeature();
      EObject referringObject = setting.getEObject();
      
      if (feature.isMany()) {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) referringObject.eGet(feature);
        list.remove(objectToRemove);
      } else {
        referringObject.eUnset(feature);
      }
    }
    
    boolean removed = objects.remove(objectToRemove);
    LOGGER.severe("Object removed=" + removed);
    EList<EReference> references = objectToRemove.eClass().getEAllReferences();
    for (EReference reference: references) {
      LOGGER.severe("Reference = " + reference.getName());
      objectToRemove.eUnset(reference);
    }
    
    updateData();
  }
  
  
  protected void attribuutEditor(int row, int column) {
    // TODO Auto-generated method stub
    LOGGER.info("=>");
  }
  
  /*
   * Implementation of ObjectSelectionListener.
   */
  
  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);
  }
  
  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }
  
  /**
   * Install a ListSelectionListener on the SelectionModel of the table for the implementation of the ObjectSelectionListener interface.
   * In case of a selection change, {@link #handleValueChanged} is called.
   */
  private void initObjectSelectionListening() {
    getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          handleValueChanged();
        }
      }
    });
  }
  
  /**
   * Notify ObjectSelectionListeners about the newly selected object.
   */
  private void handleValueChanged() {
    LOGGER.fine("=>");
    T selectedObject = getSelectedObject();
    
    for (ObjectSelectionListener<T> objectSelectionListener: objectSelectionListeners) {
      LOGGER.fine("Calling: " + objectSelectionListener);
      objectSelectionListener.objectSelected(this, selectedObject);
    }
  }

  /**
   * Get the first selected object (row) in the table.
   * 
   * @return the first selected object.
   */
  public T getSelectedObject() {
    JTable table = getTable();
    
    int[] selectedViewRows = table.getSelectedRows();
    
    if (selectedViewRows.length > 0) {
      int selectedModelRow = table.convertRowIndexToModel(selectedViewRows[0]);
      @SuppressWarnings("unchecked")
      T rowObject = ((EObjectTableModel<T>) table.getModel()).getRowObject(selectedModelRow);
      return rowObject;
    } else {
      return null;
    }
  }

  
  /*
   * This find works on the complete object, also parts that are not displayed in the table.
   * The search operates on the displayed rows.
   * If there is a row selected, the search starts at the next row of the selected row.
   * Otherwise, the search starts at the beginning of the table (display row 0).
   */
  public EObject findObjectBackward(String text, EStructuralFeature eAttributeName, boolean searchCompleteObject) {
    JTable table = getTable();
    @SuppressWarnings("unchecked")
    EObjectTableModel<T> tableModel = (EObjectTableModel<T>) table.getModel();
    Pattern pattern = Pattern.compile(text);
    
    int searchStartRow = table.getRowCount() - 1;
    int[] selectedViewRows = table.getSelectedRows();
    
    if (selectedViewRows.length > 0) {
      searchStartRow = selectedViewRows[0] + -1;
    }
    LOGGER.info("EObjectTable: findObjectBackward: searchStartRow = " + searchStartRow);
    
    for (int rowIndex = searchStartRow; rowIndex >= 0; rowIndex--) {
      int modelIndex = table.convertRowIndexToModel(rowIndex);
      LOGGER.info("EObjectTable: findObjectBackward: rowIndex = " + rowIndex + ", modelIndex = " + modelIndex);
      T rowObject = tableModel.getRowObject(modelIndex);
      if (checkObject(rowObject, pattern, eAttributeName, searchCompleteObject)) {
        findObject(rowObject);
        return rowObject;
      }
    }
    
    return null;
  }

  
  /*
   * This find works on the complete object, also parts that are not displayed in the table.
   * The search operates on the displayed rows.
   * If there is a row selected, the search starts at the next row of the selected row.
   * Otherwise, the search starts at the beginning of the table (display row 0).
   */
  public EObject findObjectForward(String searchText, boolean forceStartFromBeginning, EStructuralFeature eStructuralFeature, boolean searchCompleteObject) {
    LOGGER.info("=>");
    JTable table = getTable();
    @SuppressWarnings("unchecked")
    EObjectTableModel<T> tableModel = (EObjectTableModel<T>) table.getModel();
    
    // Prepend the 'text' with "(?i)" to search with 'ignore case'.
    Pattern pattern = Pattern.compile("(?i)" + searchText);
    
    int searchStartRow = 0;
    
    if (!forceStartFromBeginning) {
      int[] selectedViewRows = table.getSelectedRows();

      if (selectedViewRows.length > 0) {
        searchStartRow = selectedViewRows[0] + 1;
      }
    }
    LOGGER.info("searchStartRow = " + searchStartRow);
    
    for (int rowIndex = searchStartRow; rowIndex < table.getRowCount(); rowIndex++) {
      int modelIndex = table.convertRowIndexToModel(rowIndex);
      LOGGER.info("findObjectForward: rowIndex = " + rowIndex + ", modelIndex = " + modelIndex);
      T rowObject = tableModel.getRowObject(modelIndex);
      if (checkObject(rowObject, pattern, eStructuralFeature, searchCompleteObject)) {
        findObject(rowObject);
        LOGGER.info("<= " + rowObject);
        return rowObject;
      }
    }
    
    LOGGER.info("<= null");
    return null;
  }
  
  public boolean isSearchableColumn(int columnIndex) {
    JTable table = getTable();
    Class<?> c = tableModel.getColumnClass(columnIndex);
    // A column is searchable, if we know how to get the displayed text.
    TableCellRenderer renderer = table.getDefaultRenderer(c);
    renderer.getTableCellRendererComponent(table, null, false, false, 0, 0).toString();
    return true;
  }
  
  /**
   * Check whether an object contains a specific pattern.
   * @param eObject the object to be checked.
   * @param pattern the pattern to be checked on.
   * @param eStructuralFeature the name of a specific attribute that should contain the pattern. If null, all EAttributes are checked.
   *                       If searchCompleteObject is set, this parameter is ignored.
   * @param searchCompleteObject search on all attributes of the object, also 
   * @return true if the pattern is found, false otherwise.
   */
  private boolean checkObject(EObject eObject, Pattern pattern, EStructuralFeature eStructuralFeature, boolean searchCompleteObject) {
    LOGGER.info("=>");
    JTable table = getTable();
    List<EStructuralFeature> allStructuralFeatures =  eObject.eClass().getEAllStructuralFeatures();

    for (EStructuralFeature currentFeature: allStructuralFeatures) {
      LOGGER.info("In loop for feature: " + currentFeature.getName());
      if (!searchCompleteObject  &&
          (eStructuralFeature != null)  && !currentFeature.equals(eStructuralFeature)) {
        LOGGER.info("Skipping structuralFeature: " + currentFeature.getName());
        continue;
      }
      
      if (currentFeature instanceof EAttribute) {
        LOGGER.info("feature is EAttribute");
        //  check value
        EAttribute eAttribute = (EAttribute) currentFeature;
        Object object = eObject.eGet(eAttribute);
        if (object != null) {
          if (object instanceof String) {
            String string = (String) object;
            LOGGER.info("type is String");
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
              LOGGER.info("<= Match found on: " + string);
              return true;
            }
          } else if (object instanceof Boolean) {
            Boolean booleanValue = (Boolean) object;
            LOGGER.info("type is Boolean");
            Matcher matcher = pattern.matcher(booleanValue.toString());
            if (matcher.find()) {
              LOGGER.info("<= Match found on: " + booleanValue.toString());
              return true;
            }
          } else {
            TableCellRenderer renderer = table.getDefaultRenderer(object.getClass());
            if (renderer instanceof TextBasedCellRenderer) {
              TextBasedCellRenderer tbRenderer = (TextBasedCellRenderer) renderer;
              String text = tbRenderer.getText(object);
              LOGGER.info("TextBasedCellRenderer for this type, text = " + text);
              Matcher matcher = pattern.matcher(text);
              if (matcher.find()) {
                LOGGER.info("<= match found on: " + text);
                return true;
              }
            } else {
              LOGGER.severe("NO TextBasedCellRenderer for this type");
            }
          }
        } else {
          LOGGER.info("null object (so no match)");
        }
      } else if (currentFeature instanceof EReference) {
        LOGGER.info("feature is EReference");
        if (!searchCompleteObject) {
          LOGGER.info("skipping EReference structuralFeature: " + currentFeature.getName());
          continue;
        }
        
        EReference eReference = (EReference) currentFeature;
        if (eReference.isContainment()) {
          LOGGER.info("is Containment");
          if (eReference.isMany()) {
            LOGGER.info("is Many");
            @SuppressWarnings("unchecked")
            List<EObject> subObjects = (List<EObject>) eObject.eGet(eReference);
            for (EObject subObject: subObjects) {
              if (checkObject(subObject, pattern, eStructuralFeature, searchCompleteObject)) {
                LOGGER.info("<= true");
                return true;
              }
            }
          } else {
            LOGGER.info("is NOT Many");
            if (checkObject((EObject) eObject.eGet(eReference), pattern, eStructuralFeature, searchCompleteObject)) {
              LOGGER.info("<= true");
              return true;
            }
          }
        } else {
          LOGGER.info("is NOT Containment");
          if (eReference.isMany()) {
            LOGGER.info("is Many");
          } else {
            LOGGER.info("is NOT Many");
          }
        }
      } else {
        throw new IllegalArgumentException("Unknown structural feature");
      }
    }

    LOGGER.info("<= No match found ");
    return false;
  }
  
  public void findObject(T object) {
    int modelRow = tableModel.findObject(object);
    
    if (modelRow != -1) {
      JTable table = getTable();
      int viewRow = table.convertRowIndexToView(modelRow);
      table.setRowSelectionInterval(viewRow, viewRow);
      Rectangle viewPortBounds = getViewportBorderBounds();

      Rectangle bounds = table.getCellRect(viewRow, 0, true);
      if (bounds.getMaxY() < viewPortBounds.getMinY()  ||
          bounds.getMinY() > viewPortBounds.getMaxY()) {
        getViewport().setViewPosition(bounds.getLocation());
      }
    }
  }
  
  public EObjectTableDescriptor<T> getEObjectTableDescriptor() {
    return objectTableDescriptor;
  }
  
  public EStructuralFeature getEStructuralFeatureForColumnName(String columnName) {
    for (EObjectTableColumnDescriptor columnDescriptor: columnDescriptors) {
      if (columnDescriptor.getColumnName().equals(columnName)) {
        return columnDescriptor.geteStructuralFeature();
      }
    }
    
    return null;
  }
}