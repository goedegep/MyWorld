package goedegep.finan.stocks;

import java.util.Comparator;

public class OptionPosition implements Comparator<OptionPosition> {
  private OptionSerie optionSerie;
  private int         position = 0;
  
  public OptionPosition(OptionSerie optionSerie) {
    this.optionSerie = optionSerie;
  }

  public OptionSerie getOptionSerie() {
    return optionSerie;
  }

  public int getPosition() {
    return position;
  }
  
  public void addToPosition(int nrOfContracts) {
    position += nrOfContracts;
  }
  
  public void subtractFromPosition(int nrOfContracts, boolean allowNegativePosition) {
    if (!allowNegativePosition && (nrOfContracts > position)) {
      throw new IllegalArgumentException("nrOfContracts too big");
    }
    position -= nrOfContracts;
  }  
  
  public int compare(OptionPosition o1, OptionPosition o2) {
    return o1.optionSerie.compare(o1.optionSerie, o2.optionSerie);
  }
}
