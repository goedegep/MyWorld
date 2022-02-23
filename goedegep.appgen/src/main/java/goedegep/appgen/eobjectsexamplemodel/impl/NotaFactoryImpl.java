/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel.impl;

import java.text.ParseException;

import goedegep.appgen.eobjectsexamplemodel.*;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NotaFactoryImpl extends EFactoryImpl implements NotaFactory {
  private static PgCurrencyFormat cf = new PgCurrencyFormat();
  private static FlexDateFormat FDF = new FlexDateFormat();
  
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static NotaFactory init() {
    try {
      NotaFactory theNotaFactory = (NotaFactory)EPackage.Registry.INSTANCE.getEFactory(NotaPackage.eNS_URI);
      if (theNotaFactory != null) {
        return theNotaFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new NotaFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotaFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case NotaPackage.NOTAS: return createNotas();
      case NotaPackage.NOTA: return createNota();
      case NotaPackage.NOTA_ITEM: return createNotaItem();
      case NotaPackage.EIGENDOMMEN_LIJST: return createEigendommenLijst();
      case NotaPackage.EIGENDOM: return createEigendom();
      case NotaPackage.UITGAVE: return createUitgave();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Notas createNotas() {
    NotasImpl notas = new NotasImpl();
    return notas;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Nota createNota() {
    NotaImpl nota = new NotaImpl();
    return nota;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotaItem createNotaItem() {
    NotaItemImpl notaItem = new NotaItemImpl();
    return notaItem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EigendommenLijst createEigendommenLijst() {
    EigendommenLijstImpl eigendommenLijst = new EigendommenLijstImpl();
    return eigendommenLijst;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Eigendom createEigendom() {
    EigendomImpl eigendom = new EigendomImpl();
    return eigendom;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Uitgave createUitgave() {
    UitgaveImpl uitgave = new UitgaveImpl();
    return uitgave;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   */
  public PgCurrency createEMoneyFromString(EDataType eDataType, String initialValue) {
    try {
      return cf.parse(initialValue);
    } catch (ParseException e) {
      System.out.println("Exception");
      throw new IllegalArgumentException("De waarde '" + initialValue + "' is geen geldige waarde voor Money");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   */
  public String convertEMoneyToString(EDataType eDataType, Object instanceValue) {
    return cf.format((PgCurrency) instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   */
  public FlexDate createEFlexDateFromString(EDataType eDataType, String initialValue) {
    try {
      return FDF.parse(initialValue);
    } catch (ParseException e) {
      System.out.println("Exception");
      throw new IllegalArgumentException("De waarde '" + initialValue + "' is geen geldige waarde voor een datum");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   */
  public String convertEFlexDateToString(EDataType eDataType, Object instanceValue) {
    return FDF.format((FlexDate) instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotaPackage getNotaPackage() {
    return (NotaPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static NotaPackage getPackage() {
    return NotaPackage.eINSTANCE;
  }

} //NotaFactoryImpl
