package goedegep.jfx.eobjecttreeview;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;

import goedegep.util.text.Indent;

/**
 * This class provides a presentation descriptor for an EObjectTreeView.
 * <p>
 * The descriptor consists of EObjectTreeItemClassDescriptor's for the classes within an EObject hierarchy.
 * TODO delete this class
 */
public class EObjectTreeDescriptor {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeDescriptor.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * EObjectTreeItemClassDescriptor's per EClass.
   */
  private Map<EClass, EObjectTreeItemClassDescriptor> eClassToClassDescriptorMap = new HashMap<>();
  
  /**
   * EEnumEditorDescriptor's per EEnum.
   */
  private Map<EEnum, EEnumEditorDescriptor<?>> eEnumToEEnumEditorDescriptorMap = new HashMap<>();
  
  /**
   * Constructor to create an empty descriptor.
   * <p>
   * Descriptors per eClass can be added via method {@link #addEClassDescriptor}.
   */
  public EObjectTreeDescriptor() {
  }
  
  
  /**
   * Add an EClass descriptor
   * 
   * @param eClass the EClass for which a descriptor is added.
   * @param eObjectTreeItemClassDescriptor the descriptor for the <code>eClass</code>.
   */
  public void addEClassDescriptor(EClass eClass, EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor) {
    eClassToClassDescriptorMap.put(eClass, eObjectTreeItemClassDescriptor);
  }
    
  /**
   * Get the descriptor for an {@code EClass}.
   * 
   * @param eClass the {@code EClass} for which a descriptor is requested.
   * @return the {@code EObjectTreeItemClassDescriptor} for the {@code eClass}, or null if there is no descriptor for the {@code eClass}.
   */
  public EObjectTreeItemClassDescriptor getDescriptorForEClass(EClass eClass) {
    return eClassToClassDescriptorMap.get(eClass);
  }
  
  
  public Map<EClass, EObjectTreeItemClassDescriptor> getEClassToClassDescriptorMap() {
    return eClassToClassDescriptorMap;
  }
  
  public Map<EEnum, EEnumEditorDescriptor<?>> getEEnumToEEnumEditorDescriptorMap() {
    return eEnumToEEnumEditorDescriptorMap;
  }
  
  /**
   * Add an EEnum editor descriptor
   * 
   * @param eEnum the EEnum for which a descriptor is added.
   * @param eEnumEditorDescriptor the descriptor for the <code>eEnum</code>.
   */
  public void addEEnumEditorDescriptor(EEnum eEnum, EEnumEditorDescriptor<?> eEnumEditorDescriptor) {
    eEnumToEEnumEditorDescriptorMap.put(eEnum, eEnumEditorDescriptor);
  }
  
  /**
   * Get the descriptor for a specific <code>EEnum</code>.
   * 
   * @param eEnum the <code>EEnum</code> to get the descriptor for.
   * @return the <code>EEnumEditorDescriptor</code> for <code>eEnum</code>, or null if it is not set.
   */
  public EEnumEditorDescriptor<?> getEEnumEditorDescriptorForEEnum(EEnum eEnum) {
    return eEnumToEEnumEditorDescriptorMap.get(eEnum);
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    Indent indent = new Indent(4);

    for (EClass eClass: eClassToClassDescriptorMap.keySet()) {
      buf.append(NEWLINE);
      buf.append("EClass: ");
      buf.append(eClass.getName());
      buf.append(NEWLINE);
      indent.increment();
      buf.append(eClassToClassDescriptorMap.get(eClass).toString(indent));
      indent.decrement();
    }

    return buf.toString();
  }
}
