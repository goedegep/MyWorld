package goedegep.gpx.app;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;

import goedegep.gpx.model.GPXPackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorDelete;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorNew;
import goedegep.resources.ImageResource;

public class GPXTreeViewCreator extends EObjectTreeView {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(GPXTreeViewCreator.class.getName());
  
  private final GPXPackage GPX_PACKAGE = GPXPackage.eINSTANCE;

  /**
   * Create an {@link EObjectTreeView} for a GPX track ({@link DocumentRoot}).
   * 
   * @param customization the GUI customization.
   * @return an {@code EObjectTreeView} for a GPX track ({@code DocumentRoot}).
   */
  public EObjectTreeView createGPXTreeView(CustomizationFx customization) {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .setCustomization(customization)
        .addEClassDescriptor(GPX_PACKAGE.getDocumentRoot(), createDescriptorForDocumentRoot())
        .addEClassDescriptor(GPX_PACKAGE.getGpxType(), createDescriptorForGpxType())
        .addEClassDescriptor(GPX_PACKAGE.getWptType(), createDescriptorForWptType())
        .addEClassDescriptor(GPX_PACKAGE.getRteType(), createDescriptorForRteType())
        .addEClassDescriptor(GPX_PACKAGE.getTrkType(), createDescriptorForTrkType())
        .addEClassDescriptor(GPX_PACKAGE.getTrksegType(), createDescriptorForTrksegType())
        .addEClassDescriptor(GPX_PACKAGE.getMetadataType(), createDescriptorForMetadataType())
        .addEClassDescriptor(GPX_PACKAGE.getBoundsType(), createDescriptorForBoundsType())
        .addEClassDescriptor(GPX_PACKAGE.getPersonType(), createAndAddEObjectTreeDescriptorForPersonType())
        .addEClassDescriptor(GPX_PACKAGE.getCopyrightType(), createDescriptorForCopyrightType())
        .addEClassDescriptor(GPX_PACKAGE.getEmailType(), createDescriptorForEmailType())
        .addEClassDescriptor(GPX_PACKAGE.getLinkType(), createDescriptorForLinkType())
        .addEClassDescriptor(GPX_PACKAGE.getExtensionsType(), createDescriptorForExtensionsType())
        .addEClassDescriptor(GPX_PACKAGE.getDocumentRoot_XSISchemaLocation().getEReferenceType(), createDescriptorForEStringToStringMapEntry());

    return eObjectTreeView;
  }  
  
