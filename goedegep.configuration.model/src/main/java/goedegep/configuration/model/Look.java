/**
 */
package goedegep.configuration.model;

import javafx.scene.paint.Color;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Look</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This class specifies visual aspects of the GUI components of an application. Currently only colors are specified.
 * 
 * backgroundColor
 * General background color, e.g. used for panels, panes, layouts.
 * 
 * buttonBackgroundColor
 * Buttons use a gradient, so ...
 * 
 * panelBackgroundColor
 * Already covered by backgroundColor
 * 
 * listBackgroundColor
 * Background color for lists. Typically lighter than the backgroundColor
 * 
 * labelBackgroundColor
 * Labels typically have no background. There are text on a panel background.
 * 
 * boxBackgroundColor
 * Used for a ComboBox. Should this be the same as a list? And/or TextField?
 * 
 * textFieldBackgroundColor
 * Background color for TextFields.
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.configuration.model.Look#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.Look#getButtonBackgroundColor <em>Button Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.Look#getPanelBackgroundColor <em>Panel Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.Look#getListBackgroundColor <em>List Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.Look#getLabelBackgroundColor <em>Label Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.Look#getBoxBackgroundColor <em>Box Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.Look#getTextFieldBackgroundColor <em>Text Field Background Color</em>}</li>
 * </ul>
 *
 * @see goedegep.configuration.model.ConfigurationPackage#getLook()
 * @model
 * @generated
 */
