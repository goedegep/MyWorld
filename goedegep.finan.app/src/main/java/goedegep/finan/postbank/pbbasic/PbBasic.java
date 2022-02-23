//Title:       Postbank Basic
//Version:
//Copyright:   Copyright (c) 2002
//Author:      Peter Goedegebure
//Company:     goedegep
//Description: Bevat basis definities voor de Postbank.

package goedegep.finan.postbank.pbbasic;


public class PbBasic {
  // Namen van alle Postbank rekeningen en fondsen
  public final static String  giroRekeningString            = "Girorekening";
  public final static String  effectenRekeningString        = "Effectenrekening";

  // rekening FinanFormat Strings
  public final static String  giroRekeningFFString          = "GR";
  public final static String  effectenRekeningFFString      = "ER";
  public final static String  renteRekeningFFString         = "RE";
  public final static String  plusRekeningFFString          = "PL";
  public final static String  sterRekeningFFString          = "ST";
  public final static String  leeuwRekeningFFString         = "LE";
  public final static String  kapitaalRekeningFFString      = "KA";
  public final static String  aandelenFondsFFString         = "AF";
  public final static String  beleggingsFondsFFString       = "BF";
  public final static String  wereldmerkenFondsFFString     = "WF";
  public final static String  financieleWereldFondsFFString = "FW";

  // Spaarrekening ID's
  public static final short   SPAARREK_MIN                  = 1;
  public static final short   RENTEREKENING                 = 1;
  public static final short   PLUSREKENING                  = 2;
  public static final short   STERREKENING                  = 3;
  public static final short   LEEUWREKENING                 = 4;
  public static final short   KAPITAALREKENING              = 5;
  public static final short   SPAARREK_MAX                  = 5;
  public static final int     AANTAL_SPAARREK               = SPAARREK_MAX - SPAARREK_MIN + 1;

  // Rekening types
  public final static int PB_RT_GIROREKENING       = 1;
  public final static int PB_RT_EFFECTENREKENING   = 2;
  public final static int PB_RT_SPAARREKENING      = 3;
  public final static int PB_RT_FONDS              = 4;

}