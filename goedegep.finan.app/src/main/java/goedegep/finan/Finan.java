package goedegep.finan;

import java.util.logging.Logger;

import goedegep.rolodex.model.Rolodex;

public class Finan {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(Finan.class.getName());
  
  // Rolodex
  private Rolodex rolodex;
  
  
  public Finan(Rolodex rolodex) {
    this.rolodex = rolodex;
  }
  
  public Rolodex getRolodex() {
    return rolodex;
  }

}
