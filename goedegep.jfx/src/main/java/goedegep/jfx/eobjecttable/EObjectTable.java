package goedegep.jfx.eobjecttable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DoubleClickEventDispatcher;
import goedegep.jfx.eobjecttable.objectstringconverters.DateObjectStringConverter;
import goedegep.jfx.eobjecttable.objectstringconverters.IntegerObjectStringConverter;
import goedegep.jfx.observableelist.ObservableEList;
import goedegep.jfx.stringconverters.AnyTypeStringConverter;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * This class is an extension of a {@link TableView}, for presenting a list of {@link EObject}s (Eclipse Modeling Framework Objects).
 * Each object is shown in a row of the table.
 * <p>
 * The layout and behaviour of the table can be partly or fully specified by an {@link EObjectTableDescriptor}. But this descriptor
 * can also be omitted, in which case a default table is created, where all information is derived from the EClass parameter in the constructor.<br>
 * This class implements the {@link ObjectSelector} interface. The selected object is the object related to the selected row
 * (or the row of which any field is selected) of the table.<br/>
 * The table supports sorting and filtering.<br/>
 * The table will be editable if at least one column is editable.
 * <p>
 * The data type for a column is determined by the <code>eTypedElement</code> in the column descriptor.
 * If a column is editable, the default cell type is a <code>TextFieldTableCell</code>. This means that a string conversion is needed to/from the data type of the column.<br/>
 * For the following types this is automatically supported by the EObjectTable:
 * TODO<br/>
 * For all other types a <code>StringConverter</code> has to be provided in the column descriptor.
 * 
 *
 * @param <T> The type of the elements, which shall be an extension of <code>EObject</code>.
 */
public class EObjectTable<T extends EObject> extends TableView<T> implements ObjectSelector<T> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTable.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  /**
   * Default StringConverters
   */
  private static Map<Class<? extends Object>, StringConverter<Object>> defaultStringConverters = new HashMap<>();
  
  /**
   * The EClass of the items in the table.
   */
  private EClass eClass;
  
  /**
   * The descriptor which specifies the complete table.
   */
  private EObjectTableDescriptor<T> tableDescriptor;
  
  /**
   * Listeners to which object is selected.
   */
  private List<ObjectSelectionListener<T>> objectSelectionListeners = new ArrayList<>();
    
  /**
   * The objects to be shown in the table. Any editing (add and delete) shall occur on this list.
   */
  private List<T> objects;
  
  /**
   * A copy of the <code>objects</code> and possibly filtered and sorted. These are the items used in the TableView.
   */
  public FilteredList<T> filteredObjects;
  
  private ObservableList<T> observableObjects;
  
  private SortedList<T> tableSortedList;
  
  /**
   * The {@link EFactory} to create new objects of type T.
   */
  private EFactory eFactory;
  
  private EObject containingObject;
  
  private Predicate<T> tableFilterPredicate;
  private List<Predicate<T>> predicates = new ArrayList<>();
  
  private ComponentFactoryFx componentFactory;

//  public EObjectTable(CustomizationFx customization, EClass eClass, EObjectTableDescriptor<T> objectTableDescriptor, EObject containingObject, EReference reference) {
//    this(customization, eClass, objectTableDescriptor, containingObject, getReferenceForObjects(containingObject, objects));
//  }

  //
