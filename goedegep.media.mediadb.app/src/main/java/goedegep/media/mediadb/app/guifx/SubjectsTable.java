package goedegep.media.mediadb.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Subject;
import goedegep.util.string.StringUtil;
import javafx.scene.layout.Background;
import javafx.util.StringConverter;

/**
 * This class provides a table to list Subjects.
 */
public class SubjectsTable extends EObjectTable<Subject> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(SubjectsTable.class.getName());
 
  /**
   * Constructor
   * 
   * @param customization  the GUI customization.
   * @param filmDbWindow a reference to the film database window
   * @param mediaDb the media database from which the albums are listed
   */
  public SubjectsTable(CustomizationFx customization) {
    super(customization, MediadbPackage.eINSTANCE.getSubject(), new SubjectsTableDescriptor(customization), null);
        
    setTableMenuButtonVisible(true);
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
    
  }

}

/**
 * This class is the table descriptor for the FilmsTable.
 */
class SubjectsTableDescriptor extends EObjectTableDescriptor<Subject> {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  
  /*
   * The complete list of column descriptors
   */
  private List<EObjectTableColumnDescriptorAbstract<Subject>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Subject>>of(
      new EObjectTableColumnDescriptorBasic<Subject>(MEDIA_DB_PACKAGE.getSubject_Date(), "Date", true, true),
      new EObjectTableColumnDescriptorBasic<Subject>(MEDIA_DB_PACKAGE.getSubject_Title(), "Title", true, true),
      new EObjectTableColumnDescriptorBasic<Subject>(MEDIA_DB_PACKAGE.getSubject_Tags(), "Tags", 300, true, true, new StringConverter<List<String>>() {

        @Override
        public String toString(List<String> tags) {
          return StringUtil.stringCollectionToCommaSeparatedStrings(tags);
          
         }

        @Override
        public List<String> fromString(String string) {
          return StringUtil.commaSeparatedValuesToListOfValues(string);
        }
        
      })      
  );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<Subject>> rowOperations = new HashMap<>() {
    {
      put(TableRowOperation.NEW_OBJECT, new TableRowOperationDescriptor<>("New subject"));
      put(TableRowOperation.NEW_OBJECT_BEFORE, new TableRowOperationDescriptor<>("New subject before this one"));
      put(TableRowOperation.NEW_OBJECT_AFTER, new TableRowOperationDescriptor<>("New subject after this one"));
      put(TableRowOperation.MOVE_OBJECT_UP, new TableRowOperationDescriptor<>("Move subject up"));
      put(TableRowOperation.MOVE_OBJECT_DOWN, new TableRowOperationDescriptor<>("Move subject down"));
      put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete subject"));
    }
  };
    
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  SubjectsTableDescriptor(CustomizationFx customization) {
    super();
    
    setColumnDescriptors(columnDescriptors);
    setRowOperations(rowOperations);
  }
  
}


