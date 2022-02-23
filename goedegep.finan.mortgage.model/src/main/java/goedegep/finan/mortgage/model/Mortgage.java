/**
 */
package goedegep.finan.mortgage.model;

import goedegep.rolodex.model.Address;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import java.util.Date;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mortgage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getLender <em>Lender</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getLenderAddress <em>Lender Address</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner1 <em>Lender Signer1</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner2 <em>Lender Signer2</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumber <em>Lender Bank Account Number</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumberNameAndAddress <em>Lender Bank Account Number Name And Address</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerTitleAndName <em>Borrower Title And Name</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getMortgageName <em>Mortgage Name</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerAddress <em>Borrower Address</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getMortgageNumber <em>Mortgage Number</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getMortgageType <em>Mortgage Type</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getStartingDate <em>Starting Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getFirstPaymentDate <em>First Payment Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getDuration <em>Duration</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getPrincipal <em>Principal</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getFixedInterestPeriod <em>Fixed Interest Period</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgage#getMortgageEvents <em>Mortgage Events</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage()
 * @model
 * @generated
 */
public interface Mortgage extends EObject {

  /**
   * Returns the value of the '<em><b>Lender</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The bank, or other financial institution, providing the mortgage.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lender</em>' attribute.
   * @see #isSetLender()
   * @see #unsetLender()
   * @see #setLender(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_Lender()
   * @model unsettable="true"
   * @generated
   */
  String getLender();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLender <em>Lender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lender</em>' attribute.
   * @see #isSetLender()
   * @see #unsetLender()
   * @see #getLender()
   * @generated
   */
  void setLender(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLender <em>Lender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLender()
   * @see #getLender()
   * @see #setLender(String)
   * @generated
   */
  void unsetLender();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLender <em>Lender</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Lender</em>' attribute is set.
   * @see #unsetLender()
   * @see #getLender()
   * @see #setLender(String)
   * @generated
   */
  boolean isSetLender();

  /**
   * Returns the value of the '<em><b>Lender Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Address of the mortgage lender.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lender Address</em>' reference.
   * @see #isSetLenderAddress()
   * @see #unsetLenderAddress()
   * @see #setLenderAddress(Address)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_LenderAddress()
   * @model unsettable="true"
   * @generated
   */
  Address getLenderAddress();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderAddress <em>Lender Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lender Address</em>' reference.
   * @see #isSetLenderAddress()
   * @see #unsetLenderAddress()
   * @see #getLenderAddress()
   * @generated
   */
  void setLenderAddress(Address value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderAddress <em>Lender Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLenderAddress()
   * @see #getLenderAddress()
   * @see #setLenderAddress(Address)
   * @generated
   */
  void unsetLenderAddress();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderAddress <em>Lender Address</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Lender Address</em>' reference is set.
   * @see #unsetLenderAddress()
   * @see #getLenderAddress()
   * @see #setLenderAddress(Address)
   * @generated
   */
  boolean isSetLenderAddress();

  /**
   * Returns the value of the '<em><b>Lender Signer1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Name of the first signer on behalf of the mortgage lender.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lender Signer1</em>' attribute.
   * @see #isSetLenderSigner1()
   * @see #unsetLenderSigner1()
   * @see #setLenderSigner1(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_LenderSigner1()
   * @model unsettable="true"
   * @generated
   */
  String getLenderSigner1();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner1 <em>Lender Signer1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lender Signer1</em>' attribute.
   * @see #isSetLenderSigner1()
   * @see #unsetLenderSigner1()
   * @see #getLenderSigner1()
   * @generated
   */
  void setLenderSigner1(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner1 <em>Lender Signer1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLenderSigner1()
   * @see #getLenderSigner1()
   * @see #setLenderSigner1(String)
   * @generated
   */
  void unsetLenderSigner1();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner1 <em>Lender Signer1</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Lender Signer1</em>' attribute is set.
   * @see #unsetLenderSigner1()
   * @see #getLenderSigner1()
   * @see #setLenderSigner1(String)
   * @generated
   */
  boolean isSetLenderSigner1();

  /**
   * Returns the value of the '<em><b>Lender Signer2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Name of the second signer on behalf of the mortgage lender.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lender Signer2</em>' attribute.
   * @see #isSetLenderSigner2()
   * @see #unsetLenderSigner2()
   * @see #setLenderSigner2(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_LenderSigner2()
   * @model unsettable="true"
   * @generated
   */
  String getLenderSigner2();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner2 <em>Lender Signer2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lender Signer2</em>' attribute.
   * @see #isSetLenderSigner2()
   * @see #unsetLenderSigner2()
   * @see #getLenderSigner2()
   * @generated
   */
  void setLenderSigner2(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner2 <em>Lender Signer2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLenderSigner2()
   * @see #getLenderSigner2()
   * @see #setLenderSigner2(String)
   * @generated
   */
  void unsetLenderSigner2();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner2 <em>Lender Signer2</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Lender Signer2</em>' attribute is set.
   * @see #unsetLenderSigner2()
   * @see #getLenderSigner2()
   * @see #setLenderSigner2(String)
   * @generated
   */
  boolean isSetLenderSigner2();

  /**
   * Returns the value of the '<em><b>Lender Bank Account Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The bank account number on which the payments have to be done.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lender Bank Account Number</em>' attribute.
   * @see #isSetLenderBankAccountNumber()
   * @see #unsetLenderBankAccountNumber()
   * @see #setLenderBankAccountNumber(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_LenderBankAccountNumber()
   * @model unsettable="true"
   * @generated
   */
  String getLenderBankAccountNumber();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumber <em>Lender Bank Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lender Bank Account Number</em>' attribute.
   * @see #isSetLenderBankAccountNumber()
   * @see #unsetLenderBankAccountNumber()
   * @see #getLenderBankAccountNumber()
   * @generated
   */
  void setLenderBankAccountNumber(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumber <em>Lender Bank Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLenderBankAccountNumber()
   * @see #getLenderBankAccountNumber()
   * @see #setLenderBankAccountNumber(String)
   * @generated
   */
  void unsetLenderBankAccountNumber();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumber <em>Lender Bank Account Number</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Lender Bank Account Number</em>' attribute is set.
   * @see #unsetLenderBankAccountNumber()
   * @see #getLenderBankAccountNumber()
   * @see #setLenderBankAccountNumber(String)
   * @generated
   */
  boolean isSetLenderBankAccountNumber();

  /**
   * Returns the value of the '<em><b>Lender Bank Account Number Name And Address</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Name and address (as far as needed) to be provided with payments to the mortgageLenderBankAccountNumber.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lender Bank Account Number Name And Address</em>' attribute.
   * @see #isSetLenderBankAccountNumberNameAndAddress()
   * @see #unsetLenderBankAccountNumberNameAndAddress()
   * @see #setLenderBankAccountNumberNameAndAddress(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_LenderBankAccountNumberNameAndAddress()
   * @model unsettable="true"
   * @generated
   */
  String getLenderBankAccountNumberNameAndAddress();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumberNameAndAddress <em>Lender Bank Account Number Name And Address</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lender Bank Account Number Name And Address</em>' attribute.
   * @see #isSetLenderBankAccountNumberNameAndAddress()
   * @see #unsetLenderBankAccountNumberNameAndAddress()
   * @see #getLenderBankAccountNumberNameAndAddress()
   * @generated
   */
  void setLenderBankAccountNumberNameAndAddress(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumberNameAndAddress <em>Lender Bank Account Number Name And Address</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLenderBankAccountNumberNameAndAddress()
   * @see #getLenderBankAccountNumberNameAndAddress()
   * @see #setLenderBankAccountNumberNameAndAddress(String)
   * @generated
   */
  void unsetLenderBankAccountNumberNameAndAddress();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumberNameAndAddress <em>Lender Bank Account Number Name And Address</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Lender Bank Account Number Name And Address</em>' attribute is set.
   * @see #unsetLenderBankAccountNumberNameAndAddress()
   * @see #getLenderBankAccountNumberNameAndAddress()
   * @see #setLenderBankAccountNumberNameAndAddress(String)
   * @generated
   */
  boolean isSetLenderBankAccountNumberNameAndAddress();

  /**
   * Returns the value of the '<em><b>Borrower Title And Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Title and name of the borrower(s).
   * <!-- end-model-doc -->
   * @return the value of the '<em>Borrower Title And Name</em>' attribute.
   * @see #isSetBorrowerTitleAndName()
   * @see #unsetBorrowerTitleAndName()
   * @see #setBorrowerTitleAndName(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_BorrowerTitleAndName()
   * @model unsettable="true"
   * @generated
   */
  String getBorrowerTitleAndName();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerTitleAndName <em>Borrower Title And Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Borrower Title And Name</em>' attribute.
   * @see #isSetBorrowerTitleAndName()
   * @see #unsetBorrowerTitleAndName()
   * @see #getBorrowerTitleAndName()
   * @generated
   */
  void setBorrowerTitleAndName(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerTitleAndName <em>Borrower Title And Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBorrowerTitleAndName()
   * @see #getBorrowerTitleAndName()
   * @see #setBorrowerTitleAndName(String)
   * @generated
   */
  void unsetBorrowerTitleAndName();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerTitleAndName <em>Borrower Title And Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Borrower Title And Name</em>' attribute is set.
   * @see #unsetBorrowerTitleAndName()
   * @see #getBorrowerTitleAndName()
   * @see #setBorrowerTitleAndName(String)
   * @generated
   */
  boolean isSetBorrowerTitleAndName();

  /**
   * Returns the value of the '<em><b>Mortgage Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The name of the mortgage.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Mortgage Name</em>' attribute.
   * @see #isSetMortgageName()
   * @see #unsetMortgageName()
   * @see #setMortgageName(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_MortgageName()
   * @model unsettable="true"
   * @generated
   */
  String getMortgageName();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageName <em>Mortgage Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mortgage Name</em>' attribute.
   * @see #isSetMortgageName()
   * @see #unsetMortgageName()
   * @see #getMortgageName()
   * @generated
   */
  void setMortgageName(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageName <em>Mortgage Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMortgageName()
   * @see #getMortgageName()
   * @see #setMortgageName(String)
   * @generated
   */
  void unsetMortgageName();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageName <em>Mortgage Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Mortgage Name</em>' attribute is set.
   * @see #unsetMortgageName()
   * @see #getMortgageName()
   * @see #setMortgageName(String)
   * @generated
   */
  boolean isSetMortgageName();

  /**
   * Returns the value of the '<em><b>Borrower Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Address of the borrower(s).
   * <!-- end-model-doc -->
   * @return the value of the '<em>Borrower Address</em>' reference.
   * @see #isSetBorrowerAddress()
   * @see #unsetBorrowerAddress()
   * @see #setBorrowerAddress(Address)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_BorrowerAddress()
   * @model unsettable="true"
   * @generated
   */
  Address getBorrowerAddress();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerAddress <em>Borrower Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Borrower Address</em>' reference.
   * @see #isSetBorrowerAddress()
   * @see #unsetBorrowerAddress()
   * @see #getBorrowerAddress()
   * @generated
   */
  void setBorrowerAddress(Address value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerAddress <em>Borrower Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBorrowerAddress()
   * @see #getBorrowerAddress()
   * @see #setBorrowerAddress(Address)
   * @generated
   */
  void unsetBorrowerAddress();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerAddress <em>Borrower Address</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Borrower Address</em>' reference is set.
   * @see #unsetBorrowerAddress()
   * @see #getBorrowerAddress()
   * @see #setBorrowerAddress(Address)
   * @generated
   */
  boolean isSetBorrowerAddress();

  /**
   * Returns the value of the '<em><b>Mortgage Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The identification number of the mortgage.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Mortgage Number</em>' attribute.
   * @see #isSetMortgageNumber()
   * @see #unsetMortgageNumber()
   * @see #setMortgageNumber(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_MortgageNumber()
   * @model unsettable="true"
   * @generated
   */
  String getMortgageNumber();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageNumber <em>Mortgage Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mortgage Number</em>' attribute.
   * @see #isSetMortgageNumber()
   * @see #unsetMortgageNumber()
   * @see #getMortgageNumber()
   * @generated
   */
  void setMortgageNumber(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageNumber <em>Mortgage Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMortgageNumber()
   * @see #getMortgageNumber()
   * @see #setMortgageNumber(String)
   * @generated
   */
  void unsetMortgageNumber();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageNumber <em>Mortgage Number</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Mortgage Number</em>' attribute is set.
   * @see #unsetMortgageNumber()
   * @see #getMortgageNumber()
   * @see #setMortgageNumber(String)
   * @generated
   */
  boolean isSetMortgageNumber();

  /**
   * Returns the value of the '<em><b>Mortgage Type</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.finan.mortgage.model.MortgageType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The type of mortgage.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Mortgage Type</em>' attribute.
   * @see goedegep.finan.mortgage.model.MortgageType
   * @see #isSetMortgageType()
   * @see #unsetMortgageType()
   * @see #setMortgageType(MortgageType)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_MortgageType()
   * @model unsettable="true"
   * @generated
   */
  MortgageType getMortgageType();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageType <em>Mortgage Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mortgage Type</em>' attribute.
   * @see goedegep.finan.mortgage.model.MortgageType
   * @see #isSetMortgageType()
   * @see #unsetMortgageType()
   * @see #getMortgageType()
   * @generated
   */
  void setMortgageType(MortgageType value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageType <em>Mortgage Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMortgageType()
   * @see #getMortgageType()
   * @see #setMortgageType(MortgageType)
   * @generated
   */
  void unsetMortgageType();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageType <em>Mortgage Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Mortgage Type</em>' attribute is set.
   * @see #unsetMortgageType()
   * @see #getMortgageType()
   * @see #setMortgageType(MortgageType)
   * @generated
   */
  boolean isSetMortgageType();

  /**
   * Returns the value of the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The starting date of the mortgage, typically the day the house is bought.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Starting Date</em>' attribute.
   * @see #isSetStartingDate()
   * @see #unsetStartingDate()
   * @see #setStartingDate(Date)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_StartingDate()
   * @model unsettable="true"
   * @generated
   */
  Date getStartingDate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getStartingDate <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Starting Date</em>' attribute.
   * @see #isSetStartingDate()
   * @see #unsetStartingDate()
   * @see #getStartingDate()
   * @generated
   */
  void setStartingDate(Date value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getStartingDate <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStartingDate()
   * @see #getStartingDate()
   * @see #setStartingDate(Date)
   * @generated
   */
  void unsetStartingDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getStartingDate <em>Starting Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Starting Date</em>' attribute is set.
   * @see #unsetStartingDate()
   * @see #getStartingDate()
   * @see #setStartingDate(Date)
   * @generated
   */
  boolean isSetStartingDate();

  /**
   * Returns the value of the '<em><b>First Payment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The date the first payment is to be made.
   * <!-- end-model-doc -->
   * @return the value of the '<em>First Payment Date</em>' attribute.
   * @see #isSetFirstPaymentDate()
   * @see #unsetFirstPaymentDate()
   * @see #setFirstPaymentDate(Date)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_FirstPaymentDate()
   * @model unsettable="true"
   * @generated
   */
  Date getFirstPaymentDate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getFirstPaymentDate <em>First Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>First Payment Date</em>' attribute.
   * @see #isSetFirstPaymentDate()
   * @see #unsetFirstPaymentDate()
   * @see #getFirstPaymentDate()
   * @generated
   */
  void setFirstPaymentDate(Date value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getFirstPaymentDate <em>First Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFirstPaymentDate()
   * @see #getFirstPaymentDate()
   * @see #setFirstPaymentDate(Date)
   * @generated
   */
  void unsetFirstPaymentDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getFirstPaymentDate <em>First Payment Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>First Payment Date</em>' attribute is set.
   * @see #unsetFirstPaymentDate()
   * @see #getFirstPaymentDate()
   * @see #setFirstPaymentDate(Date)
   * @generated
   */
  boolean isSetFirstPaymentDate();

  /**
   * Returns the value of the '<em><b>Duration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The duration in months.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Duration</em>' attribute.
   * @see #isSetDuration()
   * @see #unsetDuration()
   * @see #setDuration(int)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_Duration()
   * @model unsettable="true"
   * @generated
   */
  int getDuration();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getDuration <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Duration</em>' attribute.
   * @see #isSetDuration()
   * @see #unsetDuration()
   * @see #getDuration()
   * @generated
   */
  void setDuration(int value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getDuration <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDuration()
   * @see #getDuration()
   * @see #setDuration(int)
   * @generated
   */
  void unsetDuration();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getDuration <em>Duration</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Duration</em>' attribute is set.
   * @see #unsetDuration()
   * @see #getDuration()
   * @see #setDuration(int)
   * @generated
   */
  boolean isSetDuration();

  /**
   * Returns the value of the '<em><b>Principal</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The amount of the loan.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Principal</em>' attribute.
   * @see #isSetPrincipal()
   * @see #unsetPrincipal()
   * @see #setPrincipal(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_Principal()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getPrincipal();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getPrincipal <em>Principal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Principal</em>' attribute.
   * @see #isSetPrincipal()
   * @see #unsetPrincipal()
   * @see #getPrincipal()
   * @generated
   */
  void setPrincipal(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getPrincipal <em>Principal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPrincipal()
   * @see #getPrincipal()
   * @see #setPrincipal(PgCurrency)
   * @generated
   */
  void unsetPrincipal();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getPrincipal <em>Principal</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Principal</em>' attribute is set.
   * @see #unsetPrincipal()
   * @see #getPrincipal()
   * @see #setPrincipal(PgCurrency)
   * @generated
   */
  boolean isSetPrincipal();

  /**
   * Returns the value of the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Interest rate in centi-percent.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Interest Rate</em>' attribute.
   * @see #isSetInterestRate()
   * @see #unsetInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_InterestRate()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getInterestRate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getInterestRate <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interest Rate</em>' attribute.
   * @see #isSetInterestRate()
   * @see #unsetInterestRate()
   * @see #getInterestRate()
   * @generated
   */
  void setInterestRate(FixedPointValue value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getInterestRate <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInterestRate()
   * @see #getInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @generated
   */
  void unsetInterestRate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getInterestRate <em>Interest Rate</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Interest Rate</em>' attribute is set.
   * @see #unsetInterestRate()
   * @see #getInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @generated
   */
  boolean isSetInterestRate();

  /**
   * Returns the value of the '<em><b>Fixed Interest Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Period, in months, during which the interest rate is fixed.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Fixed Interest Period</em>' attribute.
   * @see #isSetFixedInterestPeriod()
   * @see #unsetFixedInterestPeriod()
   * @see #setFixedInterestPeriod(int)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_FixedInterestPeriod()
   * @model unsettable="true"
   * @generated
   */
  int getFixedInterestPeriod();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getFixedInterestPeriod <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fixed Interest Period</em>' attribute.
   * @see #isSetFixedInterestPeriod()
   * @see #unsetFixedInterestPeriod()
   * @see #getFixedInterestPeriod()
   * @generated
   */
  void setFixedInterestPeriod(int value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getFixedInterestPeriod <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFixedInterestPeriod()
   * @see #getFixedInterestPeriod()
   * @see #setFixedInterestPeriod(int)
   * @generated
   */
  void unsetFixedInterestPeriod();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Mortgage#getFixedInterestPeriod <em>Fixed Interest Period</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Fixed Interest Period</em>' attribute is set.
   * @see #unsetFixedInterestPeriod()
   * @see #getFixedInterestPeriod()
   * @see #setFixedInterestPeriod(int)
   * @generated
   */
  boolean isSetFixedInterestPeriod();

  /**
   * Returns the value of the '<em><b>Mortgage Events</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.mortgage.model.MortgageEvent}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The events like monthly payments and changes in interest rate.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Mortgage Events</em>' containment reference list.
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgage_MortgageEvents()
   * @model containment="true"
   * @generated
   */
  EList<MortgageEvent> getMortgageEvents();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  String getId();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  void addMortgageEvent(MortgageEvent mortgageEvent);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  void addMortgageEvent(MortgageEvent mortgageEvent, MortgageEvent insertLocation, boolean before);
} // Mortgage