//private static EReference getReferenceForObjects(EObject containingObject, List objects) {
//  EReference objectsEReference = null;
//  for (EReference eReference: containingObject.eClass().getEAllReferences()) {
//    if (containingObject.eGet(eReference) == objects) {
//      LOGGER.severe("Found reference: " + eReference.getName());
//      objectsEReference = eReference;
//      break;
//    }
//  }
//  
//  if (objectsEReference == null) {
//    throw new IllegalArgumentException("'objects' is not part of 'containingObject'.");
//  }
//  
//  return objectsEReference;
//}
  
  /**
   * Constructor.
   * <p>
   * The properties of the table will be as follows:
   * <ul>
   * <li>
   * columns<br>
   * The columns can be specified by the <code>columnDescriptors</code> in the <code>objectTableDescriptor</code>. In this case the table will have the specified columns, in the given order.
   * If the <code>columnName</code> isn't specified, it is set to the name of the <code>structuralFeature</code>.<br>
   * By default there will be a column for each structural feature of the <code>eClass</code>, where:
   *         <ul>
   *         <li>
   *         the column name is the name of the <code>structuralFeature</code>
   *         </li>
   *         <li>
   *         each column is editable.
   *         </li>
   *         </ul>

   * </li>
   * </ul>
   * 
   * @param customization the GUI customization.
   * @param eClass the {@link EClass} of the objects listed in the table (mandatory).
   * @param objectTableDescriptor full specification of the table. If <code>null</code>, default values are used, where most information is derived from the <b>eClass</b>.
   * @param containingObject the object containing the list of objects.
   * @param objectsEReference the reference, in the containingObject, to the list of objects.
   */
  public EObjectTable(CustomizationFx customization, EClass eClass, EObjectTableDescriptor<T> objectTableDescriptor, EObject containingObject, EReference objectsEReference) {
    this(customization, eClass, objectTableDescriptor);
    
    setObjects(containingObject, objectsEReference);
  }
  
  
  public EObjectTable(CustomizationFx customization, EClass eClass, EObjectTableDescriptor<T> objectTableDescriptor, List<T> objects) {
    this(customization, eClass, objectTableDescriptor);
    
    setObjects(objects);
  }
  
  public EObjectTable(CustomizationFx customization, EClass eClass, EObjectTableDescriptor<T> objectTableDescriptor) {
    super();

    this.eClass = eClass;
    tableDescriptor = objectTableDescriptor;
    componentFactory = customization.getComponentFactoryFx();
    EPackage ePackage = eClass.getEPackage();
    eFactory = ePackage.getEFactoryInstance();

    if (this.tableDescriptor == null) {
      this.tableDescriptor = createDefaultObjectTableDescriptor();
    }

    completeColumnDescriptors(); // add missing column information

    // Create the table columns.
    for (EObjectTableColumnDescriptorAbstract<T> columnDescriptorAbstract: tableDescriptor.getColumnDescriptors()) {
      createTableColumnAbstract(columnDescriptorAbstract, false);
    }

    // Set the placeholder if specified
    if (objectTableDescriptor != null) {
      String placeHolderText = objectTableDescriptor.getPlaceHolderText();
      if (placeHolderText != null) {
        setPlaceholder(new Label(placeHolderText));
      }
    }

    // SETTING CUSTOM EVENT DISPATCHER TO SCENE
    setEventDispatcher(new DoubleClickEventDispatcher(getEventDispatcher()));

    setRowFactory(tv -> {
      TableRow<T> row = new TableRow<>();
      row.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleMouseClickedEvent(row, e));
      row.addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> handleMouseDoubleClickedEvent(row, e));
      return row ;
    });

    setEditable(shallTableBeEditable());

    initObjectSelectionListening();
  }

  public static void addDefaultStringConverter(Class<? extends Object> clazz, StringConverter<Object> stringConverter) {
    defaultStringConverters.put(clazz, stringConverter);
  }
  
  public void addPredicate(Predicate<T> predicate) {
    predicates.add(predicate);
    updateFilter();
  }
  
  public void removePredicate(Predicate<T> predicate) {
    predicates.remove(predicate);
    updateFilter();
  }
  
  /**
   * Check whether the table shall be set to 'editable'.
   * <p>
   * This is the fact if at least one column is editable.<br/>
   * As this method checks the table columns, it only makes sense to call this method after the table columns have been created.
   * @return
   */
  private boolean shallTableBeEditable() {
    for (TableColumn<T, ?> tableColumn: getColumns()) {
      if (tableColumn.isEditable()) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Get the table column identified by a specific Id.
   * <p>
   * All table columns, so also the child columns of group columns, are searched for a matching Id.<br/>
   * The search is a depth first search and the first matching column found is returned. 
   * 
   * @param id The Id to search for. This value may not be null.
   * @return A table column with the specified <code>id</code>, or null if no such column exists.
   */
  public TableColumn<T, ?> getTableColumn(String id) {
    if (id == null) {
      throw new IllegalArgumentException("'id' may not be null");
    }
    
    return getTableColumnById(id, getColumns());
  }
  
  /**
   * Search, recursively, for a column with a specific Id.
   * <p>
   * The search is a depth first search and the first matching column found is returned. 
   * 
   * @param id The Id to search for.
   * @param columns the list of columns to search in.
   * @return A table column with the specified <code>id</code>, or null if no such column exists.
   */
  private TableColumn<T, ?> getTableColumnById(String id, ObservableList<TableColumn<T, ?>> columns) {
    for (TableColumn<T, ?> tableColumn: columns) {
      if (id.equals(tableColumn.getId())) {
        return tableColumn;
      }
      if (tableColumn.getColumns() != null) {
        TableColumn<T, ?> childTableColumn = getTableColumnById(id, tableColumn.getColumns());
        if (childTableColumn != null) {
          return childTableColumn;
        }
      }
    }
      
    return null;
  }
  
  /**
   * Create a table column for any type (normal or group) and add this to the table (if applicable).
   * 
   * @param columnDescriptorAbstract the column descriptor
   * @param inGroup if true, the column will not be added to the table (as it is added to a group).
   * @return the created table column.
   */
  private TableColumn<T, ?> createTableColumnAbstract(EObjectTableColumnDescriptorAbstract<T> columnDescriptorAbstract, boolean inGroup) {
    LOGGER.info("=> columnName=" + columnDescriptorAbstract.getColumnName());
    
    TableColumn<T, ?> tableColumn = null;
    
    if (columnDescriptorAbstract instanceof EObjectTableColumnDescriptorCheckBox) {
      tableColumn = createTableColumn((EObjectTableColumnDescriptorCheckBox<T>) columnDescriptorAbstract);
    } else if (columnDescriptorAbstract instanceof EObjectTableColumnDescriptorBase) {
      tableColumn = createTableColumn((EObjectTableColumnDescriptorBase<T>) columnDescriptorAbstract);
    } else if (columnDescriptorAbstract instanceof EObjectTableColumnGroupDescriptor) {
      EObjectTableColumnGroupDescriptor<T> eObjectTableColumnGroupDescriptor = (EObjectTableColumnGroupDescriptor<T>) columnDescriptorAbstract;
      tableColumn = new TableColumn<>(eObjectTableColumnGroupDescriptor.getColumnName());
      for (EObjectTableColumnDescriptorAbstract<T> childColumnDescriptorAbstract: eObjectTableColumnGroupDescriptor.getChildColumnDescriptors()) {
        tableColumn.getColumns().add(createTableColumnAbstract(childColumnDescriptorAbstract, true));
      }
    }
    
    if (tableColumn != null  &&  !inGroup) {
      getColumns().add(tableColumn);
    }
    
    return tableColumn;
  }
  
  /**
   * Create a 'normal' table column, based on a column descriptor
   * 
   * The kind of cell used for a column depends on the data type for the column, whether the column can be edited and whether a cell factory is specified in the column descriptor.
   * If a cell factory is specified in the descriptor, this is used. Otherwise it is determined as follows:
   * 
   * 
   * @param columnDescriptor a specification of the column to be created.
   * @return the newly created column.
   */
  @SuppressWarnings("unchecked")
  private TableColumn<T, ?> createTableColumn(EObjectTableColumnDescriptorBase<T> columnDescriptor) {
    LOGGER.info("=> columnName=" + columnDescriptor.getColumnName());


    List<ETypedElement> eTypedElements = columnDescriptor.getETypedElements();
    
//    if (columnDescriptor.getCellFactory() != null) {
//      TableColumn<T, Object> objectTableColumn = new TableColumn<>(columnDescriptor.getColumnName());
//      EStructuralFeatureValueCellFactory<T, Object> defaultCellFactory = new EStructuralFeatureValueCellFactory<T, Object>(eTypedElement);
//      objectTableColumn.setCellValueFactory(defaultCellFactory);
//      objectTableColumn.setCellFactory(columnDescriptor.getCellFactory());
//      tableColumn = objectTableColumn;
//    } else {
    
    // Determine the column class, used for ...
    Class<?> columnClass = getColumnClass(columnDescriptor);
    
    // Create a column for this class
    // It would be best to specify a column data type (type columnClass), but with generics you can only use a type which is known at compile time.
    // So we just use <T, Object>.
    TableColumn<T, Object> tableColumn = new TableColumn<T, Object>(columnDescriptor.getColumnName());
    
    if (columnDescriptor.getId() != null) {
      tableColumn.setId(columnDescriptor.getId());
    } else {
      tableColumn.setId(columnDescriptor.getColumnName());
    }
    
    // Set the cell value factory
    // This is always an EStructuralFeatureValueCellFactory. If needed, the column descriptor can be extended to specify a value factory.
    // Again it would be best to specify the return type as columnClass, but again this isn't possible with generics. So we just use return type Object.
    tableColumn.setCellValueFactory(new EStructuralFeatureValueCellFactory<T, Object>(eTypedElements));
    
    // Set the cell factory
    // If a cell factory is specified in the column descriptor, use this. In this case no StringConverter is used.
    // Else the value is rendered as text. If the column is editable, a TextFieldTableCell is used, else the (default) TableCell is used.
    if (columnDescriptor instanceof EObjectTableColumnDescriptorCustom) {
      EObjectTableColumnDescriptorCustom<T> eObjectTableColumnDescriptorCustom = (EObjectTableColumnDescriptorCustom<T>) columnDescriptor;
      if (eObjectTableColumnDescriptorCustom.getCellFactory() != null) {
        tableColumn.setCellFactory(eObjectTableColumnDescriptorCustom.getCellFactory());
      }
      
      
//      if (columnDescriptor.getColumnName().equals("Play")) {
//        tableColumn.setCellFactory(tc -> {
//          TableCell<T, Object> cell = new TableCell<>() {
//            @Override
//            protected void updateItem(Object item, boolean empty) {
//              super.updateItem(item, empty) ;
//              setText(empty ? null : item.toString());
//            }
//          };
//          cell.setOnMouseClicked(e -> {
//            System.out.println("Mouse clicked");
//            if (! cell.isEmpty()) {
//              Object userId = cell.getItem();
//            }
//          });
//          return cell ;
//        });
//      }
    }
    
    
    StringConverter<Object> stringConverter = null;
    if (columnDescriptor instanceof EObjectTableColumnDescriptorBasic) {
      stringConverter = (StringConverter<Object>) ((EObjectTableColumnDescriptorBasic<T>) columnDescriptor).getStringConverter();
    } else if (columnDescriptor instanceof EObjectTableColumnDescriptorChoiceBox) {
      stringConverter = (StringConverter<Object>) ((EObjectTableColumnDescriptorChoiceBox<T>) columnDescriptor).getStringConverter();
    }
    if (stringConverter == null) {
      String columnClassName = columnClass.getName();
      switch (columnClassName) {
      case "java.lang.Integer":
        stringConverter = new IntegerObjectStringConverter();
        break;
        
      case "java.util.Date":
        stringConverter = new DateObjectStringConverter();
        break;
        
      default:
        stringConverter = new AnyTypeStringConverter<Object>();
        break;
      }
    }
    final StringConverter<Object> finalStringConverter = stringConverter;
    
    if (columnDescriptor.isEditable()) {
      
      
      if (columnDescriptor instanceof EObjectTableColumnDescriptorBasic) {
        
        tableColumn.setCellFactory(new Callback<TableColumn<T, Object>, TableCell<T, Object>>() {
          @Override
          public TableCell<T, Object> call(TableColumn<T, Object> tableColumn) {
            return new TextFieldTableCell<T, Object>(finalStringConverter);
          }
        });
        
      } else if (columnDescriptor instanceof EObjectTableColumnDescriptorChoiceBox) {
        final EObjectTableColumnDescriptorChoiceBox<T> eObjectTableColumnDescriptorChoiceBox = (EObjectTableColumnDescriptorChoiceBox<T>) columnDescriptor;
        
        tableColumn.setCellFactory(new Callback<TableColumn<T, Object>, TableCell<T, Object>>() {
          
          @Override
          public TableCell<T, Object> call(TableColumn<T, Object> tableColumn) {
            ChoiceBoxTableCell<T, Object> cell = new ChoiceBoxTableCell<>(eObjectTableColumnDescriptorChoiceBox.getItems()) {
              
              @Override
              public void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if(!empty && (item != null)) {
                  setText(getConverter().toString(item));
                }
              }
              
            };
            
            cell.setConverter(finalStringConverter);

            return cell;
          }
        });
        
      }
      

      tableColumn.setOnEditCommit(event -> {
        LOGGER.severe("Commit: " + event.toString());
        final Object value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
        T t = event.getTableView().getItems().get(event.getTablePosition().getRow());
        EAttribute eAttribute = (EAttribute) eTypedElements.get(0);
        t.eSet(eAttribute, value);
        refresh();
      });
    } else {
      if (columnDescriptor instanceof EObjectTableColumnDescriptorBasic) {

        tableColumn.setCellFactory(new Callback<TableColumn<T, Object>, TableCell<T, Object>>() {
          @Override
          public TableCell<T, Object> call(TableColumn<T, Object> tableColumn) {
            TextFieldTableCell<T, Object> tableCell = new TextFieldTableCell<>(finalStringConverter);
            tableCell.setEditable(false);
            return tableCell;
          }
        });

      }
    }

    if (columnDescriptor.isEditable()) {
      tableColumn.setOnEditCommit(event -> {
        LOGGER.severe("Commit: " + event.toString());
        final Object value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
        T t = event.getTableView().getItems().get(event.getTablePosition().getRow());
        EObject eObjectToSet = t;
        LOGGER.info("eObjectToSet=" + eObjectToSet.toString());
        for (int i = 0; i < eTypedElements.size() - 1; i++) {
          EStructuralFeature eStructuralFeature = (EStructuralFeature) eTypedElements.get(i);
          LOGGER.severe("eStructuralFeature=" + eStructuralFeature.getName());
          EObject nextEObjectToSet = (EObject) eObjectToSet.eGet(eStructuralFeature);
          LOGGER.info("in loop nextEObjectToSet=" + nextEObjectToSet);
          
          if (nextEObjectToSet == null) {
            EClass eClass = eObjectToSet.eClass();
            LOGGER.severe("eClass=" + eClass);
            EPackage ePackage = eClass.getEPackage();
            LOGGER.severe("ePackage=" + ePackage);
            EFactory eFactory = ePackage.getEFactoryInstance();
            LOGGER.severe("eFactory=" + eFactory);
            EClass structuralFeatureEClass = (EClass) eStructuralFeature.getEType();
            LOGGER.severe("structuralFeatureEClass=" + structuralFeatureEClass);
             EObject newEObject = eFactory.create(structuralFeatureEClass);
            LOGGER.severe("Created: " + newEObject);
            eObjectToSet.eSet(eStructuralFeature, newEObject);
            eObjectToSet = newEObject;
          } else {
            eObjectToSet = nextEObjectToSet;
          }
        }
        LOGGER.info("after loop eObjectToSet=" + eObjectToSet.toString());
        eObjectToSet.eSet((EStructuralFeature) eTypedElements.get(eTypedElements.size() - 1), value);
        refresh();
      });
    }    
    
    
//    switch (columnClass.getName()) {
//    case "java.lang.String":
//      TableColumn<T, String> stringTableColumn = new TableColumn<>(columnDescriptor.getColumnName());
//      tableColumn = stringTableColumn;
//      EStructuralFeatureValueCellFactory<T, String> cellFactory = new EStructuralFeatureValueCellFactory<T, String>(eTypedElement);
//      stringTableColumn.setCellValueFactory(cellFactory);
//      if (columnDescriptor.isEditable()) {
//        stringTableColumn.setCellFactory(TextFieldTableCell.<T>forTableColumn());

//        stringTableColumn.setOnEditCommit(event -> {
//          final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
//          T t = event.getTableView().getItems().get(event.getTablePosition().getRow());
//          EAttribute eAttribute = (EAttribute) eTypedElement;
//          t.eSet(eAttribute, value);
//          refresh();
//        });
//      } // else use the default label cell
//      break;

//    case "java.util.Date":
//      if (columnDescriptor.isEditable()) {
//        TableColumn<T, Date> dateTableColumn = new TableColumn<>(columnDescriptor.getColumnName());
//        tableColumn = dateTableColumn;
//        EStructuralFeatureValueCellFactory<T, Date> dateCellFactory = new EStructuralFeatureValueCellFactory<T, Date>(eTypedElement);
//        dateTableColumn.setCellValueFactory(dateCellFactory);
//        dateTableColumn.setCellFactory(TextFieldTableCell.<T, Date>forTableColumn(new DateStringConverter("dd-MM-yyyy")));
//        tableColumn = dateTableColumn;
//      } // else use the default label cell
//      break;

//    default:
//      LOGGER.severe("Column type: " + columnClass.getName() + " is not supported.");
//      TableColumn<T, Object> objectTableColumn = new TableColumn<>(columnDescriptor.getColumnName());
//      EStructuralFeatureValueCellFactory<T, Object> defaultCellFactory = new EStructuralFeatureValueCellFactory<T, Object>(eTypedElement);
//      objectTableColumn.setCellValueFactory(defaultCellFactory);
//      tableColumn = objectTableColumn;
//    }
//    }
    
//    if (eTypedElement == null) {
//      tableColumn.setCellValueFactory(new RowObjectValueCellFactory());
//    } else if (eTypedElement instanceof EOperation  ||  eTypedElement instanceof EStructuralFeature) {
//      tableColumn.setCellValueFactory(new EStructuralFeatureValueCellFactory<T>(eTypedElement));
//    }
    
    tableColumn.setVisible(columnDescriptor.isVisible());
    tableColumn.setSortable(true);
    tableColumn.setSortType(TableColumn.SortType.ASCENDING);
    Integer minimumWidth = columnDescriptor.getMinimumWidth();
    if (minimumWidth != null) {
      tableColumn.setMinWidth(minimumWidth);
    }

    return tableColumn;
  }
  
  /**
   * Create a 'normal' table column, based on a column descriptor
   * 
   * The kind of cell used for a column depends on the data type for the column, whether the column can be edited and whether a cell factory is specified in the column descriptor.
   * If a cell factory is specified in the descriptor, this is used. Otherwise it is determined as follows:
   * 
   * 
   * @param columnDescriptor a specification of the column to be created.
   * @return the newly created column.
   */
  private TableColumn<T, ?> createTableColumn(EObjectTableColumnDescriptorCheckBox<T> columnDescriptor) {
    LOGGER.info("=> columnName=" + columnDescriptor.getColumnName());

    List<ETypedElement> eTypedElements = columnDescriptor.getETypedElements();
            
    // Create a column for this class
    TableColumn<T, Boolean> tableColumn = new TableColumn<>(columnDescriptor.getColumnName());
    
    // A checkbox cell works different than all others, there is no cell value factory, as there is no edit mode with commit.
    // Instead you have to provide an ObservableBoolean.
    CheckBoxCellHelper<T> checkBoxCellHelper = new CheckBoxCellHelper<>(this, eTypedElements);
    
    // Set the cell factory
    tableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxCellHelper::call));
      
    if (columnDescriptor.isEditable()) {
      tableColumn.setOnEditCommit(event -> {
        LOGGER.severe("Commit: " + event.toString());
        final Object value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
        T t = event.getTableView().getItems().get(event.getTablePosition().getRow());
        EObject eObjectToSet = t;
        LOGGER.severe("eObjectToSet=" + eObjectToSet.toString());
        for (int i = 0; i < eTypedElements.size() - 1; i++) {
          EStructuralFeature eStructuralFeature = (EStructuralFeature) eTypedElements.get(i);
          LOGGER.severe("eStructuralFeature=" + eStructuralFeature.getName());
          EObject nextEObjectToSet = (EObject) eObjectToSet.eGet(eStructuralFeature);
          LOGGER.severe("in loop nextEObjectToSet=" + nextEObjectToSet);
          
          if (nextEObjectToSet == null) {
            EClass eClass = eObjectToSet.eClass();
            LOGGER.severe("eClass=" + eClass);
            EPackage ePackage = eClass.getEPackage();
            LOGGER.severe("ePackage=" + ePackage);
            EFactory eFactory = ePackage.getEFactoryInstance();
            LOGGER.severe("eFactory=" + eFactory);
            EClass structuralFeatureEClass = (EClass) eStructuralFeature.getEType();
            LOGGER.severe("structuralFeatureEClass=" + structuralFeatureEClass);
             EObject newEObject = eFactory.create(structuralFeatureEClass);
            LOGGER.severe("Created: " + newEObject);
            eObjectToSet.eSet(eStructuralFeature, newEObject);
            eObjectToSet = newEObject;
          } else {
            eObjectToSet = nextEObjectToSet;
          }
        }
        LOGGER.severe("after loop eObjectToSet=" + eObjectToSet.toString());
        eObjectToSet.eSet((EStructuralFeature) eTypedElements.get(eTypedElements.size() - 1), value);
        refresh();
      });
    }    
        
    tableColumn.setVisible(columnDescriptor.isVisible());
    tableColumn.setSortable(true);
    tableColumn.setSortType(TableColumn.SortType.ASCENDING);
    tableColumn.setOnEditCommit(null);
    Integer minimumWidth = columnDescriptor.getMinimumWidth();
    if (minimumWidth != null) {
      tableColumn.setMinWidth(minimumWidth);
    }

    return tableColumn;
  }
  
  /**
   * Get the effective table descriptor.
   * 
   * @return the effective table descriptor.
   */
  public EObjectTableDescriptor<T> getTableDescriptor() {
    return tableDescriptor;
  }
  
  /**
   * Set the list of EObjects to be shown in the table.
   * 
   * @param objects object (items) to be shown in the table.
   */
  public void setObjects(List<T> objects) {
    if (objects == null) {
      setObjects(null, null);
    } else {
      // Create the EPackage
      EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

      EClass listContainerClass = EcoreFactory.eINSTANCE.createEClass();
      listContainerClass.setName("ListContainer");
      EList<EStructuralFeature> structuralFeatures = listContainerClass.getEStructuralFeatures();

      EReference reference = EcoreFactory.eINSTANCE.createEReference();
      reference.setName("reference");
      reference.setEType(EcorePackage.eINSTANCE.getEEList());
      structuralFeatures.add(reference);

      ePackage.getEClassifiers().add(listContainerClass);

      EFactory factory = ePackage.getEFactoryInstance();
      EObject listContainer = factory.create(listContainerClass);
      listContainer.eSet(reference, objects);

      setObjects(listContainer, reference);
    }
    
//    
//    this.containingObject = containingObject;
//    this.objects = objects;
//    
//    if (objects == null) {
//      setItems(null);
//      return;
//    }
//    
//    EReference objectsEReference = null;
//    for (EReference eReference: containingObject.eClass().getEAllReferences()) {
//      if (containingObject.eGet(eReference) == objects) {
//        LOGGER.severe("Found reference: " + eReference.getName());
//        objectsEReference = eReference;
//        break;
//      }
//    }
//        
//    // For the sorting and filtering to work, we need to wrap an ObservableList in a FilteredList and then in a SortedList.
//    // And bind the SortedList comparator to the TableView comparator.
//    // In order to support default sorting, based on the specified comparator, a second SortedList between the FilteredList and the SortedList is needed.
//    
//    if ((objects instanceof EObjectContainmentEList)  &&  (containingObject != null)) {
////      observableObjects = new ObservableEList<>(containingObject, (EObjectContainmentEList<T>) objects);
//      observableObjects = new ObservableEList<>(false, containingObject, objectsEReference);
//    } else {
//      observableObjects = FXCollections.observableList(objects);
//    }
//            
//    filteredObjects = new FilteredList<>(observableObjects);
//    if (tableDescriptor.getFilterPredicate() != null) {
//      filteredObjects.setPredicate(tableDescriptor.getFilterPredicate());
//    }
//    Comparator<T> comparator = tableDescriptor.getComparator();
//    SortedList<T> comparatorBasedSortedList  = new SortedList<>(filteredObjects, comparator);
//    tableSortedList = new SortedList<>(comparatorBasedSortedList);
//    tableSortedList.comparatorProperty().bind(comparatorProperty());
//    
////    if (observableObjects instanceof ObservableEList) {
////      ((ObservableEList<T>) observableObjects).setPresentationList(tableSortedList);
////    }
//
//    setItems(tableSortedList);
//    ListChangeListener<T> l = new ListChangeListener<>() {
//
//      @Override
//      public void onChanged(Change<? extends T> arg0) {
//        LOGGER.severe("=>");
//        refresh();
//        
//      }
//
//      
//    };
//    LOGGER.severe("adding listener");
//    tableSortedList.addListener(l);
  }
  
  /**
   * Set the list of EObjects to be shown in the table.
   * 
   * @param objects object (items) to be shown in the table.
   */
  public void setObjects(EObject containingObject, EReference eReference) {
    
    this.containingObject = containingObject;
//    this.objects = objects;
    
    if (eReference == null) {
      setItems(null);
      return;
    }
        
    // For the sorting and filtering to work, we need to wrap an ObservableList in a FilteredList and then in a SortedList.
    // And bind the SortedList comparator to the TableView comparator.
    // In order to support default sorting, based on the specified comparator, a second SortedList between the FilteredList and the SortedList is needed.
    
    observableObjects = new ObservableEList<>(false, containingObject, eReference);
            
    filteredObjects = new FilteredList<>(observableObjects);
    if (tableDescriptor.getFilterPredicate() != null) {
      filteredObjects.setPredicate(tableDescriptor.getFilterPredicate());
    }
    Comparator<T> comparator = tableDescriptor.getComparator();
    SortedList<T> comparatorBasedSortedList  = new SortedList<>(filteredObjects, comparator);
    tableSortedList = new SortedList<>(comparatorBasedSortedList);
    tableSortedList.comparatorProperty().bind(comparatorProperty());
    
//    if (observableObjects instanceof ObservableEList) {
//      ((ObservableEList<T>) observableObjects).setPresentationList(tableSortedList);
//    }

    setItems(tableSortedList);
//    ListChangeListener<T> l = new ListChangeListener<>() {
//
//      @Override
//      public void onChanged(Change<? extends T> arg0) {
//        LOGGER.severe("=>");
//        refresh();
//        
//      }
//
//      
//    };
    ((ObservableEList<T>) observableObjects).addTableRefreshNeededListener(o -> refresh());
  }
  
