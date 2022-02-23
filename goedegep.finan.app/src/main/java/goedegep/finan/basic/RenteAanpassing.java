package goedegep.finan.basic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;

public class RenteAanpassing {
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  private FixedPointValue rentePercentage;  // rente in centi-procenten (factor is 100).
  private LocalDate       ingangsDatum;     // ingangsdatum van het nieuwe percentage.

  public RenteAanpassing(LocalDate ingangsDatum, FixedPointValue rentePercentage) {
    this.ingangsDatum = ingangsDatum;
    this.rentePercentage = rentePercentage;
  }
  
  public RenteAanpassing() {
    this(null, null);
  }
  
  public FixedPointValue getRentePercentage() {
    return rentePercentage;
  }
  
  public void setRentePercentage(FixedPointValue rentePercentage) {
    this.rentePercentage = rentePercentage;
  }
  
  public LocalDate getIngangsDatum() {
    return ingangsDatum;
  }
  
  public void setIngangsDatum(LocalDate ingangsDatum) {
    this.ingangsDatum = ingangsDatum;
  }
  
  public boolean isValid() {
    if (rentePercentage == null  ||
        ingangsDatum == null) {
      return false;
    }
    
    return true;
  }
  
  public String getDescription() {
    StringBuilder buf = new StringBuilder();
    buf.append("RenteAanpassing: nieuwe rente ");
    buf.append(FPVF.format(rentePercentage));
    buf.append(" %");
    buf.append(", ingaande per ");
    buf.append(DTF.format(ingangsDatum));
    
    return buf.toString();
  }
}