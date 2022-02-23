/**
 */
package goedegep.configuration.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.Look;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import javafx.scene.paint.Color;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Look</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.configuration.model.impl.LookImpl#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.LookImpl#getButtonBackgroundColor <em>Button Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.LookImpl#getPanelBackgroundColor <em>Panel Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.LookImpl#getListBackgroundColor <em>List Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.LookImpl#getLabelBackgroundColor <em>Label Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.LookImpl#getBoxBackgroundColor <em>Box Background Color</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.LookImpl#getTextFieldBackgroundColor <em>Text Field Background Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LookImpl extends MinimalEObjectImpl.Container implements Look {
  /**
   * The default value of the '{@link #getBackgroundColor() <em>Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBackgroundColor()
   * @generated
   * @ordered
   */
  protected static final Color BACKGROUND_COLOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBackgroundColor()
   * @generated
   * @ordered
   */
  protected Color backgroundColor = BACKGROUND_COLOR_EDEFAULT;

  /**
   * This is true if the Background Color attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean backgroundColorESet;

  /**
   * The default value of the '{@link #getButtonBackgroundColor() <em>Button Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getButtonBackgroundColor()
   * @generated
   * @ordered
   */
  protected static final Color BUTTON_BACKGROUND_COLOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getButtonBackgroundColor() <em>Button Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getButtonBackgroundColor()
   * @generated
   * @ordered
   */
  protected Color buttonBackgroundColor = BUTTON_BACKGROUND_COLOR_EDEFAULT;

  /**
   * This is true if the Button Background Color attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean buttonBackgroundColorESet;

  /**
   * The default value of the '{@link #getPanelBackgroundColor() <em>Panel Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPanelBackgroundColor()
   * @generated
   * @ordered
   */
  protected static final Color PANEL_BACKGROUND_COLOR_EDEFAULT = (Color) TypesFactory.eINSTANCE
      .createFromString(TypesPackage.eINSTANCE.getEColor(), "1,2,3");

  /**
   * The cached value of the '{@link #getPanelBackgroundColor() <em>Panel Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPanelBackgroundColor()
   * @generated
   * @ordered
   */
  protected Color panelBackgroundColor = PANEL_BACKGROUND_COLOR_EDEFAULT;

  /**
   * This is true if the Panel Background Color attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean panelBackgroundColorESet;

  /**
   * The default value of the '{@link #getListBackgroundColor() <em>List Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getListBackgroundColor()
   * @generated
   * @ordered
   */
  protected static final Color LIST_BACKGROUND_COLOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getListBackgroundColor() <em>List Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getListBackgroundColor()
   * @generated
   * @ordered
   */
  protected Color listBackgroundColor = LIST_BACKGROUND_COLOR_EDEFAULT;

  /**
   * This is true if the List Background Color attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean listBackgroundColorESet;

  /**
   * The default value of the '{@link #getLabelBackgroundColor() <em>Label Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabelBackgroundColor()
   * @generated
   * @ordered
   */
  protected static final Color LABEL_BACKGROUND_COLOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLabelBackgroundColor() <em>Label Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabelBackgroundColor()
   * @generated
   * @ordered
   */
  protected Color labelBackgroundColor = LABEL_BACKGROUND_COLOR_EDEFAULT;

  /**
   * This is true if the Label Background Color attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean labelBackgroundColorESet;

  /**
   * The default value of the '{@link #getBoxBackgroundColor() <em>Box Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBoxBackgroundColor()
   * @generated
   * @ordered
   */
  protected static final Color BOX_BACKGROUND_COLOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBoxBackgroundColor() <em>Box Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBoxBackgroundColor()
   * @generated
   * @ordered
   */
  protected Color boxBackgroundColor = BOX_BACKGROUND_COLOR_EDEFAULT;

  /**
   * This is true if the Box Background Color attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean boxBackgroundColorESet;

  /**
   * The default value of the '{@link #getTextFieldBackgroundColor() <em>Text Field Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTextFieldBackgroundColor()
   * @generated
   * @ordered
   */
  protected static final Color TEXT_FIELD_BACKGROUND_COLOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTextFieldBackgroundColor() <em>Text Field Background Color</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTextFieldBackgroundColor()
   * @generated
   * @ordered
   */
  protected Color textFieldBackgroundColor = TEXT_FIELD_BACKGROUND_COLOR_EDEFAULT;

  /**
   * This is true if the Text Field Background Color attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean textFieldBackgroundColorESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LookImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.LOOK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Color getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBackgroundColor(Color newBackgroundColor) {
    Color oldBackgroundColor = backgroundColor;
    backgroundColor = newBackgroundColor;
    boolean oldBackgroundColorESet = backgroundColorESet;
    backgroundColorESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.LOOK__BACKGROUND_COLOR,
          oldBackgroundColor, backgroundColor, !oldBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBackgroundColor() {
    Color oldBackgroundColor = backgroundColor;
    boolean oldBackgroundColorESet = backgroundColorESet;
    backgroundColor = BACKGROUND_COLOR_EDEFAULT;
    backgroundColorESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.LOOK__BACKGROUND_COLOR,
          oldBackgroundColor, BACKGROUND_COLOR_EDEFAULT, oldBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBackgroundColor() {
    return backgroundColorESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Color getButtonBackgroundColor() {
    return buttonBackgroundColor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setButtonBackgroundColor(Color newButtonBackgroundColor) {
    Color oldButtonBackgroundColor = buttonBackgroundColor;
    buttonBackgroundColor = newButtonBackgroundColor;
    boolean oldButtonBackgroundColorESet = buttonBackgroundColorESet;
    buttonBackgroundColorESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.LOOK__BUTTON_BACKGROUND_COLOR,
          oldButtonBackgroundColor, buttonBackgroundColor, !oldButtonBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetButtonBackgroundColor() {
    Color oldButtonBackgroundColor = buttonBackgroundColor;
    boolean oldButtonBackgroundColorESet = buttonBackgroundColorESet;
    buttonBackgroundColor = BUTTON_BACKGROUND_COLOR_EDEFAULT;
    buttonBackgroundColorESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.LOOK__BUTTON_BACKGROUND_COLOR,
          oldButtonBackgroundColor, BUTTON_BACKGROUND_COLOR_EDEFAULT, oldButtonBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetButtonBackgroundColor() {
    return buttonBackgroundColorESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Color getPanelBackgroundColor() {
    return panelBackgroundColor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPanelBackgroundColor(Color newPanelBackgroundColor) {
    Color oldPanelBackgroundColor = panelBackgroundColor;
    panelBackgroundColor = newPanelBackgroundColor;
    boolean oldPanelBackgroundColorESet = panelBackgroundColorESet;
    panelBackgroundColorESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.LOOK__PANEL_BACKGROUND_COLOR,
          oldPanelBackgroundColor, panelBackgroundColor, !oldPanelBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPanelBackgroundColor() {
    Color oldPanelBackgroundColor = panelBackgroundColor;
    boolean oldPanelBackgroundColorESet = panelBackgroundColorESet;
    panelBackgroundColor = PANEL_BACKGROUND_COLOR_EDEFAULT;
    panelBackgroundColorESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.LOOK__PANEL_BACKGROUND_COLOR,
          oldPanelBackgroundColor, PANEL_BACKGROUND_COLOR_EDEFAULT, oldPanelBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPanelBackgroundColor() {
    return panelBackgroundColorESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Color getListBackgroundColor() {
    return listBackgroundColor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setListBackgroundColor(Color newListBackgroundColor) {
    Color oldListBackgroundColor = listBackgroundColor;
    listBackgroundColor = newListBackgroundColor;
    boolean oldListBackgroundColorESet = listBackgroundColorESet;
    listBackgroundColorESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.LOOK__LIST_BACKGROUND_COLOR,
          oldListBackgroundColor, listBackgroundColor, !oldListBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetListBackgroundColor() {
    Color oldListBackgroundColor = listBackgroundColor;
    boolean oldListBackgroundColorESet = listBackgroundColorESet;
    listBackgroundColor = LIST_BACKGROUND_COLOR_EDEFAULT;
    listBackgroundColorESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.LOOK__LIST_BACKGROUND_COLOR,
          oldListBackgroundColor, LIST_BACKGROUND_COLOR_EDEFAULT, oldListBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetListBackgroundColor() {
    return listBackgroundColorESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Color getLabelBackgroundColor() {
    return labelBackgroundColor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLabelBackgroundColor(Color newLabelBackgroundColor) {
    Color oldLabelBackgroundColor = labelBackgroundColor;
    labelBackgroundColor = newLabelBackgroundColor;
    boolean oldLabelBackgroundColorESet = labelBackgroundColorESet;
    labelBackgroundColorESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.LOOK__LABEL_BACKGROUND_COLOR,
          oldLabelBackgroundColor, labelBackgroundColor, !oldLabelBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLabelBackgroundColor() {
    Color oldLabelBackgroundColor = labelBackgroundColor;
    boolean oldLabelBackgroundColorESet = labelBackgroundColorESet;
    labelBackgroundColor = LABEL_BACKGROUND_COLOR_EDEFAULT;
    labelBackgroundColorESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.LOOK__LABEL_BACKGROUND_COLOR,
          oldLabelBackgroundColor, LABEL_BACKGROUND_COLOR_EDEFAULT, oldLabelBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLabelBackgroundColor() {
    return labelBackgroundColorESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Color getBoxBackgroundColor() {
    return boxBackgroundColor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBoxBackgroundColor(Color newBoxBackgroundColor) {
    Color oldBoxBackgroundColor = boxBackgroundColor;
    boxBackgroundColor = newBoxBackgroundColor;
    boolean oldBoxBackgroundColorESet = boxBackgroundColorESet;
    boxBackgroundColorESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.LOOK__BOX_BACKGROUND_COLOR,
          oldBoxBackgroundColor, boxBackgroundColor, !oldBoxBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBoxBackgroundColor() {
    Color oldBoxBackgroundColor = boxBackgroundColor;
    boolean oldBoxBackgroundColorESet = boxBackgroundColorESet;
    boxBackgroundColor = BOX_BACKGROUND_COLOR_EDEFAULT;
    boxBackgroundColorESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.LOOK__BOX_BACKGROUND_COLOR,
          oldBoxBackgroundColor, BOX_BACKGROUND_COLOR_EDEFAULT, oldBoxBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBoxBackgroundColor() {
    return boxBackgroundColorESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Color getTextFieldBackgroundColor() {
    return textFieldBackgroundColor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTextFieldBackgroundColor(Color newTextFieldBackgroundColor) {
    Color oldTextFieldBackgroundColor = textFieldBackgroundColor;
    textFieldBackgroundColor = newTextFieldBackgroundColor;
    boolean oldTextFieldBackgroundColorESet = textFieldBackgroundColorESet;
    textFieldBackgroundColorESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.LOOK__TEXT_FIELD_BACKGROUND_COLOR,
          oldTextFieldBackgroundColor, textFieldBackgroundColor, !oldTextFieldBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTextFieldBackgroundColor() {
    Color oldTextFieldBackgroundColor = textFieldBackgroundColor;
    boolean oldTextFieldBackgroundColorESet = textFieldBackgroundColorESet;
    textFieldBackgroundColor = TEXT_FIELD_BACKGROUND_COLOR_EDEFAULT;
    textFieldBackgroundColorESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.LOOK__TEXT_FIELD_BACKGROUND_COLOR,
          oldTextFieldBackgroundColor, TEXT_FIELD_BACKGROUND_COLOR_EDEFAULT, oldTextFieldBackgroundColorESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTextFieldBackgroundColor() {
    return textFieldBackgroundColorESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case ConfigurationPackage.LOOK__BACKGROUND_COLOR:
      return getBackgroundColor();
    case ConfigurationPackage.LOOK__BUTTON_BACKGROUND_COLOR:
      return getButtonBackgroundColor();
    case ConfigurationPackage.LOOK__PANEL_BACKGROUND_COLOR:
      return getPanelBackgroundColor();
    case ConfigurationPackage.LOOK__LIST_BACKGROUND_COLOR:
      return getListBackgroundColor();
    case ConfigurationPackage.LOOK__LABEL_BACKGROUND_COLOR:
      return getLabelBackgroundColor();
    case ConfigurationPackage.LOOK__BOX_BACKGROUND_COLOR:
      return getBoxBackgroundColor();
    case ConfigurationPackage.LOOK__TEXT_FIELD_BACKGROUND_COLOR:
      return getTextFieldBackgroundColor();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case ConfigurationPackage.LOOK__BACKGROUND_COLOR:
      setBackgroundColor((Color) newValue);
      return;
    case ConfigurationPackage.LOOK__BUTTON_BACKGROUND_COLOR:
      setButtonBackgroundColor((Color) newValue);
      return;
    case ConfigurationPackage.LOOK__PANEL_BACKGROUND_COLOR:
      setPanelBackgroundColor((Color) newValue);
      return;
    case ConfigurationPackage.LOOK__LIST_BACKGROUND_COLOR:
      setListBackgroundColor((Color) newValue);
      return;
    case ConfigurationPackage.LOOK__LABEL_BACKGROUND_COLOR:
      setLabelBackgroundColor((Color) newValue);
      return;
    case ConfigurationPackage.LOOK__BOX_BACKGROUND_COLOR:
      setBoxBackgroundColor((Color) newValue);
      return;
    case ConfigurationPackage.LOOK__TEXT_FIELD_BACKGROUND_COLOR:
      setTextFieldBackgroundColor((Color) newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
    case ConfigurationPackage.LOOK__BACKGROUND_COLOR:
      unsetBackgroundColor();
      return;
    case ConfigurationPackage.LOOK__BUTTON_BACKGROUND_COLOR:
      unsetButtonBackgroundColor();
      return;
    case ConfigurationPackage.LOOK__PANEL_BACKGROUND_COLOR:
      unsetPanelBackgroundColor();
      return;
    case ConfigurationPackage.LOOK__LIST_BACKGROUND_COLOR:
      unsetListBackgroundColor();
      return;
    case ConfigurationPackage.LOOK__LABEL_BACKGROUND_COLOR:
      unsetLabelBackgroundColor();
      return;
    case ConfigurationPackage.LOOK__BOX_BACKGROUND_COLOR:
      unsetBoxBackgroundColor();
      return;
    case ConfigurationPackage.LOOK__TEXT_FIELD_BACKGROUND_COLOR:
      unsetTextFieldBackgroundColor();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case ConfigurationPackage.LOOK__BACKGROUND_COLOR:
      return isSetBackgroundColor();
    case ConfigurationPackage.LOOK__BUTTON_BACKGROUND_COLOR:
      return isSetButtonBackgroundColor();
    case ConfigurationPackage.LOOK__PANEL_BACKGROUND_COLOR:
      return isSetPanelBackgroundColor();
    case ConfigurationPackage.LOOK__LIST_BACKGROUND_COLOR:
      return isSetListBackgroundColor();
    case ConfigurationPackage.LOOK__LABEL_BACKGROUND_COLOR:
      return isSetLabelBackgroundColor();
    case ConfigurationPackage.LOOK__BOX_BACKGROUND_COLOR:
      return isSetBoxBackgroundColor();
    case ConfigurationPackage.LOOK__TEXT_FIELD_BACKGROUND_COLOR:
      return isSetTextFieldBackgroundColor();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append("backgroundColor=");
    if (backgroundColorESet) {
      buf.append(getRGBAsText(backgroundColor));
    } else {
      buf.append("<unset>");
    }

    buf.append(", buttonBackgroundColor=");
    if (buttonBackgroundColorESet)
      buf.append(getRGBAsText(buttonBackgroundColor));
    else
      buf.append("<unset>");

    buf.append(", panelBackgroundColor=");
    if (panelBackgroundColorESet)
      buf.append(getRGBAsText(panelBackgroundColor));
    else
      buf.append("<unset>");

    buf.append(", listBackgroundColor=");
    if (listBackgroundColorESet)
      buf.append(getRGBAsText(listBackgroundColor));
    else
      buf.append("<unset>");

    buf.append(", labelBackgroundColor=");
    if (labelBackgroundColorESet)
      buf.append(getRGBAsText(labelBackgroundColor));
    else
      buf.append("<unset>");

    buf.append(", boxBackgroundColor=");
    if (boxBackgroundColorESet)
      buf.append(getRGBAsText(boxBackgroundColor));
    else
      buf.append("<unset>");

    buf.append(", textFieldBackgroundColor=");
    if (textFieldBackgroundColorESet)
      buf.append(getRGBAsText(textFieldBackgroundColor));
    else
      buf.append("<unset>");

    return buf.toString();
  }

  /**
   * @generated NOT
   */
  private static String getRGBAsText(Color color) {
    return "[" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "]";
  }

} //LookImpl
