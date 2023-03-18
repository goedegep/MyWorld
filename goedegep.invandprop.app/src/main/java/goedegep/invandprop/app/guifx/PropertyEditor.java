package goedegep.invandprop.app.guifx;

import java.util.Arrays;

import org.eclipse.emf.common.util.EList;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Property;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecteditor.EObjectAttributeEditDescriptor;
import goedegep.jfx.eobjecteditor.EObjectEditor;
import goedegep.jfx.eobjecteditor.EObjectEditorDescriptor;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlString;

public class PropertyEditor extends EObjectEditor<Property> {
    
  public PropertyEditor(CustomizationFx customization, EList<Property> properties) {
    super(customization, InvAndPropPackage.eINSTANCE.getProperty(), new PropertyEditorDescriptor(customization),  properties);
  }
  
}

class PropertyEditorDescriptor extends EObjectEditorDescriptor {
  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;

  public static final String DESCRIPTION_ID = "Description";
  public static final String BRAND_ID = "Brand";
  public static final String TYPE_ID = "Type";
  public static final String SERIAL_NUMBER_ID = "Serial number";
  public static final String REMARKS_ID = "Remarks";
  public static final String FROM_DATE_ID = "From";
  public static final String UNTIL_DATE_ID = "Until";
  public static final String ARCHIVE_ID = "Archive";
  
  public PropertyEditorDescriptor(CustomizationFx customization) {
    setWindowTitle("property");
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    ObjectControlString descriptionObjectControl = componentFactory.createObjectControlString(null, 150.0, false, "the property description");
    descriptionObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Description().getName());
    ObjectControlString brandObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "the brand of the property");
    brandObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Brand().getName());
    ObjectControlString typeObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "the property type");
    typeObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Type().getName());
    ObjectControlString serialNumberObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "the serial number of the property");
    serialNumberObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_SerialNumber().getName());
    ObjectControlString remarksObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "any comments on this property");
    remarksObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Remarks().getName());
    ObjectControlFlexDate fromDateObjectControl = componentFactory.createObjectControlFlexDate(null, 150.0, true, "date from when you own(ed) the property");
    fromDateObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_FromDate().getName());
    ObjectControlFlexDate untilDateObjectControl = componentFactory.createObjectControlFlexDate(null, 150.0, true, "date until when you owned the property");
    untilDateObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_UntilDate().getName());
    ObjectControlBoolean archiveObjectControl = componentFactory.createObjectControlBoolean(null, false, true, "select if you don't own the property anymore");
    archiveObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Archive().getName());
        
    EObjectAttributeEditDescriptor[] eObjectAttributeEditDescriptors = {
        new EObjectAttributeEditDescriptor(DESCRIPTION_ID, descriptionObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Description()),
        new EObjectAttributeEditDescriptor(BRAND_ID, brandObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Brand()),
        new EObjectAttributeEditDescriptor(TYPE_ID, typeObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Type()),
        new EObjectAttributeEditDescriptor(SERIAL_NUMBER_ID, serialNumberObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_SerialNumber()),
        new EObjectAttributeEditDescriptor(REMARKS_ID, remarksObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Remarks()),
        new EObjectAttributeEditDescriptor(FROM_DATE_ID, fromDateObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_FromDate()),
        new EObjectAttributeEditDescriptor(UNTIL_DATE_ID, untilDateObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_UntilDate()),
        new EObjectAttributeEditDescriptor(ARCHIVE_ID, archiveObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Archive())
    };
    
    getEObjectAttributeEditDescriptors().addAll(Arrays.asList(eObjectAttributeEditDescriptors));
  }
}

