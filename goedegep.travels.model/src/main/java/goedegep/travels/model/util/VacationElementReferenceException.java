package goedegep.travels.model.util;

import goedegep.travels.model.VacationElement;

/**
 * 
 * generated NOT
 */
@SuppressWarnings("serial")
public class VacationElementReferenceException extends Exception {
  private VacationElement vacationElement;
  
  public VacationElementReferenceException(VacationElement vacationElement) {
    this.vacationElement = vacationElement;
  }

  public VacationElement getVacationElement() {
    return vacationElement;
  }
  
}