  /**
   * Create the descriptor for the EClass goedegep.gpx.model.DocumentRoot.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForDocumentRoot() {
    // DocumentRoot is root node
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Document root")
        .setExpandOnCreation(true);

    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
    
    // DocumentRoot.mixed - not shown, as the information is also available under GPX
    
    // DocumentRoot.XMLNSPrefixMap
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getDocumentRoot_XMLNSPrefixMap())
        .setLabelText("XMLNSPrefixMap");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // DocumentRoot.XSISchemaLocation
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getDocumentRoot_XSISchemaLocation())
        .setLabelText("XSISchemaLocation");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    // DocumentRoot.gpx
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getDocumentRoot_Gpx())
        .setNodeTextFunction(eObject -> "GPX");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.GpxType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForGpxType() {
    // GpxType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "GPX (gpx)")
        .setNodeIconFunction(eObject -> {
          return ImageResource.GPX.getImage();
        });
    
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor;
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
    
    // GpxType.metadata
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getGpxType_Metadata())
        .setNodeTextFunction(eObject -> "Meta data (metadata)")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("Create Metadata", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // GpxType.version
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getGpxType_Version())
        .setLabelText("Version");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // GpxType.creator
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getGpxType_Creator())
        .setLabelText("Creator");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // GpxType.wpt
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getGpxType_Wpt())
        .setLabelText("Waypoints (wpt)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // GpxType.rte
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getGpxType_Rte())
        .setLabelText("Routes (rte)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // GpxType.trk
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getGpxType_Trk())
        .setLabelText("Tracks (trk)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // GpxType.extensions
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getGpxType_Extensions())
        .setNodeTextFunction(eObject -> "Extensions (extensions)")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("Create Metadata", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.WptType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForWptType() {
    // WptType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Waypoint")
        .setNodeIconFunction(eObject -> {
          return ImageResource.LOCATION_FLAG_YELLOW.getImage();
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete", null));

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // WptType.name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Name())
        .setLabelText("Name (name)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.cmt
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Cmt())
        .setLabelText("Comments (cmt)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.desc
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Desc())
        .setLabelText("Description (desc)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.lat
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Lat())
        .setLabelText("Latitude (lat)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.lon
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Lon())
        .setLabelText("Longitude (lon)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.ele
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Ele())
        .setLabelText("Elevation (ele)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.time
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Time())
        .setLabelText("Time (time)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.src
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Src())
        .setLabelText("Source (src)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.magvar
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Magvar())
        .setLabelText("Magnetic variance (magvar)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.geoidheight
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Geoidheight())
        .setLabelText("Geoid height (geoidheight)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.sym
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Sym())
        .setLabelText("Sym (sym)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.type
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Type())
        .setLabelText("Type (type)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.fix
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Fix())
        .setLabelText("Fix (fix)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.sat
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Sat())
        .setLabelText("Number of Satellites (sat)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.hdop
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Hdop())
        .setLabelText("Hdop (hdop)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.vdop
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Vdop())
        .setLabelText("Vdop (vdop)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.pdop
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Pdop())
        .setLabelText("Pdop (dpop)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.ageofdgpsdata
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Ageofdgpsdata())
        .setLabelText("Age of dgps data (ageofdgpsdata)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // WptType.dgpsid
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getWptType_Dgpsid())
        .setLabelText("Dgpsid (dgpsid)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // WptType.link
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getWptType_Link())
        .setLabelText("Links (link)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // WptType.extensions    
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getWptType_Extensions())
        .setNodeTextFunction(eObject -> "Extensions (extensions)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
   
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.RteType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForRteType() {
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
        
    // RteType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Route (rte)");

    // RteType.name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Name())
        .setLabelText("Name (name)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // RteType.cmt
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Cmt())
        .setLabelText("Comments (cmt)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // RteType.desc
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Desc())
        .setLabelText("Description (desc)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // RteType.src
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Src())
        .setLabelText("Source (src)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // RteType.number
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Number())
        .setLabelText("Number (number)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // RteType.type
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getRteType_Type())
        .setLabelText("Type (type)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // WptType.rtept
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getRteType_Rtept())
        .setLabelText("Route points (rtept)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // WptType.link
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getRteType_Link())
        .setLabelText("Links (link)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // WptType.extensions
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getRteType_Extensions())
        .setNodeTextFunction(eObject -> "Extensions (extensions)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.TrkType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForTrkType() {
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
        
    // TrkType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Track (trk)");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // TrkType.name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Name())
        .setLabelText("Name (name)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // TrkType.cmt
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Cmt())
        .setLabelText("Comments (cmt)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // TrkType.desc
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Desc())
        .setLabelText("Description (desc)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // TrkType.src
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Src())
        .setLabelText("Source (src)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // TrkType.number
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Number())
        .setLabelText("Number (number)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // TrkType.type
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getTrkType_Type())
        .setLabelText("Type (type)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // TrkType.trkseg
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getTrkType_Trkseg())
        .setLabelText("Track segments (trkseg)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // TrkType.link
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getTrkType_Link())
        .setLabelText("Links (link)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // TrkType.extensions
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getTrkType_Extensions())
        .setNodeTextFunction(eObject -> "Extensions (extensions)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.TrksegType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForTrksegType() {
    // TrksegType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Track segment (trkseg)");
    
    // TrksegType.trkpt
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getTrksegType_Trkpt())
        .setLabelText("Track points (trkpt)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // TrksegType.extensions
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getTrksegType_Extensions())
        .setNodeTextFunction(eObject -> "Extensions (extensions)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.MetadataType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForMetadataType() {
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor;
        
    // MetadataType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Meta data (metadata)");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // MetadataType.name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Name())
        .setLabelText("Name (name)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MetadataType.desc
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Desc())
        .setLabelText("Description (desc)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MetadataType.time
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Time())
        .setLabelText("Time (time)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MetadataType.keywords
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getMetadataType_Keywords())
        .setLabelText("Keywords (keywords)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MetadataType.bounds
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Bounds())
        .setNodeTextFunction(eObject -> "Bounds (bounds)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    // MetadataType.author
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Author())
        .setNodeTextFunction(eObject -> "Author (author)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    // MetadataType.copyright
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Copyright())
        .setNodeTextFunction(eObject -> "Copyright (copyright)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);

    // MetadataType.link
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(GPX_PACKAGE.getMetadataType_Link())
        .setLabelText("Links (link)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // MetadataType.extensions
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getMetadataType_Extensions())
        .setNodeTextFunction(eObject -> "Extensions (extensions)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.BoundsType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForBoundsType() {
    // BoundsType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Bounds (bounds)");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // BoundsType.minlat
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Minlat())
        .setLabelText("Minimum latitude (minlat)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // BoundsType.minlon
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Minlon())
        .setLabelText("Minimum longitude (minlon)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // BoundsType.maxlat
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Maxlat())
        .setLabelText("Maximum latitude (maxlat)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // BoundsType.maxlon
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getBoundsType_Maxlon())
        .setLabelText("Maximum longitude (maxlon)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.PersonType.
   */
  private EObjectTreeItemClassDescriptor createAndAddEObjectTreeDescriptorForPersonType() {
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor;
        
    // PersonType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Bounds (bounds)");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // PersonType.name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getPersonType_Name())
        .setLabelText("Name (name)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // PersonType.email
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getPersonType_Email())
        .setNodeTextFunction(eObject -> "Email (email)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);

    // PersonType.link
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(GPX_PACKAGE.getPersonType_Link())
        .setNodeTextFunction(eObject -> "Link (link)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.CopyrightType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForCopyrightType() {
    // CopyrightType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Bounds (bounds)");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // CopyrightType.year
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getCopyrightType_Year())
        .setLabelText("Year (year)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // CopyrightType.license
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getCopyrightType_License())
        .setLabelText("License (license)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // CopyrightType.author
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getCopyrightType_Author())
        .setLabelText("Author (author)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.EmailType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForEmailType() {
    // EmailType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Email (email)");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // EmailType.domain
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getEmailType_Domain())
        .setLabelText("Domain (domain)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // EmailType.id
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getEmailType_Id())
        .setLabelText("Id (id)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.LinkType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForLinkType() {
    // LinkType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Link (link)");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // LinkType.text
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getLinkType_Text())
        .setLabelText("Text (text)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // LinkType.type
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getLinkType_Type())
        .setLabelText("Type (type)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // LinkType.href
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(GPX_PACKAGE.getLinkType_Href())
        .setLabelText("Href (href)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.ExtensionsType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForExtensionsType() {
    // ExtensionsType
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Extensions (extensions)");

    // ExtensionsType.any
    EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = new EObjectTreeItemAttributeListDescriptor(GPX_PACKAGE.getExtensionsType_Any())
        .setLabelText("Any (any)");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeListDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.gpx.model.GpxType.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForEStringToStringMapEntry() {
    EClass eClass = GPX_PACKAGE.getDocumentRoot_XSISchemaLocation().getEReferenceType();
        
    // EStringToStringMapEntry
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Entry");

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // EStringToStringMapEntry.key
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor((EAttribute) eClass.getEStructuralFeature("key"))
        .setLabelText("Key");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // EStringToStringMapEntry.value
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor((EAttribute) eClass.getEStructuralFeature("value"))
        .setLabelText("Value");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

}

