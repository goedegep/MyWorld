package goedegep.gpx.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import goedegep.appgen.TableRowOperation;
import goedegep.gpx.model.GPXPackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.resources.ImageResource;
import goedegep.util.emf.EmfPackageHelper;

public class GPXTreeView extends EObjectTreeView {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(GPXTreeView.class.getName());
  

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param eObject the GPX data
   */
  public GPXTreeView(CustomizationFx customization, EObject eObject) {
    super(eObject, new GPXTreeViewDescriptor(customization), false);
    
  }
  
}


class GPXTreeViewDescriptor extends EObjectTreeDescriptor {
  private static GPXPackage GPX_PACKAGE = GPXPackage.eINSTANCE;
    
  public GPXTreeViewDescriptor(CustomizationFx customization) {
    EmfPackageHelper gpxPackageHelper = new EmfPackageHelper(GPX_PACKAGE);
    
    createAndAddEObjectTreeDescriptorForDocumentRoot(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForGpxType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForWptType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForRteType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForTrkType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForTrksegType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForMetadataType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForBoundsType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForPersonType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForCopyrightType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForEmailType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForLinkType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForExtensionsType(this, gpxPackageHelper);
    createAndAddEObjectTreeDescriptorForEStringToStringMapEntry(this, gpxPackageHelper);
    
  }
  
  
  /**
   * Create the descriptor for the EClass goedegep.gpx.model.DocumentRoot.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForDocumentRoot(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.DocumentRoot");
        
    // DocumentRoot is root node
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Document root", true, null);
    
    // DocumentRoot.mixed - not shown, as the information is also available under GPX
    
    // DocumentRoot.XMLNSPrefixMap
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getDocumentRoot_XMLNSPrefixMap(), "XMLNSPrefixMap", false, null));
    
    // DocumentRoot.XSISchemaLocation
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getDocumentRoot_XSISchemaLocation(), "XSISchemaLocation", false, null));

    // DocumentRoot.gpx
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getDocumentRoot_Gpx(), gpxPackageHelper.getEClass("goedegep.gpx.model.GpxType"), (eObject) -> "GPX", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.GpxType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForGpxType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.GpxType");
        
    // GpxType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "GPX (gpx)", false, null, eObject -> {
      return ImageResource.GPX.getImage();
    });
    
    // GpxType.metadata
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create Metadata"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getGpxType_Metadata(), gpxPackageHelper.getEClass("goedegep.gpx.model.MetadataType"), (eObject) -> "Meta data (metadata)", true, nodeOperationDescriptors));

    // GpxType.version
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getGpxType_Version(), "Version", PresentationType.SINGLE_LINE_TEXT, null));

    // GpxType.creator
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getGpxType_Creator(), "Creator", PresentationType.SINGLE_LINE_TEXT, null));
    
    // GpxType.wpt
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getGpxType_Wpt(), "Waypoints (wpt)", false, null));
    
    // GpxType.rte
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getGpxType_Rte(), "Routes (rte)", false, null));
    
    // GpxType.trk
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getGpxType_Trk(), "Tracks (trk)", false, null));
    
    // GpxType.extensions
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getGpxType_Extensions(), gpxPackageHelper.getEClass("goedegep.gpx.model.ExtensionsType"), (eObject) -> "Extensions (extensions)", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.WptType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForWptType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.WptType");
        
    // WptType
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Waypoint", false, nodeOperationDescriptors, eObject -> {
      return ImageResource.LOCATION_FLAG_YELLOW.getImage();
    });

    // WptType.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Name(), "Name (name)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.cmt
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Cmt(), "Comments (cmt)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.desc
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Desc(), "Description (desc)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.lat
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Lat(), "Latitude (lat)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.lon
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Lon(), "Longitude (lon)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.ele
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Ele(), "Elevation (ele)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.time
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Ele(), "Time (time)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.src
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Src(), "Source (src)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.magvar
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Magvar(), "Magnetic variance (magvar)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.geoidheight
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Geoidheight(), "Geoid height (geoidheight)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.sym
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Sym(), "Sym (sym)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.type
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Type(), "Type (type)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.fix
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Fix(), "Fix (fix)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.sat
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Sat(), "Number of Satellites (sat)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.hdop
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Hdop(), "Hdop (hdop)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.vdop
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Vdop(), "Vdop (vdop)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.pdop
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Pdop(), "Pdop (dpop)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.ageofdgpsdata
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Ageofdgpsdata(), "Age ofd gps data (ageofdgpsdata)", PresentationType.SINGLE_LINE_TEXT, null));

    // WptType.dgpsid
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Dgpsid(), "Dgpsid (dgpsid)", PresentationType.SINGLE_LINE_TEXT, null));
    
    // WptType.link
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getWptType_Link(), "Links (link)", false, null));
    
    // WptType.extensions
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getWptType_Extensions(), gpxPackageHelper.getEClass("goedegep.gpx.model.ExtensionsType"), (eObject) -> "Extensions (extensions)", false, null));
   
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.RteType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForRteType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.RteType");
        
    // RteType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Route (rte)", false, null);

    // RteType.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Name(), "Name (name)", PresentationType.SINGLE_LINE_TEXT, null));

    // RteType.cmt
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Cmt(), "Comments (cmt)", PresentationType.SINGLE_LINE_TEXT, null));

    // RteType.desc
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Desc(), "Description (desc)", PresentationType.SINGLE_LINE_TEXT, null));

    // RteType.src
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Src(), "Source (src)", PresentationType.SINGLE_LINE_TEXT, null));

    // RteType.number
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Number(), "Number (number)", PresentationType.SINGLE_LINE_TEXT, null));

    // RteType.type
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Type(), "Type (type)", PresentationType.SINGLE_LINE_TEXT, null));
    
    // WptType.rtept
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getRteType_Rtept(), "Route points (rtept)", false, null));
    
    // WptType.link
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getRteType_Link(), "Links (link)", false, null));
    
    // WptType.extensions
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getRteType_Extensions(), gpxPackageHelper.getEClass("goedegep.gpx.model.ExtensionsType"), (eObject) -> "Extensions (extensions)", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.TrkType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForTrkType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.TrkType");
        
    // TrkType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Track (trk)", false, null);

    // TrkType.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Name(), "Name (name)", PresentationType.SINGLE_LINE_TEXT, null));

    // TrkType.cmt
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Cmt(), "Comments (cmt)", PresentationType.SINGLE_LINE_TEXT, null));

    // TrkType.desc
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Desc(), "Description (desc)", PresentationType.SINGLE_LINE_TEXT, null));

    // TrkType.src
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Src(), "Source (src)", PresentationType.SINGLE_LINE_TEXT, null));

    // TrkType.number
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Number(), "Number (number)", PresentationType.SINGLE_LINE_TEXT, null));

    // TrkType.type
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Type(), "Type (type)", PresentationType.SINGLE_LINE_TEXT, null));
    
    // TrkType.trkseg
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getTrkType_Trkseg(), "Track segments (trkseg)", false, null));
    
    // TrkType.link
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getTrkType_Link(), "Links (link)", false, null));
    
    // TrkType.extensions
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getTrkType_Extensions(), gpxPackageHelper.getEClass("goedegep.gpx.model.ExtensionsType"), (eObject) -> "Extensions (extensions)", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.TrksegType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForTrksegType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.TrksegType");
        
    // TrksegType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Track segment (trkseg)", false, null);
    
    // TrksegType.trkpt
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getTrksegType_Trkpt(), "Track points (trkpt)", false, null));
    
    // TrksegType.extensions
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getTrksegType_Extensions(), gpxPackageHelper.getEClass("goedegep.gpx.model.ExtensionsType"), (eObject) -> "Extensions (extensions)", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.MetadataType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForMetadataType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.MetadataType");
        
    // MetadataType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Meta data (metadata)", false, null);

    // MetadataType.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Name(), "Name (name)", PresentationType.SINGLE_LINE_TEXT, null));

    // MetadataType.desc
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Desc(), "Description (desc)", PresentationType.SINGLE_LINE_TEXT, null));

    // MetadataType.time
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Time(), "Time (time)", PresentationType.SINGLE_LINE_TEXT, null));

    // MetadataType.keywords
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Keywords(), "Keywords (keywords)", PresentationType.SINGLE_LINE_TEXT, null));

    // MetadataType.bounds
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Bounds(), gpxPackageHelper.getEClass("goedegep.gpx.model.BoundsType"), (eObject) -> "Bounds (bounds)", false, null));
    
    // MetadataType.author
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Author(), gpxPackageHelper.getEClass("goedegep.gpx.model.PersonType"), (eObject) -> "Author (author)", false, null));
    
    // MetadataType.copyright
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Copyright(), gpxPackageHelper.getEClass("goedegep.gpx.model.CopyrightType"), (eObject) -> "Copyright (copyright)", false, null));

    // MetadataType.link
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getMetadataType_Link(), "Links (link)", false, null));
    
    // MetadataType.extensions
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Extensions(), gpxPackageHelper.getEClass("goedegep.gpx.model.ExtensionsType"), (eObject) -> "Extensions (extensions)", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.BoundsType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForBoundsType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.BoundsType");
        
    // BoundsType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Bounds (bounds)", false, null);

    // BoundsType.minlat
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Minlat(), "Minimum latitude (minlat)", PresentationType.SINGLE_LINE_TEXT, null));

    // BoundsType.minlon
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Minlon(), "Minimum longitude (minlon)", PresentationType.SINGLE_LINE_TEXT, null));

    // BoundsType.maxlat
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Maxlat(), "Maximum latitude (maxlat)", PresentationType.SINGLE_LINE_TEXT, null));

    // BoundsType.maxlon
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Maxlon(), "Maximum longitude (maxlon)", PresentationType.SINGLE_LINE_TEXT, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.PersonType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForPersonType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.PersonType");
        
    // PersonType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Bounds (bounds)", false, null);

    // PersonType.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getPersonType_Name(), "Name (name)", PresentationType.SINGLE_LINE_TEXT, null));

    // PersonType.email
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getPersonType_Email(), gpxPackageHelper.getEClass("goedegep.gpx.model.EmailType"), (eObject) -> "Email (email)", false, null));

    // PersonType.link
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getPersonType_Link(), gpxPackageHelper.getEClass("goedegep.gpx.model.LinkType"), (eObject) -> "Link (link)", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.CopyrightType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForCopyrightType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.CopyrightType");
        
    // CopyrightType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Bounds (bounds)", false, null);

    // CopyrightType.year
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getCopyrightType_Year(), "Year (year)", PresentationType.SINGLE_LINE_TEXT, null));

    // CopyrightType.license
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getCopyrightType_License(), "License (license)", PresentationType.SINGLE_LINE_TEXT, null));

    // CopyrightType.author
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getCopyrightType_Author(), "Author (author)", PresentationType.SINGLE_LINE_TEXT, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.EmailType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForEmailType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.EmailType");
        
    // EmailType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Email (email)", false, null);

    // EmailType.domain
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getEmailType_Domain(), "Domain (domain)", PresentationType.SINGLE_LINE_TEXT, null));

    // EmailType.id
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getEmailType_Id(), "Id (id)", PresentationType.SINGLE_LINE_TEXT, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.LinkType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForLinkType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.LinkType");
        
    // LinkType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Link (link)", false, null);

    // LinkType.text
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getLinkType_Text(), "Text (text)", PresentationType.SINGLE_LINE_TEXT, null));

    // LinkType.type
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getLinkType_Type(), "Type (type)", PresentationType.SINGLE_LINE_TEXT, null));

    // LinkType.href
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getLinkType_Href(), "Href (href)", PresentationType.SINGLE_LINE_TEXT, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.ExtensionsType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForExtensionsType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EClass eClass = gpxPackageHelper.getEClass("goedegep.gpx.model.ExtensionsType");
        
    // ExtensionsType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Extensions (extensions)", false, null);

    // ExtensionsType.any
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeListDescriptor(GPX_PACKAGE.getExtensionsType_Any(), "Any (any)", false, null, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.GpxType.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForEStringToStringMapEntry(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper gpxPackageHelper) {
    EReference eReference = GPX_PACKAGE.getDocumentRoot_XSISchemaLocation();
    EClass eClass = eReference.getEReferenceType();
        
    // EStringToStringMapEntry
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Entry", false, null);

    // EStringToStringMapEntry.key
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor((EAttribute) eClass.getEStructuralFeature("key"), "Key", PresentationType.SINGLE_LINE_TEXT, null));

    // EStringToStringMapEntry.value
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor((EAttribute) eClass.getEStructuralFeature("value"), "Value", PresentationType.SINGLE_LINE_TEXT, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

}