//  private void handleMouseClickedEvent(TableColumn column, MouseEvent mouseEvent) {
//    LOGGER.severe("column: " + column + ", mouseEvent: " + mouseEvent);
//
////    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
////      //T rowData = row.getItem();
////      LOGGER.severe("row=" + row.toString());
////
////      handleRowClicked(row.getItem());
////    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
////      ContextMenu contextMenu = createContextMenu(row);
////      if (contextMenu != null) {
////        contextMenu.show(row, mouseEvent.getScreenX(), mouseEvent.getScreenY());
////      }
////    }
//  }
        
  private void handleMouseClickedEvent(TableRow<T> row, MouseEvent mouseEvent) {
    if (row.isEmpty()) {
      return;
    }

    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
//      T rowData = row.getItem();
//      LOGGER.severe("row=" + row.toString());
//      
//      handleRowClicked(row.getItem());
    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      ContextMenu contextMenu = createContextMenu(row);
      if (contextMenu != null) {
        contextMenu.show(row, mouseEvent.getScreenX(), mouseEvent.getScreenY());
      }
      mouseEvent.consume();
    }
  }
  
  private String rowToString(TableRow<T> row) {
   return null;
  }

  private void handleMouseDoubleClickedEvent(TableRow<T> row, MouseEvent mouseEvent) {
    if (row.isEmpty()) {
      return;
    }

    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
      T rowData = row.getItem();
      handleRowDoubleClicked(rowData);
    }
  }

  /**
   * Create a context menu for a specific row.
   * 
   * @param row the table row for which the context menu is to be created.
   * @return the newly created context menu.
   */
  protected ContextMenu createContextMenu(TableRow<T> row) {
    Map<TableRowOperation, TableRowOperationDescriptor<T>> rowOperations = tableDescriptor.getRowOperations();

    if (rowOperations == null) {
      return null;
    }
    
    ContextMenu contextMenu = componentFactory.createContextMenu();
    MenuItem menuItem;
    Menu menu;
    int rowIndex = row.getIndex();
    
    TableRowOperationDescriptor<T> tableRowOperationDescriptor;

    if (rowIndex != -1) {
      EmfPackageHelper emfPackageHelper = new EmfPackageHelper(eClass.getEPackage());
      List<EClass> subTypes = emfPackageHelper.getSubTypes(eClass);
      if (subTypes != null) {
        subTypes.add(0, eClass);
      }
      
      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.OPEN);
      if (tableRowOperationDescriptor != null) {
        menuItem = componentFactory.createMenuItem(tableRowOperationDescriptor.getMenuText());
        menuItem.setOnAction((ActionEvent event) -> {
          openObject(row.getItem());
        });
        contextMenu.getItems().add(menuItem);
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.NEW_OBJECT_BEFORE);
      if (tableRowOperationDescriptor != null) {
        if (subTypes != null  &&  subTypes.size() > 1) {
          menu = createSubClassesMenu(tableRowOperationDescriptor.getMenuText(), subTypes, rowIndex, true);
          contextMenu.getItems().add(menu);
        } else {
          menuItem = componentFactory.createMenuItem(tableRowOperationDescriptor.getMenuText());
          menuItem.setOnAction((ActionEvent event) -> {
            createNewObject(rowIndex, true);
          });
          contextMenu.getItems().add(menuItem);
        }
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.NEW_OBJECT_AFTER);
      if (tableRowOperationDescriptor != null) {
        menuItem = componentFactory.createMenuItem(tableRowOperationDescriptor.getMenuText());
        menuItem.setOnAction((ActionEvent event) -> {
          createNewObject(rowIndex, false);
        });
        contextMenu.getItems().add(menuItem);
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.DELETE_OBJECT);
      if (tableRowOperationDescriptor != null) {
        menuItem = componentFactory.createMenuItem(tableRowOperationDescriptor.getMenuText());
        menuItem.setOnAction((ActionEvent event) -> {
          deleteObject(row.getItem());
        });
        contextMenu.getItems().add(menuItem);
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.MOVE_OBJECT_UP);
      if (tableRowOperationDescriptor != null) {
        menuItem = componentFactory.createMenuItem(tableRowOperationDescriptor.getMenuText());
        menuItem.setOnAction((ActionEvent event) -> {
          moveObject(row.getItem(), true);
        });
        contextMenu.getItems().add(menuItem);
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.MOVE_OBJECT_DOWN);
      if (tableRowOperationDescriptor != null) {
        menuItem = componentFactory.createMenuItem(tableRowOperationDescriptor.getMenuText());
        menuItem.setOnAction((ActionEvent event) -> {
          moveObject(row.getItem(), false);
        });
        contextMenu.getItems().add(menuItem);
      }

      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.EXTENDED_OPERATION);
      if (tableRowOperationDescriptor != null) {
        menuItem = componentFactory.createMenuItem(tableRowOperationDescriptor.getMenuText());
        final BiConsumer<List<T>, T> consumer = tableRowOperationDescriptor.getConsumer();
        menuItem.setOnAction((ActionEvent event) -> {
          LOGGER.info("Extended operation");
          LOGGER.info("objects class" + (objects != null ? objects.getClass().getName() : "<null>"));
          consumer.accept(objects, row.getItem());
//          this.setObjects(containingObject, objects);
        });
        contextMenu.getItems().add(menuItem);
      }

    } else {
//      Object o = rowOperations.get(TableRowOperation.NIEUW_OBJECT);
//      LOGGER.info("o: " + o);
//      tableRowOperationDescriptor = rowOperations.get(TableRowOperation.NIEUW_OBJECT);
//      if (tableRowOperationDescriptor != null) {
//        MenuFactory.addMenuItem(popup, tableRowOperationDescriptor.getMenuText(), new ActionListener()  {
//          public void actionPerformed(ActionEvent e) {
//            createNewObject(row, false);
//          }
//        });
//      }
    }

    return contextMenu;
  }
  
  private Menu createSubClassesMenu(String menuText, List<EClass> classes, int atIndex, boolean before) {
    Menu menu = componentFactory.createMenu(menuText);
    MenuItem menuItem;
    
    for (EClass eClass: classes) {
      // As abstract classes cannot be instantiated, they don't belong in this menu.
      if (eClass.isAbstract()) {
        continue;
      }
      
      menuItem = componentFactory.createMenuItem(eClass.getName());
      
      menuItem.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          @SuppressWarnings("unchecked")
          T newItem = (T) eFactory.create(eClass);
          LOGGER.severe("newItem: " + newItem.getClass().getName());
          addObject(newItem, atIndex, before);
        }
        
      });
      
      menu.getItems().add(menuItem);
    }
    
    return menu;
  }

  protected void handleRowClicked(T row) {
    LOGGER.severe("=> row=" + row);
//    this.edit(row.getIndex(), row.getco);
  }

  protected void handleRowDoubleClicked(T rowData) {
    LOGGER.severe("Seems handleRowDoubleClicked has not been overwritten!");
  }

  /**
   * Override this method to open an object.
   */
  public void openObject(T object) {
  }

  public void createNewObject(int atIndex, boolean before) {
    T item = createItem();
    addObject(item, atIndex, before);
  }
  
  private void addObject(T object, int atIndex, boolean before) {
//    if (before) {
//      objects.add(atIndex, object);
//    } else {
//      // insert before the next element, if it exists
//      int nextRow = atIndex + 1;
//      if (nextRow < objects.size()) {
//        objects.add(nextRow, object);
//      } else {
//        objects.add(object);
//      }
//    }
//    
//    setObjects(objects);
//    
//    findObject(object);
  }

  private void moveObject(T item, boolean up) {
//    int currentPosition = objects.indexOf(item);
//    int newPosition;
//    if (up) {
//      newPosition = currentPosition - 1;
//    } else {
//      newPosition = currentPosition + 1;
//    }
////    objects.move(newPosition, currentPosition);
//    
//    
//    setObjects(objects);
//    
////    findObject(item);
  }
  
  /**
   * Delete an object
   * 
   * @param objectToRemove the object to be removed.
   */
  public void deleteObject(T objectToRemove) {
    LOGGER.severe("Object to remove=" + objectToRemove.toString());
    
    ResourceSet resourceSet = null;
    Resource resource = objectToRemove.eResource();
    if (resource != null) {
      resourceSet = resource.getResourceSet();
    }
    
    // TODO handle cross references when there is no resource set.
    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(objectToRemove, resourceSet);
    if (settings.size() != 0) {
      StringBuffer buf = new StringBuffer();
      buf.append("There are ");
      buf.append(settings.size());
      buf.append(" references to this object.");
      buf.append(NEW_LINE);

      for (Object object: settings) {
        EStructuralFeature.Setting setting = (EStructuralFeature.Setting) object;
        EStructuralFeature feature = setting.getEStructuralFeature();
        String inOrAs = " as ";
        if (feature.isMany()) {
          inOrAs = " in ";
        }
        EObject referringObject = setting.getEObject();
        EObject container = referringObject.eContainer();
        buf.append(container.getClass().getName());
        buf.append(" in ");
        buf.append(referringObject.toString());
        buf.append(inOrAs);
        buf.append(setting.getEStructuralFeature().getName());
        buf.append(NEW_LINE);
      }
      
      Alert alert = componentFactory.createOkCancelConfirmationDialog("How to continue?", buf.toString(), "What do you want?");

      ButtonType buttonContinue = new ButtonType("Continue");
      alert.getButtonTypes().remove(ButtonType.OK);
      alert.getButtonTypes().add(buttonContinue);

      Optional<ButtonType> result = alert.showAndWait();
      
      if (result.get() == ButtonType.CANCEL) {
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
        
    int removeIndex = observableObjects.indexOf(objectToRemove);
    int indexToScrollTo = removeIndex - 1;
    if (indexToScrollTo < 0) {
      indexToScrollTo = 0;
    }
    
    boolean removed = observableObjects.remove(objectToRemove);
    LOGGER.severe("Object removed=" + removed);
    EList<EReference> references = objectToRemove.eClass().getEAllReferences();
    for (EReference reference: references) {
      Object object = objectToRemove.eGet(reference);
      if (object instanceof List) {
        List<?> list = (List<?>) object;
        if (list.isEmpty()) {
          continue;
        }
      }
      LOGGER.severe("Reference = " + reference.getName());
      objectToRemove.eUnset(reference);
    }
    
    T objectToScrollTo = observableObjects.get(indexToScrollTo);
    
    
//    setObjects(containingObject, observableObjects);
    
    findObject(objectToScrollTo);
  }
    
  /**
   * Find a specific object in the table.
   * <p>
   * The items in the table are searched for the object and if found, the object is selected and made visible.
   * 
   * @param object the object to be found
   */
  public boolean findObject(T object) {
    LOGGER.info("=> object=" + object.toString());
    TableViewSelectionModel<T> selectionModel = getSelectionModel();
    selectionModel.select(object);
    T selectedObject = selectionModel.getSelectedItem();
    if (object.equals(selectedObject)) {
      scrollTo(object);
      LOGGER.severe("<= selection succeeded");
      return true;
    } else {
      LOGGER.severe("<= selection failed");
      return false;
    }
  }
  
  @SuppressWarnings("unchecked")
  public T createItem() {
    return (T) eFactory.create(eClass);
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
   * Get the first selected object (row) in the table.
   * 
   * @return the first selected object.
   */
  public T getSelectedObject() {
    return getSelectionModel().getSelectedItem();
  }
  
  /*
   * End of implementation of ObjectSelectionListener.
   */
  
  /**
   * Install a ListSelectionListener on the SelectionModel of the table for the implementation of the ObjectSelectionListener interface.
   * In case of a selection change, {@link #handleValueChanged} is called.
   */
  private void initObjectSelectionListening() {
    this.getSelectionModel().selectedItemProperty().addListener(object -> handleSelectionChanged());
  }
  
  /**
   * Notify ObjectSelectionListeners about the newly selected object.
   */
  private void handleSelectionChanged() {
    LOGGER.info("=>");
    T selectedObject = getSelectedObject();
    
    for (ObjectSelectionListener<T> objectSelectionListener: objectSelectionListeners) {
      LOGGER.info("Calling: " + objectSelectionListener);
      objectSelectionListener.objectSelected(this, selectedObject);
    }
  }

  /**
   * Create the default table descriptor.
   * <p>
   * The created descriptor has the following properties:
   * <ul>
   * <li>placeHolderText - not set</li>
   * <li>comparator - not set</li>
   * <li>columnDescriptors<br/>
   * One descriptor (of type {@link EObjectTableColumnDescriptorBasic}) for each structural feature (obtained via <code>eClass.getEAllStructuralFeatures()</code>), with the following properties:
   *         <ul>
   *         <li>
   *         eStructuralFeature the <code>EStructuralFeature</code>
   *         </li>
   *         <li>
   *         <code>columnName</code> the name of the <code>structuralFeature</code> (obtained by <code>getName()</code>).
   *         </li>
   *         <li>
   *         <code>isEditable</code> set to <code>true</code>.
   *         </li>
   *         <li>
   *         <code>isVisible</code> set to <code>true</code>.
   *         </li>
   *         </ul>
   * </li>
   * <li>rowOperations - none</li>
   * </ul>
   * 
   * @return the newly created table descriptor.
   */
  private EObjectTableDescriptor<T> createDefaultObjectTableDescriptor() {
    EObjectTableDescriptor<T> eObjectTableDescriptor = new EObjectTableDescriptor<T>(null, null, createDefaultColumnDescriptors(), null);
    
    return eObjectTableDescriptor;
  }

  /**
   * Create default column descriptors.
   * <p/>
   * A column descriptor is created for each structural feature (obtained via <code>eClass.getEAllStructuralFeatures()</code>).</br>
   * 
   * @return an array of {@link EObjectTableColumnDescriptorBasic}, where for each column descriptor the following information is filled in:
   *         <ul>
   *         <li>
   *         eStructuralFeature the <code>EStructuralFeature</code>
   *         </li>
   *         <li>
   *         <code>columnName</code> the name of the <code>structuralFeature</code> (obtained by <code>getName()</code>).
   *         </li>
   *         <li>
   *         <code>isEditable</code> set to <code>true</code>.
   *         </li>
   *         <li>
   *         <code>isVisible</code> set to <code>true</code>.
   *         </li>
   *         </ul>
   */
  private List<EObjectTableColumnDescriptorAbstract<T>> createDefaultColumnDescriptors() {
    LOGGER.info("=>");
    
    List<EStructuralFeature> allStructuralFeatures = eClass.getEAllStructuralFeatures();
    List<EObjectTableColumnDescriptorAbstract<T>> columnDescriptors = new ArrayList<>();
    
    for (EStructuralFeature structuralFeature: allStructuralFeatures) {
      EClassifier eType = structuralFeature.getEType();
      if (eType.getClassifierID() == EcorePackage.EBOOLEAN) {
        EObjectTableColumnDescriptorCheckBox<T> columnDescriptor = new EObjectTableColumnDescriptorCheckBox<>(structuralFeature, structuralFeature.getName(), true, true);
        LOGGER.info("Adding column descriptor: " + columnDescriptor.toString());
        columnDescriptors.add(columnDescriptor);
      } else {
        EObjectTableColumnDescriptorBasic<T> columnDescriptor = new EObjectTableColumnDescriptorBasic<T>(structuralFeature, structuralFeature.getName(), true, true);
        LOGGER.info("Adding column descriptor: " + columnDescriptor.toString());
        columnDescriptors.add(columnDescriptor);
      }
    }
    
    LOGGER.info("<=");
    return columnDescriptors;
  }
  
  /**
   * Complete the column descriptors where needed.
   * <p>
   * If there are no column descriptors, a set of default column descriptors is created.<br/>
   * If there are column descriptors, where the name isn't specified, a name is added (using the name of the structural feature).
   */
  private void completeColumnDescriptors() {
      if (tableDescriptor.getColumnDescriptors() == null) {
        tableDescriptor.setColumnDescriptors(createDefaultColumnDescriptors());
      }

      // Fill in missing column names, using the name of the structural feature.
      for (EObjectTableColumnDescriptorAbstract<T> columnDescriptorAbstract: tableDescriptor.getColumnDescriptors()) {
        if ((columnDescriptorAbstract instanceof EObjectTableColumnDescriptorBasic) && (columnDescriptorAbstract.getColumnName() == null)) {
          EObjectTableColumnDescriptorBasic<T> eObjectTableColumnDescriptor = (EObjectTableColumnDescriptorBasic<T>) columnDescriptorAbstract;
          eObjectTableColumnDescriptor.setColumnNameAbstract(eObjectTableColumnDescriptor.getETypedElements().get(0).getName());
          LOGGER.severe("Default name filled in for column: " + eObjectTableColumnDescriptor.getColumnName());
        }
      }
  }
  
  /**
   * Determine the data type of a column.
   * 
   * @param columnDescriptor
   * @return
   */
  public Class<?> getColumnClass(EObjectTableColumnDescriptorBase<T> columnDescriptor) {
    LOGGER.info("=>");
    List<ETypedElement> eTypedElements = columnDescriptor.getETypedElements();
    ETypedElement eTypedElement = eTypedElements.get(eTypedElements.size() - 1);
    if (eTypedElement instanceof EAttribute) {
      EAttribute eAttribute = (EAttribute) eTypedElement;
      LOGGER.info("eAttribute=" + eAttribute.getName());
      if (eAttribute.isMany()) {
        LOGGER.info("<= EAttribute.Many.class = " + EList.class);
        return EList.class;
      }
      if (eAttribute.getEType().getInstanceClass() == boolean.class) {
        LOGGER.info("<= class = " + Boolean.class);
        return Boolean.class;
      } else if (eAttribute.getEType().getInstanceClass() == float.class) {
        LOGGER.info("<= class = " + Float.class);
        return Float.class;
      } else if (eAttribute.getEType().getInstanceClass() == int.class) {
        LOGGER.info("<= class = " + Integer.class);
        return Integer.class;
      }
      LOGGER.info("<= EAttribute.class = " + eAttribute.getEType().getInstanceClass());
      return eAttribute.getEType().getInstanceClass();
    } else if (eTypedElement instanceof EReference) {
      EReference eReference = (EReference) eTypedElement;
      LOGGER.info("<= EReference.class = " + eReference.getEReferenceType().getInstanceClass());
      return eReference.getEReferenceType().getInstanceClass();
    } else if (eTypedElement instanceof EOperation) {
      EOperation eOperation = (EOperation) eTypedElement;
      return eOperation.getEType().getInstanceClass();
    }
    
    LOGGER.info("<= class = " + Object.class);
    
    return Object.class;
  }

  public void setFilterExpression(String regex, EStructuralFeature eStructuralFeature) {
    Predicate<T> predicate;
    
    tableFilterPredicate = new TableFilterPredicate<T>(regex.toLowerCase(), eStructuralFeature);
    updateFilter();
    
    if (tableDescriptor.getFilterPredicate() != null) {
      predicate = tableDescriptor.getFilterPredicate().and(tableFilterPredicate);
    } else {
      predicate = tableFilterPredicate;
    }
    
    filteredObjects.setPredicate(predicate);
  }
  
  private void updateFilter() {
    Predicate<T> totalPredicate;
    
    totalPredicate = tableDescriptor.getFilterPredicate();
    
    if (tableFilterPredicate != null) {
      if (totalPredicate == null) {
        totalPredicate = tableFilterPredicate;
      } else {
        totalPredicate = totalPredicate.and(tableFilterPredicate);
      }
    }
    
    for (Predicate<T> predicate: predicates) {
      if (totalPredicate == null) {
        totalPredicate = predicate;
      } else {
        totalPredicate = totalPredicate.and(predicate);
      }
    }
    
    filteredObjects.setPredicate(totalPredicate);
  }
  
  class RowObjectValueCellFactory implements Callback<TableColumn.CellDataFeatures<T, Object>, ObservableValue<Object>> {
    @Override
    public ObservableValue<Object> call(CellDataFeatures<T, Object> arg0) {
      return new SimpleObjectProperty<>(arg0.getValue());
    }

  }

}

class RowMouseEventHandler implements EventHandler<MouseEvent> {

  @Override
  public void handle(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }
  
}

/**
 * This class is a Predicate used to filter rows from the table.
 *
 * @param <T> The type of the items in the table.
 */
class TableFilterPredicate<T extends EObject> implements Predicate<T> {
  private static final Logger LOGGER = Logger.getLogger(TableFilterPredicate.class.getName());

  /**
   * The filter expression.
   */
  private String regex;
  
  /**
   * The EStructuralFeature to filter on.
   */
  private EStructuralFeature eStructuralFeature;
  
  /**
   * Stack of EObjects to check for cycles.
   */
  private Stack<EObject> handledObjects = new Stack<>();
  
  /**
   * Constructor.
   * 
   * @param regex The filter expression (text to filter on).
   * @param eStructuralFeature The EStructuralFeature to filter on, or null to filter on all attributes of the item (whether shown in the table or not).
   */
  public TableFilterPredicate(String regex, EStructuralFeature eStructuralFeature) {
    this.regex = regex;
    this.eStructuralFeature = eStructuralFeature;
  }

  @Override
  public boolean test(EObject eObject) {
    LOGGER.info("=>" );
    
    for (EStructuralFeature currentEStructuralFeature: eObject.eClass().getEAllStructuralFeatures()) {
      if (eStructuralFeature != null  &&  !currentEStructuralFeature.equals(eStructuralFeature)) {
        continue;
      }
      
      LOGGER.info("eStructuralFeature=" + currentEStructuralFeature.getName());
      if (currentEStructuralFeature instanceof EAttribute) {
        EAttribute eAttribute = (EAttribute) currentEStructuralFeature;
        LOGGER.info("eAttribute=" + eAttribute.getName());
        Object attribute = eObject.eGet(eAttribute);
        if (attribute != null) {
          String attributeValue = attribute.toString();
          LOGGER.info("attributeValue=" + attributeValue);
          if ((attributeValue != null)  &&  attributeValue.toLowerCase().contains(regex)) {
            LOGGER.info("<= HIT");
            return true;
          }
        }
      } else if (currentEStructuralFeature instanceof EReference) {
        EReference eReference = (EReference) currentEStructuralFeature;
        LOGGER.info("eReference=" + eReference.getName());
        Object referredObject = eObject.eGet(eReference);
        LOGGER.info("referredObject=" + referredObject);
        if (referredObject instanceof EObject) {
          LOGGER.info("is EObject");
          if (!handledObjects.contains(referredObject)) {
            handledObjects.push(eObject);
            boolean result = test((EObject) referredObject);
            handledObjects.pop();
            if (result) {
              return true;
            }
          }
        }
      }
    }
    
    LOGGER.info("<= false");
    return false;
  }
  
}

class CheckBoxCellHelper<T extends EObject> implements Callback<Integer, SimpleBooleanProperty> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(CheckBoxCellHelper.class.getName());

  private final ETypedElement eTypedElement;
  private final TableView<T> tableView;
  
  /**
   * Constructor
   * 
   * @param eTypedElement the attribute, reference, or operation to be used to get the value from the row object.
   */
  public CheckBoxCellHelper(TableView<T> tableView, List<ETypedElement> eTypedElements) {
    if (eTypedElements.size() != 1) {
      throw new IllegalArgumentException("eTypedElements shall have exactly 1 element (here it has " + eTypedElements.size() + ")");
    }
    this.tableView = tableView;
    eTypedElement = eTypedElements.get(0);
  }
  
  @Override
  public SimpleBooleanProperty call(final Integer param) {
    T t = (T) tableView.getItems().get(param);
    Object o = t.eGet((EStructuralFeature) eTypedElement);
    if (o instanceof org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList.ManyInverse manyInverse) {
      LOGGER.severe("ManyInverse: " + o.toString());
    }
    Boolean value = (Boolean) t.eGet((EStructuralFeature) eTypedElement);
    SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(value);
    booleanProperty.addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        T t = (T) tableView.getItems().get(param);
        t.eSet((EStructuralFeature) eTypedElement, newValue);
      }
      
    });
    
    return booleanProperty;
  }

}
