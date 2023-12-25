package goedegep.vacations.app.guifx;

import goedegep.jfx.eobjecttreeview.EEnumEditorDescriptor;
import goedegep.poi.model.POICategoryId;

/**
 * This class is a singleton that provides an instance of an {@link EEnumEditorDescriptor} for POI's (based on the {@link POICategoryId}).
 */
public class EEnumEditorDescriptorForPOIs {
  private static EEnumEditorDescriptor<POICategoryId> eEnumEditorDescriptor = null;
  
  /**
   * Private constructor
   */
  private EEnumEditorDescriptorForPOIs() {
  }

  /**
   * Get the EEnumEditorDescriptor instance.
   * 
   * @return the EEnumEditorDescriptor instance.
   */
  public static EEnumEditorDescriptor<POICategoryId> getInstance() {
    if (eEnumEditorDescriptor == null) {
      eEnumEditorDescriptor = new EEnumEditorDescriptor<>(true);

      for (POICategoryId poiCategoryId: POICategoryId.values()) {
        eEnumEditorDescriptor.addDisplayNameForEEnum(poiCategoryId, poiCategoryId.getLiteral());
      }
    }

    return eEnumEditorDescriptor;
  }
}