public interface Look extends EObject {
  /**
   * Returns the value of the '<em><b>Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Achtergrond Kleur</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The default background color. 
   * <!-- end-model-doc -->
   * @return the value of the '<em>Background Color</em>' attribute.
   * @see #isSetBackgroundColor()
   * @see #unsetBackgroundColor()
   * @see #setBackgroundColor(Color)
   * @see goedegep.configuration.model.ConfigurationPackage#getLook_BackgroundColor()
   * @model unsettable="true" dataType="goedegep.types.model.EColor"
   * @generated
   */
  Color getBackgroundColor();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.Look#getBackgroundColor <em>Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Background Color</em>' attribute.
   * @see #isSetBackgroundColor()
   * @see #unsetBackgroundColor()
   * @see #getBackgroundColor()
   * @generated
   */
  void setBackgroundColor(Color value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.Look#getBackgroundColor <em>Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBackgroundColor()
   * @see #getBackgroundColor()
   * @see #setBackgroundColor(Color)
   * @generated
   */
  void unsetBackgroundColor();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.Look#getBackgroundColor <em>Background Color</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Background Color</em>' attribute is set.
   * @see #unsetBackgroundColor()
   * @see #getBackgroundColor()
   * @see #setBackgroundColor(Color)
   * @generated
   */
  boolean isSetBackgroundColor();

  /**
   * Returns the value of the '<em><b>Button Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The background color for buttons.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Button Background Color</em>' attribute.
   * @see #isSetButtonBackgroundColor()
   * @see #unsetButtonBackgroundColor()
   * @see #setButtonBackgroundColor(Color)
   * @see goedegep.configuration.model.ConfigurationPackage#getLook_ButtonBackgroundColor()
   * @model unsettable="true" dataType="goedegep.types.model.EColor"
   * @generated
   */
  Color getButtonBackgroundColor();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.Look#getButtonBackgroundColor <em>Button Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Button Background Color</em>' attribute.
   * @see #isSetButtonBackgroundColor()
   * @see #unsetButtonBackgroundColor()
   * @see #getButtonBackgroundColor()
   * @generated
   */
  void setButtonBackgroundColor(Color value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.Look#getButtonBackgroundColor <em>Button Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetButtonBackgroundColor()
   * @see #getButtonBackgroundColor()
   * @see #setButtonBackgroundColor(Color)
   * @generated
   */
  void unsetButtonBackgroundColor();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.Look#getButtonBackgroundColor <em>Button Background Color</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Button Background Color</em>' attribute is set.
   * @see #unsetButtonBackgroundColor()
   * @see #getButtonBackgroundColor()
   * @see #setButtonBackgroundColor(Color)
   * @generated
   */
  boolean isSetButtonBackgroundColor();

  /**
   * Returns the value of the '<em><b>Panel Background Color</b></em>' attribute.
   * The default value is <code>"1,2,3"</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The (background) color for panels.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Panel Background Color</em>' attribute.
   * @see #isSetPanelBackgroundColor()
   * @see #unsetPanelBackgroundColor()
   * @see #setPanelBackgroundColor(Color)
   * @see goedegep.configuration.model.ConfigurationPackage#getLook_PanelBackgroundColor()
   * @model default="1,2,3" unsettable="true" dataType="goedegep.types.model.EColor"
   * @generated
   */
  Color getPanelBackgroundColor();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.Look#getPanelBackgroundColor <em>Panel Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Panel Background Color</em>' attribute.
   * @see #isSetPanelBackgroundColor()
   * @see #unsetPanelBackgroundColor()
   * @see #getPanelBackgroundColor()
   * @generated
   */
  void setPanelBackgroundColor(Color value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.Look#getPanelBackgroundColor <em>Panel Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPanelBackgroundColor()
   * @see #getPanelBackgroundColor()
   * @see #setPanelBackgroundColor(Color)
   * @generated
   */
  void unsetPanelBackgroundColor();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.Look#getPanelBackgroundColor <em>Panel Background Color</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Panel Background Color</em>' attribute is set.
   * @see #unsetPanelBackgroundColor()
   * @see #getPanelBackgroundColor()
   * @see #setPanelBackgroundColor(Color)
   * @generated
   */
  boolean isSetPanelBackgroundColor();

  /**
   * Returns the value of the '<em><b>List Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The background color for lists.
   * <!-- end-model-doc -->
   * @return the value of the '<em>List Background Color</em>' attribute.
   * @see #isSetListBackgroundColor()
   * @see #unsetListBackgroundColor()
   * @see #setListBackgroundColor(Color)
   * @see goedegep.configuration.model.ConfigurationPackage#getLook_ListBackgroundColor()
   * @model unsettable="true" dataType="goedegep.types.model.EColor"
   * @generated
   */
  Color getListBackgroundColor();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.Look#getListBackgroundColor <em>List Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>List Background Color</em>' attribute.
   * @see #isSetListBackgroundColor()
   * @see #unsetListBackgroundColor()
   * @see #getListBackgroundColor()
   * @generated
   */
  void setListBackgroundColor(Color value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.Look#getListBackgroundColor <em>List Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetListBackgroundColor()
   * @see #getListBackgroundColor()
   * @see #setListBackgroundColor(Color)
   * @generated
   */
  void unsetListBackgroundColor();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.Look#getListBackgroundColor <em>List Background Color</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>List Background Color</em>' attribute is set.
   * @see #unsetListBackgroundColor()
   * @see #getListBackgroundColor()
   * @see #setListBackgroundColor(Color)
   * @generated
   */
  boolean isSetListBackgroundColor();

  /**
   * Returns the value of the '<em><b>Label Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The background color for labels.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Label Background Color</em>' attribute.
   * @see #isSetLabelBackgroundColor()
   * @see #unsetLabelBackgroundColor()
   * @see #setLabelBackgroundColor(Color)
   * @see goedegep.configuration.model.ConfigurationPackage#getLook_LabelBackgroundColor()
   * @model unsettable="true" dataType="goedegep.types.model.EColor"
   * @generated
   */
  Color getLabelBackgroundColor();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.Look#getLabelBackgroundColor <em>Label Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label Background Color</em>' attribute.
   * @see #isSetLabelBackgroundColor()
   * @see #unsetLabelBackgroundColor()
   * @see #getLabelBackgroundColor()
   * @generated
   */
  void setLabelBackgroundColor(Color value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.Look#getLabelBackgroundColor <em>Label Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLabelBackgroundColor()
   * @see #getLabelBackgroundColor()
   * @see #setLabelBackgroundColor(Color)
   * @generated
   */
  void unsetLabelBackgroundColor();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.Look#getLabelBackgroundColor <em>Label Background Color</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Label Background Color</em>' attribute is set.
   * @see #unsetLabelBackgroundColor()
   * @see #getLabelBackgroundColor()
   * @see #setLabelBackgroundColor(Color)
   * @generated
   */
  boolean isSetLabelBackgroundColor();

  /**
   * Returns the value of the '<em><b>Box Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The background color for combo boxes.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Box Background Color</em>' attribute.
   * @see #isSetBoxBackgroundColor()
   * @see #unsetBoxBackgroundColor()
   * @see #setBoxBackgroundColor(Color)
   * @see goedegep.configuration.model.ConfigurationPackage#getLook_BoxBackgroundColor()
   * @model unsettable="true" dataType="goedegep.types.model.EColor"
   * @generated
   */
  Color getBoxBackgroundColor();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.Look#getBoxBackgroundColor <em>Box Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Box Background Color</em>' attribute.
   * @see #isSetBoxBackgroundColor()
   * @see #unsetBoxBackgroundColor()
   * @see #getBoxBackgroundColor()
   * @generated
   */
  void setBoxBackgroundColor(Color value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.Look#getBoxBackgroundColor <em>Box Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBoxBackgroundColor()
   * @see #getBoxBackgroundColor()
   * @see #setBoxBackgroundColor(Color)
   * @generated
   */
  void unsetBoxBackgroundColor();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.Look#getBoxBackgroundColor <em>Box Background Color</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Box Background Color</em>' attribute is set.
   * @see #unsetBoxBackgroundColor()
   * @see #getBoxBackgroundColor()
   * @see #setBoxBackgroundColor(Color)
   * @generated
   */
  boolean isSetBoxBackgroundColor();

  /**
   * Returns the value of the '<em><b>Text Field Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The background color for text fields and areas.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Text Field Background Color</em>' attribute.
   * @see #isSetTextFieldBackgroundColor()
   * @see #unsetTextFieldBackgroundColor()
   * @see #setTextFieldBackgroundColor(Color)
   * @see goedegep.configuration.model.ConfigurationPackage#getLook_TextFieldBackgroundColor()
   * @model unsettable="true" dataType="goedegep.types.model.EColor"
   * @generated
   */
  Color getTextFieldBackgroundColor();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.Look#getTextFieldBackgroundColor <em>Text Field Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Text Field Background Color</em>' attribute.
   * @see #isSetTextFieldBackgroundColor()
   * @see #unsetTextFieldBackgroundColor()
   * @see #getTextFieldBackgroundColor()
   * @generated
   */
  void setTextFieldBackgroundColor(Color value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.Look#getTextFieldBackgroundColor <em>Text Field Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTextFieldBackgroundColor()
   * @see #getTextFieldBackgroundColor()
   * @see #setTextFieldBackgroundColor(Color)
   * @generated
   */
  void unsetTextFieldBackgroundColor();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.Look#getTextFieldBackgroundColor <em>Text Field Background Color</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Text Field Background Color</em>' attribute is set.
   * @see #unsetTextFieldBackgroundColor()
   * @see #getTextFieldBackgroundColor()
   * @see #setTextFieldBackgroundColor(Color)
   * @generated
   */
  boolean isSetTextFieldBackgroundColor();

} // Look
