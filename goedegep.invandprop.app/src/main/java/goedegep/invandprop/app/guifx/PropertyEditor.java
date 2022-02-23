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
        
    EObjectAttributeEditDescriptor[] eObjectAttributeEditDescriptors = {
        new EObjectAttributeEditDescriptor(DESCRIPTION_ID, componentFactory.createObjectInputString(null, 150.0, false, "the property description", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Description().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Description()),
        new EObjectAttributeEditDescriptor(BRAND_ID, componentFactory.createObjectInputString(null, 150.0, true, "the brand of the property", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Brand().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Brand()),
        new EObjectAttributeEditDescriptor(TYPE_ID, componentFactory.createObjectInputString(null, 150.0, true, "the property type", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Type().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Type()),
        new EObjectAttributeEditDescriptor(SERIAL_NUMBER_ID, componentFactory.createObjectInputString(null, 150.0, true, "the serial number of the property", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_SerialNumber().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_SerialNumber()),
        new EObjectAttributeEditDescriptor(REMARKS_ID, componentFactory.createObjectInputString(null, 150.0, true, "any comments on this property", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Remarks().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Remarks()),
        new EObjectAttributeEditDescriptor(FROM_DATE_ID, componentFactory.createObjectInputFlexDate(null, 150.0, true, "date from when you own(ed) the property", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_FromDate().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_FromDate()),
        new EObjectAttributeEditDescriptor(UNTIL_DATE_ID, componentFactory.createObjectInputFlexDate(null, 150.0, true, "date until when you owned the property", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_UntilDate().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_UntilDate()),
        new EObjectAttributeEditDescriptor(ARCHIVE_ID, componentFactory.createObjectInputBoolean(null, false, true, "select if you don't own the property anymore", INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Archive().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Archive())
    };
    
    getEObjectAttributeEditDescriptors().addAll(Arrays.asList(eObjectAttributeEditDescriptors));
  }
}

