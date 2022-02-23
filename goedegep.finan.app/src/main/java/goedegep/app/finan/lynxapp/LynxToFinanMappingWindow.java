package goedegep.app.finan.lynxapp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.eclipse.emf.ecore.EClass;

import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.EMFResource;
import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.appgen.eobjecttable.EObjectTableDescriptor;
import goedegep.appgen.eobjecttable.EObjectTableFrame;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.lynx.lynxeffrek.LynxSecurityInfo;
import goedegep.finan.lynx.lynxeffrek.Ofx2Finan;
import goedegep.finan.lynx2finan.model.LynxToFinanFactory;
import goedegep.finan.lynx2finan.model.LynxToFinanPackage;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry;
import goedegep.util.file.FileUtils;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.io.AggregateUnmarshaller;
import net.sf.ofx4j.io.OFXParseException;

@SuppressWarnings("serial")
public class LynxToFinanMappingWindow extends EObjectTableFrame<LynxToFinanShareIdListEntry> {
  private static final String WINDOW_TITLE = "Vertaling Van Lynx Naar Finan";
  private static final Logger LOGGER = Logger.getLogger(LynxToFinanMappingWindow.class.getName());    

  private static LynxToFinanPackage ePackage = LynxToFinanPackage.eINSTANCE;
  private static LynxToFinanFactory LYNX2FINAN_FACTORY = LynxToFinanFactory.eINSTANCE;
  private static EClass         eClass   = ePackage.getLynxToFinanShareIdListEntry();

  private EMFResource<LynxToFinanShareIdList> lynxShareIdToFinanShareNameResource;
  private LynxToFinanShareIdList lynxToFinanShareIdList;
  
  @SuppressWarnings("rawtypes")
  private static Map<TableRowOperation, TableRowOperationDescriptor> rowOperations = new HashMap<TableRowOperation, TableRowOperationDescriptor>() {
    {
      put(TableRowOperation.NEW_OBJECT, new TableRowOperationDescriptor("Nieuwe mapping"));
      put(TableRowOperation.NEW_OBJECT_BEFORE, new TableRowOperationDescriptor("Nieuwe mapping hierna"));
      put(TableRowOperation.NEW_OBJECT_AFTER, new TableRowOperationDescriptor("Nieuwe mapping hierna"));
      put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor("Verwijderen"));
    }
  };
  
  public LynxToFinanMappingWindow(Customization customization, EMFResource<LynxToFinanShareIdList> lynxShareIdToFinanShareNameResource) {
    super(WINDOW_TITLE, customization, new Dimension(800, 600));
    this.lynxShareIdToFinanShareNameResource = lynxShareIdToFinanShareNameResource;
    this.lynxToFinanShareIdList = lynxShareIdToFinanShareNameResource.getEObject();
    initGui();
    init(eClass,
        createTableDescriptor(), lynxToFinanShareIdList, lynxToFinanShareIdList.getEntries());
  }
  
  @Override
  public boolean isSearchAndFilterPanelToBeAdded() {
    return false;
  }
  
  private void initGui() {
    setJMenuBar(createMenuBar());
  }

  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;

    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Bestand
    menu = new JMenu("Bestand");

    // Bestand: Lees effecten informatie van ofx bestanden.
    MenuFactory.addMenuItem(menu, "Lees info uit activity statements", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getInfoFromActivityStatements();
      }
    });

    // Bestand: Opslaan.
    MenuFactory.addMenuItem(menu, "Opslaan", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveLynxToFinanShareIdList();
      }
    });

    menuBar.add(menu);

    return menuBar;
  }
  
  /**
   * Security names change over time.
   * So retrieve them from latest to oldest.
   * Comparing an existing entry with a new entry, will not check the securityName.
   */
  private void getInfoFromActivityStatements() {
    AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
    FileInputStream file = null;
    Map<String, LynxSecurityInfo> lynxSecurityInfos = new HashMap<>();
    
    Path maandOverzichtenPath = Paths.get(FinanRegistry.dataDirectory, "lynx", "maandoverzichten");
    List<Path> yearFolders = new ArrayList<>();
    try (DirectoryStream<Path> jarenStream = Files.newDirectoryStream(maandOverzichtenPath)) {
      for (Path yearFolderPath: jarenStream) {
        yearFolders.add(yearFolderPath);
      }
      Collections.sort(yearFolders);
      Collections.reverse(yearFolders);
      for (Path yearFolder: yearFolders) {
        LOGGER.info("Handling folder: " + yearFolder.getFileName().toString());
        DirectoryStream<Path> maandOverzichtenStream = Files.newDirectoryStream(yearFolder);
        List<Path> maandOverzichtPaths = new ArrayList<>();
        for (Path maandOverzichtPath: maandOverzichtenStream) {
          maandOverzichtPaths.add(maandOverzichtPath);
        }
        Collections.sort(maandOverzichtPaths);
        Collections.reverse(maandOverzichtPaths);
        for (Path maandOverzichtPath: maandOverzichtPaths) {
          String fileExtension = FileUtils.getFileExtension(maandOverzichtPath);
          if (fileExtension.equals(".ofx")) {
            LOGGER.info("Handling maandoverzicht: " + maandOverzichtPath.getFileName().toString());
            try {
                file = new FileInputStream(maandOverzichtPath.toFile().getAbsolutePath());
                ResponseEnvelope envelope = unmarshaller.unmarshal(file);
                
                Ofx2Finan.getBaseSecurityInfos(envelope, lynxSecurityInfos);
            } catch (OFXParseException e) {
              LOGGER.severe("Error: " + e.getMessage());
            }
          } else {
            LOGGER.severe("Skipping file: " + maandOverzichtPath.getFileName().toString());
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    System.out.println(LynxSecurityInfo.lynxSecurityInfoListToString(lynxSecurityInfos));
    
    for (LynxSecurityInfo lynxSecurityInfo: lynxSecurityInfos.values()) {
      LynxToFinanShareIdListEntry existingEntry = null;
      for (LynxToFinanShareIdListEntry currentEntry: lynxToFinanShareIdList.getEntries()) {
        if (currentEntry.getUniqueId().equals(lynxSecurityInfo.uniqueId)) {
          existingEntry = currentEntry;
          break;
        }
      }
      
      if (existingEntry == null) {

        LynxToFinanShareIdListEntry entry = LYNX2FINAN_FACTORY.createLynxToFinanShareIdListEntry();
        entry.setFiId(lynxSecurityInfo.fiId);
        entry.setFinanName("<todo>");
        entry.setSecurityName(lynxSecurityInfo.securityName);
        entry.setTickerSymbol(lynxSecurityInfo.tickerSymbol);
        entry.setUniqueId(lynxSecurityInfo.uniqueId);
        entry.setUniqueIdType(lynxSecurityInfo.uniqueIdType);

        lynxToFinanShareIdList.getEntries().add(entry);
      }
    }
  }
  
  private void saveLynxToFinanShareIdList() {
      try {
        lynxShareIdToFinanShareNameResource.save();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
  }
   
  private static EObjectTableDescriptor<LynxToFinanShareIdListEntry> createTableDescriptor() {
    EObjectTableDescriptor<LynxToFinanShareIdListEntry> adressesTableDescriptor = new EObjectTableDescriptor<LynxToFinanShareIdListEntry>(
        new Dimension(800, 400),
        null, null,
        null, null, null, rowOperations,
        null);
    
    return adressesTableDescriptor;
    
  }
}
