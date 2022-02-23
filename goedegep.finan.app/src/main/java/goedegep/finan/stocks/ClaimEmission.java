package goedegep.finan.stocks;

import goedegep.util.money.PgCurrency;

/**
 * Deze class bevat een claim emissie.
 * Bij een claim emissie ontvangt een eigenaar van aandelen
 * een recht (claim right) per aandeel dat hij in zijn bezit heeft.
 * De rechten kunnen verwisseld worden in aandelen, onder bijbetaling
 * van een vastgestelde prijs per aandeel.
 * Claimrechten worden ook verhandeld. Dit gebeurd normaal ter afronding.
 * Ik weet niet of deze koers kan varieren. Voorlopig is voor mij 1 koers
 * voldoende.
 * Ter identificatie hebben claim emissies ook een Id.
 * 
 * TEST: As this is a data class, a JUnit test is not needed.
 */

public class ClaimEmission {
  private Share      share;          // The share to which the emission applies.
  private String     id;             // Emission identification, e.g. "DEC 2003".
  private int        fromAmount;     // For each 'fromAmount' rights you receive
  private int        toAmount;       // 'toAmount' shares.
  private PgCurrency pricePerShare;  // Extra to be payed per share.
  private PgCurrency rightRate;      // Rate at which rights are handled.
  
  /**
   * Get the share to which this claim emission applies.
   * 
   * @return the share to which this claim emission applies.
   */
  public Share getShare() {
    return share;
  }
  
  /**
   * Set the share to which this claim emission applies.
   * 
   * @param share The share to which this claim emission applies.
   */
  public void setShare(Share share) {
    this.share = share;
  }
  
  /**
   * Get the identification of a claim emission.
   * 
   * @return The identification of the claim emission.
   */
  public String getId() {
    return id;
  }
  
  /**
   * Set the Id of a claim emission.
   * 
   * @param id The identification of the claim emission.
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * Get the number of rights needed for a specific amount (to amount)
   * of shares.
   * 
   * @return The number of rights needed for a specific amount (to amount) of shares.
   */
  public int getFromAmount() {
    return fromAmount;
  }
  
  /**
   * Set the number of rights needed for a specific amount (to amount)
   * of shares.
   * 
   * @param fromAmount The number of rights needed for a specific amount (to amount)
   * of shares.
   */
  public void setFromAmount(int fromAmount) {
    this.fromAmount = fromAmount;
  }
  
  /**
   * Get the number of shares to be obtained for a specific number
   * (from amount) of rights.
   * 
   * @return The number of shares to be obtained for a specific number
   * (from amount) of rights.
   */
  public int getToAmount() {
    return toAmount;
  }
  
  /**
   * Set the number of shares to be obtained for a specific number
   * (from amount) of rights.
   * 
   * @param toAmount The number of shares to be obtained for a specific number
   * (from amount) of rights.
   */
  public void setToAmount(int toAmount) {
    this.toAmount = toAmount;
  }
  
  /**
   * Get the price to be paid per share.
   * 
   * @return The price to be paid per share.
   */
  public PgCurrency getPricePerShare() {
    return pricePerShare;
  }
  
  /**
   * Set the price to be paid per share.
   * 
   * @param pricePerShare The price to be paid per share.
   */
  public void setPricePerShare(PgCurrency pricePerShare) {
    this.pricePerShare = pricePerShare;
  }
  
  /**
   * Get the rate at which rights are handled.
   * 
   * @return The rate at which rights are handled.
   */  
  public PgCurrency getRightRate() {
    return rightRate;
  }
  
  /**
   * Set the rate at which rights are handled.
   * 
   * @param rightRate The rate at which rights are handled.
   */
  public void setRightRate(PgCurrency rightRate) {
    this.rightRate = rightRate;
  }
}
