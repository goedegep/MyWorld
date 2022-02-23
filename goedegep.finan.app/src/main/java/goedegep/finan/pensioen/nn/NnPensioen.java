package goedegep.finan.pensioen.nn;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Vragen
 * NN:
 * - waarom is een verzekering op het beleggingskapitaal (van overgenomen waarde), en waarom maar 90%?
 * - hoeveel premie voor deze verzekering?
 * 
 */
public class NnPensioen {
	public static final double OPBOUW_PERCENTAGE = 0.0185;
	public static final PgCurrencyFormat CF = new PgCurrencyFormat();
	public static final int PENSIOEN_LEEFTIJD = 65;
	public static final GregorianCalendar START_DATUM = new GregorianCalendar(2010, 6, 1);
	public static final PgCurrency  BRUTO_MAANDSALARIS = new PgCurrency(PgCurrency.EURO, 523418);
	public static final GregorianCalendar GEBOORTE_DATUM = new GregorianCalendar(1957, 0, 23);
//	public static final GregorianCalendar GEBOORTE_DATUM = new GregorianCalendar(1975, 5, 1);
	
	public static final PgCurrency  FRANCHISE = new PgCurrency(PgCurrency.EURO, 1842800);
	public static final PgCurrency  MAXIMUM_JAARSALARIS_GARANTIE_PENSIOEN = new PgCurrency(PgCurrency.EURO, 4005000);
	public static final double PARTNER_PENSIOEN_PERCENTAGE = 0.7;
	public static final double WEZEN_PENSIOEN_PERCENTAGE = 0.14;
	public static final double EIGEN_BIJDRAGE_PERCENTAGE = 0.08;
	public static final PgCurrency  MAANDELIJKSE_PREMIE = new PgCurrency(PgCurrency.EURO, 36009);
	
	private PgCurrency brutoJaarsalaris = null;
	private GregorianCalendar geboorteDatum = null;
	
  public static void main(String[] args) {
	  NnPensioen nnPensioen = new NnPensioen();
	  
	  System.out.println("Bruto maandsalaris: " + CF.format(BRUTO_MAANDSALARIS));
	  
	  PgCurrency brutoJaarsalaris = calculateBrutoJaarsalaris(BRUTO_MAANDSALARIS);
	  nnPensioen.setBrutoJaarsalaris(brutoJaarsalaris);
	  System.out.println("Bruto jaarsalaris: " + CF.format(brutoJaarsalaris));
	  nnPensioen.setGeboorteDatum(GEBOORTE_DATUM);
	  
	  PgCurrency pensioenGrondslag = nnPensioen.getPensioenGrondslag();
	  System.out.println("Pensioengrondslag: " + CF.format(pensioenGrondslag));
	  double aantalPensioenJaren = nnPensioen.getAantalPensioenJaren();
	  System.out.println("Te bereiken aantal pensioen jaren: " + aantalPensioenJaren);
	  PgCurrency teBereikenOuderdomsPensioen = nnPensioen.berekenOuderdomsPensioen();
	  System.out.println("Te bereiken ouderdomspensioen: " + CF.format(teBereikenOuderdomsPensioen));
	  PgCurrency partnerPensioen = nnPensioen.berekenPartnerPensioen(pensioenGrondslag, GEBOORTE_DATUM);
	  System.out.println("Partnerpensioen: " + CF.format(partnerPensioen));
	  PgCurrency wezenPensioen = nnPensioen.berekenWezenPensioen();
	  System.out.println("Wezenpensioen: " + CF.format(wezenPensioen));
	  PgCurrency eigenBijdrage = nnPensioen.getEigenBijdrage();
	  System.out.println("Eigen bijdrage: " + CF.format(eigenBijdrage));
	  System.out.println("Maandelijkse premie: " + CF.format(MAANDELIJKSE_PREMIE));
  }

  public static PgCurrency calculateBrutoJaarsalaris(PgCurrency brutoMaandsalaris) {
	  return brutoMaandsalaris.multiply(12).multiply(1.08);
  }

  public PgCurrency getBrutoJaarsalaris() {
	  return brutoJaarsalaris;
  }

  public void setBrutoJaarsalaris(PgCurrency brutoJaarsalaris) {
	  this.brutoJaarsalaris = brutoJaarsalaris;
  }

  public GregorianCalendar getGeboorteDatum() {
	  return geboorteDatum;
  }

  public void setGeboorteDatum(GregorianCalendar geboorteDatum) {
	  this.geboorteDatum = geboorteDatum;
  }

  public PgCurrency getPensioenGrondslag() {
	  PgCurrency gemaximaliseerdBrutoJaarsalaris = PgCurrency.min(brutoJaarsalaris, MAXIMUM_JAARSALARIS_GARANTIE_PENSIOEN);
	  return gemaximaliseerdBrutoJaarsalaris.subtract(FRANCHISE);
  }
  
  public double getAantalPensioenJaren() {
//	  System.out.println("y, m, d: " + geboorteDatum.get(Calendar.YEAR) + ", " + geboorteDatum.get(Calendar.MONTH) + ", " + geboorteDatum.get(Calendar.DAY_OF_MONTH));
	  double leeftijdOpIngangsDatum = getDatumVerschilInJaren(geboorteDatum, START_DATUM);
	  return PENSIOEN_LEEFTIJD - leeftijdOpIngangsDatum;
  }
  
  public static double getDatumVerschilInJaren(GregorianCalendar startDatum, GregorianCalendar eindDatum) {
	  double monthCount = -1;
	  GregorianCalendar checkDatum = (GregorianCalendar) startDatum.clone();
	  while(checkDatum.before(eindDatum)){
		  checkDatum.add(Calendar.MONTH, 1);
		  monthCount++;
	  }
	  
	  return monthCount / 12;
  }
  
  public PgCurrency berekenOuderdomsPensioen() {
	  return getPensioenGrondslag().multiply(getAantalPensioenJaren()).multiply(OPBOUW_PERCENTAGE);
  }
  
  public PgCurrency berekenPartnerPensioen(PgCurrency pensioenGrondslag, GregorianCalendar geboorteDatum) {
	  return berekenOuderdomsPensioen().multiply(PARTNER_PENSIOEN_PERCENTAGE);
  }
  
  public PgCurrency berekenWezenPensioen() {
	  return berekenOuderdomsPensioen().multiply(WEZEN_PENSIOEN_PERCENTAGE);
  }
  
  public PgCurrency getEigenBijdrage() {
	  return getPensioenGrondslag().multiply(EIGEN_BIJDRAGE_PERCENTAGE).divide(12);
  }
} 
