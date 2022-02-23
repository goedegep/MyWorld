package goedegep.jfx.eobjecttreeview;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;

import goedegep.util.text.Indent;

/**
 * This class provides a presentation descriptor for an EObjectTreeView.
 * <p>
 * The descriptor consists of EObjectTreeItemClassDescriptor's for the classes within an EObject hierarchy.
 */
public class EObjectTreeDescriptor {
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
    
  public EObjectTreeItemClassDescriptor getDescriptorForEClass(EClass eClass) {
    return eClassToClassDescriptorMap.get(eClass);
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
